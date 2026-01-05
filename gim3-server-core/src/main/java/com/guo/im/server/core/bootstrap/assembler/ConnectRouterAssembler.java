package com.guo.im.server.core.bootstrap.assembler;

import com.guo.im.server.core.bootstrap.BootstrapContext;
import com.guo.im.server.core.route.ConnectRouter;
import com.guo.im.server.core.route.DefaultConnectRouter;

/**
 * @author ： gyj
 * @date ：2026/1/5 19:59
 * @modifiedBy ：
 */
public class ConnectRouterAssembler {

    public static ConnectRouter assemble(BootstrapContext bootstrapContext) {
        if (bootstrapContext.getConnectRouter() != null) {
            return bootstrapContext.getConnectRouter();
        } else {
            return new DefaultConnectRouter(bootstrapContext.getImChannelRegistry());
        }
    }

}
