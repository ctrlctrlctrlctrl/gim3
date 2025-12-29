package com.guo.im.server.core.endpoint;

import cn.hutool.core.util.IdUtil;
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

        IMEndpointHolder.putEndpoint(imEndpoint, IdUtil.getSnowflakeNextIdStr());

        this.onEndpointCreated(imEndpoint);

    }

    public abstract void onEndpointCreated(IMEndpoint imEndpoint);

    public void endpointClosed(IMEndpoint imEndpoint) {

        IMEndpointHolder.removeEndpoint(imEndpoint);

        //todo 待补充通知远端端点已关闭

    }


}
