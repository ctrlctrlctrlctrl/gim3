package com.guo.im.server.core.event;

/**
 * @author ： gyj
 * @date ：2025/12/27 14:31
 * @modifiedBy ：
 */
public class EndpointReadyEvent extends EndpointEvent {
    public EndpointReadyEvent(String instanceId, String endpointId) {
        super(instanceId, endpointId);
    }
}
