package com.guo.im.server.core.bootstrap.assembler;

import com.guo.im.server.core.bootstrap.BootstrapContext;
import com.guo.im.server.core.registry.channel.DefaultSingleIMChannelRegistry;
import com.guo.im.server.core.registry.channel.IMChannelRegistry;

/**
 * @author ： gyj
 * @date ：2026/1/5 19:55
 * @modifiedBy ：
 */
public class IMChannelRegistryAssembler {

    public static IMChannelRegistry assemble(BootstrapContext bootstrapContext) {
        if (bootstrapContext.getImChannelRegistry() != null) {
            return bootstrapContext.getImChannelRegistry();
        } else {
            return new DefaultSingleIMChannelRegistry();
        }
    }

}
