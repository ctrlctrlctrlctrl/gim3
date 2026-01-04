package com.guo.im.server.core.pipeline.Inlet;

import cn.hutool.core.collection.CollUtil;
import com.guo.im.server.core.internal.model.ClusterMessageOuterClass;
import com.guo.im.server.core.pipeline.outlet.DefaultOutlet;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ： gyj
 * @date ：2026/1/2 20:33
 * @modifiedBy ：
 */
public class DefaultInlet extends AbstractInlet {

    @Override
    protected List<ClusterMessageOuterClass.ClusterMessage> doReceive(String instanceId, Integer cmd, int batchSize) {
        ConcurrentHashMap<Integer, List<ClusterMessageOuterClass.ClusterMessage>> integerListConcurrentHashMap = DefaultOutlet.data.get(instanceId);
        if (CollUtil.isNotEmpty(integerListConcurrentHashMap)) {
            List<ClusterMessageOuterClass.ClusterMessage> clusterMessages = integerListConcurrentHashMap.get(cmd);
            if (CollUtil.isNotEmpty(clusterMessages)) {
                return clusterMessages.subList(0, Math.min(batchSize, clusterMessages.size()));
            }
        }
        return List.of();
    }
}
