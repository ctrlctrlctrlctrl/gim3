package com.guo.im.server.core.bootstrap.assembler;

import com.guo.im.server.core.bootstrap.BootstrapContext;
import com.guo.im.server.core.processor.IMProcessorRouter;

/**
 * @author ： gyj
 * @date ：2026/1/5 19:50
 * @modifiedBy ：
 */
public class IMProcessorRouterAssembler {

    public static IMProcessorRouter assemble(BootstrapContext bootstrapContext) {
        return new IMProcessorRouter(bootstrapContext.getImProcessors(), bootstrapContext.getBizFilters());
    }

}
