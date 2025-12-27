package com.guo.im.server.core.dispatch;

import com.guo.im.server.core.internal.model.ClusterMessageOuterClass;

import java.util.List;

public interface Inlet {

    List<ClusterMessageOuterClass.ClusterMessage> receive(String instanceId, String key, int batchSize);

}
