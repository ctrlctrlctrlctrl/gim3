package com.guo.im.server.core.dispatch;

import com.guo.im.server.core.internal.model.ClusterMessageOuterClass;

public interface Outlet {

    void send(String instanceId, String key, ClusterMessageOuterClass.ClusterMessage clusterMessage);

}