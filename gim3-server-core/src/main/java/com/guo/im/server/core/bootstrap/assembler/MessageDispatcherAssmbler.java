package com.guo.im.server.core.bootstrap.assembler;

import com.guo.im.server.core.bootstrap.BootstrapContext;
import com.guo.im.server.core.pipeline.dispatch.DefaultMessageDispatcher;
import com.guo.im.server.core.pipeline.dispatch.MessageDispatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ： gyj
 * @date ：2026/1/5 20:07
 * @modifiedBy ：
 */
public class MessageDispatcherAssmbler {

    public static List<MessageDispatcher> assemble(BootstrapContext bootstrapContext) {

        if (bootstrapContext.getMessageDispatcher() != null) {
            return bootstrapContext.getMessageDispatcher();
        } else {
            ArrayList<MessageDispatcher> messageDispatchers = new ArrayList<>();
            DefaultMessageDispatcher defaultMessageDispatcher = new DefaultMessageDispatcher(bootstrapContext.getBindkeyRegistry(),
                    bootstrapContext.getConnectRouter(),
                    bootstrapContext.getOutlet(),
                    bootstrapContext.getSerializerConfig());
            messageDispatchers.add(defaultMessageDispatcher);
            return messageDispatchers;
        }

    }

}
