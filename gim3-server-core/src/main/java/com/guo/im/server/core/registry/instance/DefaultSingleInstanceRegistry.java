package com.guo.im.server.core.registry.instance;

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
 * @date ：2025/12/29 15:24
 * @modifiedBy ：
 */
public class DefaultSingleInstanceRegistry implements InstanceRegistry {

    private static final ConcurrentHashMap<String, InstanceRegistration> INSTANCE_REGISTRY = new ConcurrentHashMap<>();

    private static final List<InstanceChangeListener> LISTENERS = new ArrayList<>();

    @Override
    public void register(String instanceId, Map<String, Object> metadata) {
        Assert.notBlank(instanceId, "实例ID不能为空");

        RegistryActionEnum registryAction;

        if (!INSTANCE_REGISTRY.containsKey(instanceId)) {
            registryAction = RegistryActionEnum.REGISTRY;
        } else {
            registryAction = RegistryActionEnum.CHANGE;
        }

        INSTANCE_REGISTRY.put(instanceId, new InstanceRegistration(instanceId, metadata));


        this.notifyListeners(registryAction, INSTANCE_REGISTRY.get(instanceId));

    }

    @Override
    public boolean deregister(String instanceId) {
        if (INSTANCE_REGISTRY.containsKey(instanceId)) {

            InstanceRegistration instanceRegistration = INSTANCE_REGISTRY.get(instanceId);

            INSTANCE_REGISTRY.remove(instanceId);

            this.notifyListeners(RegistryActionEnum.DEREGISTRY, instanceRegistration);

            return true;
        }
        return false;
    }

    @Override
    public List<InstanceRegistration> getInstances(Collection<String> instanceIds) {
        if (CollUtil.isEmpty(instanceIds)) {
            return new ArrayList<>(INSTANCE_REGISTRY.values());
        }

        List<InstanceRegistration> instanceRegistrations = new ArrayList<>();

        for (String instanceId : instanceIds) {
            InstanceRegistration instanceRegistration = INSTANCE_REGISTRY.get(instanceId);
            if (instanceRegistration != null) {
                instanceRegistrations.add(instanceRegistration);
            }
        }

        return instanceRegistrations;
    }

    @Override
    public InstanceRegistration getInstance(String instanceId) {
        if (INSTANCE_REGISTRY.containsKey(instanceId)) {
            return INSTANCE_REGISTRY.get(instanceId);
        }
        return null;
    }

    @Override
    public void watchInstances(InstanceChangeListener listener) {
        if (listener != null) {
            LISTENERS.add(listener);
        }
    }

    private void notifyListeners(RegistryActionEnum registryAction, InstanceRegistration instance) {
        for (InstanceChangeListener listener : LISTENERS) {
            listener.onInstancesChanged(registryAction, instance);
        }
    }

}
