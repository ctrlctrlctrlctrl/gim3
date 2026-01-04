package com.guo.im.server.core.pipeline.Inlet;

import com.guo.im.server.core.internal.model.ClusterMessageOuterClass;

import java.util.List;

public interface Inlet {

    List<ClusterMessageOuterClass.ClusterMessage> receive(String instanceId, Integer cmd, int batchSize);

}
