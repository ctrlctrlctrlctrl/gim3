package com.guo.im.server.core.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ： gyj
 * @date ：2025/12/27 14:25
 * @modifiedBy ：
 */
@Getter
@AllArgsConstructor
public class EndpointEvent extends IMEvent {

    private final String instanceId;
    private final String endpointId;

}
