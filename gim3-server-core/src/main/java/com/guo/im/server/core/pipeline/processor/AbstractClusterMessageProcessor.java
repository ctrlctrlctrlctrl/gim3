package com.guo.im.server.core.pipeline.processor;

import com.guo.im.server.core.config.SerializerConfig;
import com.guo.im.server.core.internal.model.ClusterMessageOuterClass;
import com.guo.im.server.core.pipeline.MessageHandleModeEnum;

import java.util.List;

/**
 * @author ： gyj
 * @date ：2026/1/3 10:49
 * @modifiedBy ：
 */
public abstract class AbstractClusterMessageProcessor implements ClusterMessageProcessor {


    @Override
    public int batchSize() {
        return 0;
    }

    @Override
    public int period() {
        return 50;
    }

}
