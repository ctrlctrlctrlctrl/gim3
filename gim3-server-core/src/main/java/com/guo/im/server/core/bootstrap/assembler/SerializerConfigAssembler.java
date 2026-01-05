package com.guo.im.server.core.bootstrap.assembler;

import com.guo.im.server.core.bootstrap.BootstrapContext;
import com.guo.im.server.core.config.SerializerConfig;

/**
 * @author ： gyj
 * @date ：2026/1/5 19:39
 * @modifiedBy ：
 */
public class SerializerConfigAssembler {

    public static SerializerConfig assemble(BootstrapContext bootstrapContext) {
        if (bootstrapContext.getSerializerConfig() == null) {
            return new SerializerConfig();
        } else {
            return bootstrapContext.getSerializerConfig();
        }
    }
}
