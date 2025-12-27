package com.guo.im.server.core.exception.model;

import com.guo.im.server.core.exception.enums.MessageFailStageEnum;
import com.guo.im.server.core.internal.model.ClusterMessageOuterClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ： gyj
 * @date ：2025/12/27 13:02
 * @modifiedBy ：
 */
@Getter
@Setter
@AllArgsConstructor
public class ClusterMessageErrorContext {

    private ClusterMessageOuterClass.ClusterMessage clusterMessage;
    private MessageFailStageEnum messageFailStage;
    private String reason;

}
