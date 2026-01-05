package com.guo.im.server.core.bootstrap.assembler;

import com.guo.im.server.core.bootstrap.BootstrapContext;
import com.guo.im.server.core.registry.instance.DefaultSingleInstanceRegistry;
import com.guo.im.server.core.registry.instance.InstanceRegistry;

/**
 * @author ： gyj
 * @date ：2026/1/5 19:57
 * @modifiedBy ：
 */
public class InstanceRegistryAssembler {

    public static InstanceRegistry assemble(BootstrapContext bootstrapContext) {
        if (bootstrapContext.getInstanceRegistry() != null) {
            return bootstrapContext.getInstanceRegistry();
        } else {
            return new DefaultSingleInstanceRegistry();
        }
    }

}
