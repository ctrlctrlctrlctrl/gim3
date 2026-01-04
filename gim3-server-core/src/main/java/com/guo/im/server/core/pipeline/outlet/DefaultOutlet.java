package com.guo.im.server.core.pipeline.outlet;

import com.guo.im.server.core.internal.model.ClusterMessageOuterClass;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ： gyj
 * @date ：2026/1/3 12:10
 * @modifiedBy ：
 */
public class DefaultOutlet extends AbstractOutlet{

    public static final ConcurrentHashMap<String ,ConcurrentHashMap<Integer, List<ClusterMessageOuterClass.ClusterMessage>>> data = new ConcurrentHashMap();

    @Override
    protected void doSend(String instanceId, ClusterMessageOuterClass.ClusterMessage clusterMessage) {
        ConcurrentHashMap<Integer, List<ClusterMessageOuterClass.ClusterMessage>> cmdMap = data.getOrDefault(instanceId, new ConcurrentHashMap<>());
        List<ClusterMessageOuterClass.ClusterMessage> messages = cmdMap.getOrDefault(clusterMessage.getClusterCMD(), new ArrayList<>());
        messages.add(clusterMessage);
        cmdMap.put(clusterMessage.getClusterCMD(), messages);
        data.put(instanceId, cmdMap);
    }
}
