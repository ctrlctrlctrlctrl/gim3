package com.guo.im.server.core.dispatch;

import com.guo.im.server.core.internal.model.ClusterMessageOuterClass;

import java.util.List;

public interface MessageProcessor {

    String topic();

    int batchSize();

    int period();

    void onMessage(ClusterMessageOuterClass.ClusterMessage message);

    void onMessage(List<ClusterMessageOuterClass.ClusterMessage> messages);

}
