package com.guo.im.server.core.registry.channel;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.guo.im.server.core.registry.enums.RegistryActionEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ： gyj
 * @date ：2025/12/29 19:12
 * @modifiedBy ：
 */
public class DefaultSingleIMChannelRegistry implements IMChannelRegistry {

    private static final ConcurrentHashMap<String, IMChannelRegistration> CHANNEL_REGISTRY = new ConcurrentHashMap<>();

    private static final List<IMChannelChangeListener> LISTENERS = new ArrayList<>();

    @Override
    public void register(String connectId, Map<String, Object> metadata) {
        Assert.notBlank(connectId, "实例ID不能为空");

        RegistryActionEnum registryAction;

        if (!CHANNEL_REGISTRY.containsKey(connectId)) {
            registryAction = RegistryActionEnum.REGISTRY;
        } else {
            registryAction = RegistryActionEnum.CHANGE;
        }

        CHANNEL_REGISTRY.put(connectId, new IMChannelRegistration(connectId, metadata));


        this.notifyListeners(registryAction, CHANNEL_REGISTRY.get(connectId));

    }

    @Override
    public boolean deregister(String connectId) {
        if (CHANNEL_REGISTRY.containsKey(connectId)) {

            IMChannelRegistration imChannelRegistration = CHANNEL_REGISTRY.get(connectId);

            CHANNEL_REGISTRY.remove(connectId);

            this.notifyListeners(RegistryActionEnum.DEREGISTRY, imChannelRegistration);

            return true;
        }
        return false;
    }

    @Override
    public List<IMChannelRegistration> getIMChannels(Collection<String> connectIds) {
        if (CollUtil.isEmpty(connectIds)) {
            return new ArrayList<>(CHANNEL_REGISTRY.values());
        }

        List<IMChannelRegistration> imChannelRegistrations = new ArrayList<>();

        for (String instanceId : connectIds) {
            IMChannelRegistration instanceRegistration = CHANNEL_REGISTRY.get(instanceId);
            if (instanceRegistration != null) {
                imChannelRegistrations.add(instanceRegistration);
            }
        }

        return imChannelRegistrations;
    }

    @Override
    public void watchIMChannels(IMChannelChangeListener listener) {
        if (listener != null) {
            LISTENERS.add(listener);
        }
    }

    private void notifyListeners(RegistryActionEnum registryAction, IMChannelRegistration imChannel) {
        for (IMChannelChangeListener listener : LISTENERS) {
            listener.onIMChannelsChanged(registryAction, imChannel);
        }
    }
}
