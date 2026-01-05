package com.guo.im.server.core.bootstrap.assembler;

import com.guo.im.server.core.bootstrap.BootstrapContext;
import com.guo.im.server.core.pipeline.outlet.DefaultOutlet;
import com.guo.im.server.core.pipeline.outlet.Outlet;

/**
 * @author ： gyj
 * @date ：2026/1/5 20:10
 * @modifiedBy ：
 */
public class OutletAssmbler {

    public static Outlet assemble(BootstrapContext bootstrapContext) {
        if (bootstrapContext.getOutlet() != null) {
            return bootstrapContext.getOutlet();
        } else {
            return new DefaultOutlet();
        }
    }

}
