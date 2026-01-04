package com.guo.im.server.core.pipeline.Inlet;

import com.guo.im.server.core.internal.model.ClusterMessageOuterClass;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ： gyj
 * @date ：2026/1/2 20:28
 * @modifiedBy ：
 */
public abstract class AbstractInlet implements Inlet {

    @Override
    public List<ClusterMessageOuterClass.ClusterMessage> receive(String instanceId, Integer cmd, int batchSize) {
        List<ClusterMessageOuterClass.ClusterMessage> clusterMessages = new ArrayList<>();
        // 前置处理
        clusterMessages = doReceive(instanceId, cmd, batchSize);
        // 后置处理
        return clusterMessages;
    }

    protected abstract List<ClusterMessageOuterClass.ClusterMessage> doReceive(String instanceId, Integer cmd, int batchSize);
}
