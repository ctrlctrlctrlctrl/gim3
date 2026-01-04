package com.guo.im.server.core.route;

import cn.hutool.core.collection.CollUtil;
import com.guo.im.server.core.registry.channel.IMChannelRegistration;
import com.guo.im.server.core.registry.channel.IMChannelRegistry;

import java.util.List;

/**
 * @author ： gyj
 * @date ：2026/1/2 13:38
 * @modifiedBy ：
 */
public class DefaultConnectRouter implements ConnectRouter {

    private final IMChannelRegistry imChannelRegistry;

    public DefaultConnectRouter(IMChannelRegistry imChannelRegistry) {
        this.imChannelRegistry = imChannelRegistry;
    }

    @Override
    public IMChannelRegistration route(String connectId) {
        List<IMChannelRegistration> imChannels = imChannelRegistry.getIMChannels(CollUtil.newArrayList(connectId));
        if (CollUtil.isEmpty(imChannels)){
            return null;
        } else {
            return imChannels.getFirst();
        }
    }
}
