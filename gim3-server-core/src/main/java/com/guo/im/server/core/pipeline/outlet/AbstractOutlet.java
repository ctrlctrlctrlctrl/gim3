package com.guo.im.server.core.pipeline.outlet;

import com.guo.im.server.core.internal.model.ClusterMessageOuterClass;

/**
 * @author ： gyj
 * @date ：2026/1/3 11:40
 * @modifiedBy ：
 */
public abstract class AbstractOutlet implements Outlet{
    @Override
    public void send(String instanceId, ClusterMessageOuterClass.ClusterMessage clusterMessage) {
        doSend(instanceId, clusterMessage);
    }

    protected abstract void doSend(String instanceId, ClusterMessageOuterClass.ClusterMessage clusterMessage);
}
