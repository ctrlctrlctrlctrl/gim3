package com.guo.im.server.core.channel;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.guo.im.server.core.endpoint.IMEndpoint;
import com.guo.im.server.core.endpoint.IMEndpointHolder;
import com.guo.im.server.core.model.BindKey;
import com.guo.im.server.core.model.IMConnect;
import org.apache.commons.lang3.Validate;

import java.util.Random;

/**
 * @author ： gyj
 * @date ：2025/12/27 15:36
 * @modifiedBy ：
 */
public abstract class IMChannelLifecycle {

    private final String instanceId;

    protected IMChannelLifecycle(String instanceId) {
        this.instanceId = instanceId;
    }

    void channelCreated(IMChannel imChannel, IMEndpoint imEndpoint) {

        Validate.notNull(imChannel, "imChannel不能为空");
        Validate.notNull(imEndpoint, "imEndpoint不能为空");

        String endpointId = IMEndpointHolder.getEndpointId(imEndpoint);
        Validate.notNull(endpointId, "端点不能为空");

        onChannelCreated(imChannel);
        BindKey bindKey = new BindKey(IdUtil.getSnowflakeNextIdStr(), IdUtil.getSnowflakeNextIdStr());
        new IMConnect(instanceId, endpointId, imChannel.getConnectId(), bindKey);
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
