package com.guo.im.server.core.endpoint;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.guo.im.server.core.event.BindKeyEvent;
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

    public IMEndpointLifecycle(String instanceId) {
        this.instanceId = instanceId;
    }

    public void endpointCreated(IMEndpoint imEndpoint) {

        Validate.notNull(imEndpoint, "端点不能为空");

        String endpointId = IdUtil.getSnowflakeNextIdStr();

        IMEndpointHolder.putEndpoint(imEndpoint, endpointId);

        this.onEndpointCreated(imEndpoint);

        EventUtil.pulishEvent(instanceId, new EndpointReadyEvent(instanceId, endpointId));
    }

    public abstract void onEndpointCreated(IMEndpoint imEndpoint);

    public void endpointClosed(IMEndpoint imEndpoint) {

        Validate.notNull(imEndpoint, "端点不能为空");

        String endpointId = IMEndpointHolder.getEndpointId(imEndpoint);

        Validate.isTrue(StrUtil.isNotBlank(endpointId), "端点不存在");

        this.onEndpointClosed(imEndpoint);

        IMEndpointHolder.removeEndpoint(imEndpoint);

        EventUtil.pulishEvent(instanceId, new EndpointStopEvent(instanceId, endpointId));

    }

    public abstract void onEndpointClosed(IMEndpoint imEndpoint);


}
