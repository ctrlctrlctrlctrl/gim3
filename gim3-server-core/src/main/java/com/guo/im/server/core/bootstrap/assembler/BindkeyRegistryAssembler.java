package com.guo.im.server.core.bootstrap.assembler;

import com.guo.im.server.core.bootstrap.BootstrapContext;
import com.guo.im.server.core.registry.bindkey.BindkeyRegistry;
import com.guo.im.server.core.registry.bindkey.DefaultSingleBindkeyRegistry;

/**
 * @author ： gyj
 * @date ：2026/1/5 19:53
 * @modifiedBy ：
 */
public class BindkeyRegistryAssembler {

    public static BindkeyRegistry assemble(BootstrapContext bootstrapContext) {
        if (bootstrapContext.getBindkeyRegistry() != null){
            return bootstrapContext.getBindkeyRegistry();
        } else {
            return new DefaultSingleBindkeyRegistry();
        }
    }

}
