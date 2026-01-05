package com.guo.im.server.core.endpoint;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.guo.im.server.core.channel.IMChannelLifecycle;
import com.guo.im.server.core.event.EndpointReadyEvent;
import com.guo.im.server.core.event.EndpointStopEvent;
import com.guo.im.server.core.utils.EventUtil;
import org.apache.commons.lang3.Validate;

/**
 * @author ： gyj
 * @date ：2025/12/27 20:51
 * @modifiedBy ：
 */
public abstract class IMEndpointLifecycle {

    private final String instanceId;

    private final IMChannelLifecycle imChannelLifecycle;

    public IMEndpointLifecycle(String instanceId, IMChannelLifecycle imChannelLifecycle) {
        this.instanceId = instanceId;
        this.imChannelLifecycle = imChannelLifecycle;
    }

    public void endpointCreated(IMEndpoint imEndpoint) {

        Validate.notNull(imEndpoint, "端点不能为空");

        imEndpoint.setIMChannelLifecycle(imChannelLifecycle);

        this.onEndpointCreated(imEndpoint);

        String endpointId = IMEndpointHolder.getEndpointId(imEndpoint);

        EventUtil.pulishEvent(instanceId, new EndpointReadyEvent(instanceId, endpointId));
    }

    protected abstract void onEndpointCreated(IMEndpoint imEndpoint);

    public void endpointClosed(IMEndpoint imEndpoint) {

        Validate.notNull(imEndpoint, "端点不能为空");

        String endpointId = IMEndpointHolder.getEndpointId(imEndpoint);

        Validate.isTrue(StrUtil.isNotBlank(endpointId), "端点不存在");

        this.onEndpointClosed(imEndpoint);

        IMEndpointHolder.removeEndpoint(imEndpoint);

        EventUtil.pulishEvent(instanceId, new EndpointStopEvent(instanceId, endpointId));

    }

    protected abstract void onEndpointClosed(IMEndpoint imEndpoint);


}
