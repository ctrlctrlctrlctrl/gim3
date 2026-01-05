package com.guo.im.server.core.bootstrap.assembler;

import com.guo.im.server.core.bootstrap.BootstrapContext;
import com.guo.im.server.core.pipeline.processor.ClusterMessageProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ： gyj
 * @date ：2026/1/5 20:12
 * @modifiedBy ：
 */
public class ClusterMessageProcessorAssmbler {

    public static List<ClusterMessageProcessor> assemble(BootstrapContext bootstrapContext) {
        if (bootstrapContext.getClusterMessageProcessors() != null) {
            return bootstrapContext.getClusterMessageProcessors();
        } else {
            return new ArrayList<>();
        }
    }

}
