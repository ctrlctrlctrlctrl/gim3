package com.guo.im.server.core.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ： gyj
 * @date ：2025/12/27 14:31
 * @modifiedBy ：
 */
@Getter
@AllArgsConstructor
public class ConnectEvent extends IMEvent {

    private final String instanceId;
    private final String endpointId;
    private final String connectId;

}
