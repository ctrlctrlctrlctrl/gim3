package com.guo.im.server.core.pipeline.processor;

import com.guo.im.server.core.internal.model.ClusterMessageOuterClass;

import java.util.List;

public interface ClusterMessageProcessor {

    Integer topic();

    int batchSize();

    int period();

    MessageHandleModeEnum handleMode();

    void onMessage(ClusterMessageOuterClass.ClusterMessage message);

    void onMessage(List<ClusterMessageOuterClass.ClusterMessage> messages);

}
