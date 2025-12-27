package com.guo.im.server.core.event;

/**
 * @author ： gyj
 * @date ：2025/12/27 14:37
 * @modifiedBy ：
 */
public class ClusterMessageSuccessAckEvent extends ClusterMessageAckEvent {
    public ClusterMessageSuccessAckEvent(String messageId, String sourceInstanceId, String targetInstanceId) {
        super(messageId, sourceInstanceId, targetInstanceId);
    }
}
