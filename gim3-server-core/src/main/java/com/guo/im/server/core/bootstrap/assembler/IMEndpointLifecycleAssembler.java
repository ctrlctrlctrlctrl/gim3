package com.guo.im.server.core.bootstrap.assembler;

import com.guo.im.server.core.bootstrap.BootstrapContext;
import com.guo.im.server.core.endpoint.DefaultIMEndpointLifecycle;
import com.guo.im.server.core.endpoint.IMEndpointLifecycle;

/**
 * @author ： gyj
 * @date ：2026/1/5 19:41
 * @modifiedBy ：
 */
public class IMEndpointLifecycleAssembler {

    public static IMEndpointLifecycle assemble(BootstrapContext bootstrapContext) {
        if (bootstrapContext.getImEndpointLifecycle() != null) {
            return bootstrapContext.getImEndpointLifecycle();
        } else {
            return new DefaultIMEndpointLifecycle(bootstrapContext.getInstanceId(), bootstrapContext.getImChannelLifecycle());
        }
    }

}
