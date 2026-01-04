package com.guo.im.server.core.listener;

import cn.hutool.core.collection.CollUtil;
import com.guo.im.server.core.channel.IMChannelLifecycle;
import com.guo.im.server.core.constants.metadatakey.IMChannelKey;
import com.guo.im.server.core.constants.metadatakey.InstanceKey;
import com.guo.im.server.core.event.EndpointEvent;
import com.guo.im.server.core.event.EndpointReadyEvent;
import com.guo.im.server.core.event.EndpointStopEvent;
import com.guo.im.server.core.event.IMEvent;
import com.guo.im.server.core.registry.channel.IMChannelRegistration;
import com.guo.im.server.core.registry.channel.IMChannelRegistry;
import com.guo.im.server.core.registry.instance.InstanceRegistration;
import com.guo.im.server.core.registry.instance.InstanceRegistry;

import java.util.List;
import java.util.Set;

/**
 * @author ： gyj
 * @date ：2026/1/2 10:14
 * @modifiedBy ：
 */
public class EndpointLifecycleListener implements IMEventListener {

    private final InstanceRegistry instanceRegistry;

    private final IMChannelRegistry imChannelRegistry;

    private final IMChannelLifecycle imChannelLifecycle;


    public EndpointLifecycleListener(InstanceRegistry instanceRegistry, IMChannelRegistry imChannelRegistry, IMChannelLifecycle imChannelLifecycle) {
        this.instanceRegistry = instanceRegistry;
        this.imChannelRegistry = imChannelRegistry;
        this.imChannelLifecycle = imChannelLifecycle;
    }

    @Override
    public boolean supports(IMEvent imEvent) {
        return imEvent instanceof EndpointReadyEvent || imEvent instanceof EndpointStopEvent;
    }

    @Override
    public void onEvent(IMEvent imEvent) {
        EndpointEvent endpointEvent = (EndpointEvent) imEvent;

        InstanceRegistration instance = instanceRegistry.getInstance(endpointEvent.getInstanceId());
        if (instance == null) {
            throw new IllegalStateException("实例不存在");
        }

        switch (imEvent) {
            case EndpointReadyEvent event:
                this.onEndpointReady(event, instance);
                break;
            case EndpointStopEvent event:
                this.onEndpointClosed(event, instance);
                break;
            default:
        }
    }

    private void onEndpointReady(EndpointReadyEvent endpointEvent, InstanceRegistration instance) {
        Set<String> endpointIds = InstanceKey.ENDPOINT_IDS.get(instance.metadata());
        endpointIds.add(endpointEvent.getEndpointId());
    }

    private void onEndpointClosed(EndpointStopEvent endpointEvent, InstanceRegistration instance) {
        Set<String> endpointIds = InstanceKey.ENDPOINT_IDS.get(instance.metadata());
        endpointIds.remove(endpointEvent.getEndpointId());

        // 清空该端点的所有imChannel在IM核心的发现位置
        List<IMChannelRegistration> imChannels = imChannelRegistry.getIMChannels(null);
        if (CollUtil.isNotEmpty(imChannels)) {
            for (IMChannelRegistration imChannel : imChannels) {
                String endpointId = IMChannelKey.ENDPOINT_ID.get(imChannel.metadata());// 获取IMChannel的端点id
                if (endpointEvent.getEndpointId().equals(endpointId)) {// 判断该IMChannel的端点id是否是本次关闭的端点id，是的话就通知结束其生命周期
                    imChannelLifecycle.channelClosed(imChannel.connectId());
                }
            }
        }
    }


}
