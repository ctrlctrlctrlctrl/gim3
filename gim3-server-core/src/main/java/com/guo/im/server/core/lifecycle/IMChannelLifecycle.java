package com.guo.im.server.core.lifecycle;

import com.guo.im.server.core.channel.IMChannel;

/**
 * @author ： gyj
 * @date ：2025/12/27 15:36
 * @modifiedBy ：
 */
public abstract class IMChannelLifecycle {


    void channelCreated(IMChannel imChannel) {
        onChannelCreated(imChannel);
        //todo 待补充自己的操作 注册、事件等操作
    }

    abstract void onChannelCreated(IMChannel imChannel);

    void channelClosed(String connectId) {
        onChannelClosed(connectId);
        //todo 待补充自己的操作
    }

    abstract void onChannelClosed(String connectId);

    abstract void bindKey(String oldBindKey, String newBindKey);

}
