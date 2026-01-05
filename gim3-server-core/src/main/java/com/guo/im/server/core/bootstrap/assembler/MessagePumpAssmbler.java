package com.guo.im.server.core.bootstrap.assembler;

import com.guo.im.server.core.bootstrap.BootstrapContext;
import com.guo.im.server.core.pipeline.pump.DefaultMessagePump;
import com.guo.im.server.core.pipeline.pump.MessagePump;

/**
 * @author ： gyj
 * @date ：2026/1/5 20:15
 * @modifiedBy ：
 */
public class MessagePumpAssmbler {

    public static MessagePump assemble(BootstrapContext bootstrapContext) {
        if (bootstrapContext.getMessagePump() != null) {
            return bootstrapContext.getMessagePump();
        } else {
            return new DefaultMessagePump(bootstrapContext.getClusterMessageProcessors(), bootstrapContext.getInlet());
        }
    }

}
