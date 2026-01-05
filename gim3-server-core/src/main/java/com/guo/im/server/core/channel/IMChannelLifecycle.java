package com.guo.im.server.core.channel;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import com.guo.im.common.exception.bindkey.BindkeyRepeatException;
import com.guo.im.common.exception.channel.ChannelRegistryRepeatException;
import com.guo.im.server.core.constants.metadatakey.IMChannelKey;
import com.guo.im.server.core.endpoint.IMEndpoint;
import com.guo.im.server.core.endpoint.IMEndpointHolder;
import com.guo.im.server.core.event.BindKeyCreateEvent;
import com.guo.im.server.core.event.BindKeyUnbindEvent;
import com.guo.im.server.core.event.ConnectEndEvent;
import com.guo.im.server.core.event.ConnectReadyEvent;
import com.guo.im.server.core.model.BindKey;
import com.guo.im.server.core.model.IMConnect;
import com.guo.im.server.core.processor.IMProcessorRouter;
import com.guo.im.server.core.registry.bindkey.BindkeyParam;
import com.guo.im.server.core.registry.bindkey.BindkeyRegistration;
import com.guo.im.server.core.registry.bindkey.BindkeyRegistry;
import com.guo.im.server.core.registry.channel.IMChannelRegistration;
import com.guo.im.server.core.registry.channel.IMChannelRegistry;
import com.guo.im.server.core.utils.EventUtil;
import lombok.SneakyThrows;
import org.apache.commons.lang3.Validate;

import java.util.*;

/**
 * @author ： gyj
 * @date ：2025/12/27 15:36
 * @modifiedBy ：
 */
public abstract class IMChannelLifecycle {

    private final String instanceId;

    private final IMChannelRegistry imChannelRegistry;
    private final BindkeyRegistry bindkeyRegistry;
    private final IMProcessorRouter imProcessorRouter;

    public IMChannelLifecycle(String instanceId, IMChannelRegistry imChannelRegistry, BindkeyRegistry bindkeyRegistry, IMProcessorRouter imProcessorRouter) {
        this.instanceId = instanceId;
        this.imChannelRegistry = imChannelRegistry;
        this.bindkeyRegistry = bindkeyRegistry;
        this.imProcessorRouter = imProcessorRouter;
    }

    public void channelCreated(IMChannel imChannel, IMEndpoint imEndpoint) {

        Validate.notNull(imChannel, "imChannel不能为空");
        Validate.notNull(imEndpoint, "imEndpoint不能为空");

        // 维护imChannel的本地内存映射
        IMChannelHolder.putChannel(imChannel.getConnectId(), imChannel);

        // 获取端点id
        String endpointId = IMEndpointHolder.getEndpointId(imEndpoint);
        Validate.notNull(endpointId, "端点不能为空");

        // 调用模板方法
        onChannelCreated(imChannel);

        // 初始化bindkey，并进行注册
        BindKey bindkey = new BindKey(IdUtil.getSnowflakeNextIdStr(), IdUtil.getSnowflakeNextIdStr());
        bindkeyRegistry.register(bindkey.bindKey(), bindkey.deviceId(), imChannel.getConnectId(), new HashMap<>());

        // 维护imChannel的连接信息
        IMConnect imConnect = new IMConnect(instanceId, endpointId, imChannel.getConnectId(), bindkey);
        imChannel.setIMConnect(imConnect);

        // 注册imChannel
        HashMap<String, Object> metadata = new HashMap<>();
        IMChannelKey.INSTANCE_ID.put(metadata, instanceId);
        IMChannelKey.ENDPOINT_ID.put(metadata, endpointId);
        IMChannelKey.BINDKEY.put(metadata, bindkey.bindKey());
        IMChannelKey.DEVICE_ID.put(metadata, bindkey.deviceId());
        imChannelRegistry.register(imChannel.getConnectId(), metadata);

        // 发布bindkey事件
        EventUtil.pulishEvent(instanceId, new BindKeyCreateEvent(bindkey.bindKey(), bindkey.deviceId(), imChannel.getConnectId()));
        // 发布连接就绪事件
        EventUtil.pulishEvent(instanceId, new ConnectReadyEvent(bindkey.bindKey(), bindkey.deviceId(), imChannel.getConnectId()));
    }

