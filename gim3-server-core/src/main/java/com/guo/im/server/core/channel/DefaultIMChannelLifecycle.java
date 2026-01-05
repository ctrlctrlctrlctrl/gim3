package com.guo.im.server.core.channel;

import com.guo.im.server.core.processor.IMProcessorRouter;
import com.guo.im.server.core.registry.bindkey.BindkeyRegistry;
import com.guo.im.server.core.registry.channel.IMChannelRegistry;

/**
 * @author ： gyj
 * @date ：2026/1/5 19:25
 * @modifiedBy ：
 */
public class DefaultIMChannelLifecycle extends IMChannelLifecycle{

    public DefaultIMChannelLifecycle(String instanceId, IMChannelRegistry imChannelRegistry, BindkeyRegistry bindkeyRegistry, IMProcessorRouter imProcessorRouter) {
        super(instanceId, imChannelRegistry, bindkeyRegistry, imProcessorRouter);
    }

    @Override
    protected void onChannelCreated(IMChannel imChannel) {

    }

    @Override
    protected void onChannelClosed(String connectId) {

    }
}
