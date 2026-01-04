package com.guo.im.server.core.pipeline.outlet;

import com.guo.im.server.core.internal.model.ClusterMessageOuterClass;

public interface Outlet {

    void send(String instanceId, ClusterMessageOuterClass.ClusterMessage clusterMessage);

}