package com.guo.im.server.core.event;

import com.guo.im.server.core.exception.enums.MessageFailStageEnum;
import com.guo.im.server.core.internal.model.ClusterMessageOuterClass;
import lombok.Getter;

/**
 * @author ： gyj
 * @date ：2025/12/27 14:37
 * @modifiedBy ：
 */
@Getter
public class ClusterMessageFailAckEvent extends ClusterMessageAckEvent{

    private final ClusterMessageOuterClass.ClusterMessage message;
    private final MessageFailStageEnum messageFailStage;
    private final String reason;

    public ClusterMessageFailAckEvent(String messageId, String sourceInstanceId, String targetInstanceId, ClusterMessageOuterClass.ClusterMessage message, MessageFailStageEnum messageFailStage, String reason) {
        super(messageId, sourceInstanceId, targetInstanceId);
        this.message = message;
        this.messageFailStage = messageFailStage;
        this.reason = reason;
    }
}
