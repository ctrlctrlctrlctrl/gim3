package com.guo.im.server.core.endpoint;

import com.guo.im.server.core.channel.IMChannelLifecycle;

/**
 * @author ： gyj
 * @date ：2026/1/5 19:35
 * @modifiedBy ：
 */
public class DefaultIMEndpointLifecycle extends IMEndpointLifecycle {

    public DefaultIMEndpointLifecycle(String instanceId, IMChannelLifecycle imChannelLifecycle) {
        super(instanceId, imChannelLifecycle);
    }

    @Override
    protected void onEndpointCreated(IMEndpoint imEndpoint) {

    }

    @Override
    protected void onEndpointClosed(IMEndpoint imEndpoint) {

    }
}