    protected abstract void onChannelCreated(IMChannel imChannel);

    public void channelClosed(String connectId) {

        Validate.notBlank(connectId, "connectId不能为空");

        // 调用模板方法
        onChannelClosed(connectId);

        // 删除imChannel的本地内存映射
        IMChannelHolder.removeChannel(connectId);

        // 删除imChannel的注册信息
        List<IMChannelRegistration> imChannels = imChannelRegistry.getIMChannels(CollUtil.newArrayList(connectId));
        if (CollUtil.isNotEmpty(imChannels)) {
            imChannelRegistry.deregister(connectId);
        }

        // 删除bindkey的注册信息
        List<BindkeyRegistration> bindkeys = bindkeyRegistry.getBindkeys(CollUtil.newArrayList(new BindkeyParam(null, null, connectId)));
        if (CollUtil.isNotEmpty(bindkeys)) {

            for (BindkeyRegistration bindkey : bindkeys) {
                bindkeyRegistry.deregister(bindkey.connectId());

                // 发布bindkey事件
                EventUtil.pulishEvent(instanceId, new BindKeyUnbindEvent(bindkey.bindKey(), bindkey.deviceId(), connectId));
                // 发布连接关闭事件
                EventUtil.pulishEvent(instanceId, new ConnectEndEvent(bindkey.bindKey(), bindkey.deviceId(), connectId));
            }
        }
    }

    protected abstract void onChannelClosed(String connectId);

    @SneakyThrows
    public void reBindKey(String oldBindKey, String oldDeviceId, String newBindKey, String newDeviceId) {

        // 查询出旧的bindkey
        ArrayList<BindkeyParam> bindkeyParams = CollUtil.newArrayList(new BindkeyParam(oldBindKey, oldDeviceId, null));
        List<BindkeyRegistration> bindkeys = bindkeyRegistry.getBindkeys(bindkeyParams);
        if (CollUtil.isEmpty(bindkeys)) {
            return;
        }
        if (bindkeys.size() > 1) {
            throw new BindkeyRepeatException(oldBindKey, oldDeviceId);
        }

        BindkeyRegistration bindkey = bindkeys.getFirst();

        // 发布bindkey解绑事件
        EventUtil.pulishEvent(instanceId, new BindKeyUnbindEvent(oldBindKey, oldDeviceId, bindkey.connectId()));

        // 重新注册bindkey
        bindkeyRegistry.register(newBindKey, newDeviceId, bindkey.connectId(), bindkey.metadata());

        // 发布bindkey事件
        EventUtil.pulishEvent(instanceId, new BindKeyCreateEvent(oldBindKey, oldDeviceId, bindkey.connectId()));

        // 更新imChannel的本地内存映射
        IMChannel channel = IMChannelHolder.getChannel(bindkey.connectId());
        channel.setIMConnect(new IMConnect(channel.getIMConnect().instanceId(), channel.getIMConnect().pointId(), channel.getIMConnect().connectId(), new BindKey(newBindKey, newDeviceId)));

        // 删除imchannel的注册信息
        List<IMChannelRegistration> imChannels = imChannelRegistry.getIMChannels(CollUtil.newArrayList(bindkey.connectId()));
        if (CollUtil.isNotEmpty(imChannels)) {
            if (imChannels.size() > 1) {
                throw new ChannelRegistryRepeatException(bindkey.connectId());
            }

            // 重新注册imChannel
            IMChannelRegistration imChannelRegistration = imChannels.getFirst();
            imChannelRegistry.deregister(bindkey.connectId());
            // 重新注册imChannel
            Map<String, Object> metadata = imChannelRegistration.metadata();
            IMChannelKey.INSTANCE_ID.put(metadata, instanceId);
            IMChannelKey.BINDKEY.put(metadata, bindkey.bindKey());
            IMChannelKey.DEVICE_ID.put(metadata, bindkey.deviceId());
            imChannelRegistry.register(bindkey.connectId(), imChannelRegistration.metadata());
        }
    }

}
