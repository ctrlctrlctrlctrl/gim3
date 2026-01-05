package com.guo.im.server.core.bootstrap.assembler;

import com.guo.im.server.core.bootstrap.BootstrapContext;
import com.guo.im.server.core.channel.DefaultIMChannelLifecycle;
import com.guo.im.server.core.channel.IMChannelLifecycle;

/**
 * @author ： gyj
 * @date ：2026/1/5 19:27
 * @modifiedBy ：
 */
public class IMChannelLifecycleAssembler {

    public static IMChannelLifecycle assemble(BootstrapContext bootstrapContext) {
        IMChannelLifecycle imChannelLifecycle = bootstrapContext.getImChannelLifecycle();

        if (imChannelLifecycle == null) {
            imChannelLifecycle = new DefaultIMChannelLifecycle(bootstrapContext.getInstanceId(),
                    bootstrapContext.getImChannelRegistry(),
                    bootstrapContext.getBindkeyRegistry(),
                    bootstrapContext.getImProcessorRouter()
            );

        }

        return imChannelLifecycle;
    }

}
