package com.guo.im.server.core.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ： gyj
 * @date ：2025/12/27 14:35
 * @modifiedBy ：
 */
@Getter
@AllArgsConstructor
public class ClusterMessageAckEvent extends IMEvent {

    private final String messageId;
    private final String sourceInstanceId;
    private final String targetInstanceId;

}
