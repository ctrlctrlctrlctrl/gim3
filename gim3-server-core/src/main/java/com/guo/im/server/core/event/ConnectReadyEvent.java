package com.guo.im.server.core.event;

/**
 * @author ： gyj
 * @date ：2025/12/27 14:33
 * @modifiedBy ：
 */
public class ConnectReadyEvent extends ConnectEvent {
    public ConnectReadyEvent(String instanceId, String endpointId, String connectId) {
        super(instanceId, endpointId, connectId);
    }
}
