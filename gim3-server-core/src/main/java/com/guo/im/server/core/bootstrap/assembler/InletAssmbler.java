package com.guo.im.server.core.bootstrap.assembler;

import com.guo.im.server.core.bootstrap.BootstrapContext;
import com.guo.im.server.core.pipeline.Inlet.DefaultInlet;
import com.guo.im.server.core.pipeline.Inlet.Inlet;

/**
 * @author ： gyj
 * @date ：2026/1/5 20:06
 * @modifiedBy ：
 */
public class InletAssmbler {

    public static Inlet assemble(BootstrapContext bootstrapContext) {

        if (bootstrapContext != null) {
            return bootstrapContext.getInlet();
        } else {
            return new DefaultInlet();
        }

    }

}
