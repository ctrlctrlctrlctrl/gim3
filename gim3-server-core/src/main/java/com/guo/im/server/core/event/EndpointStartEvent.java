package com.guo.im.server.core.event;

/**
 * @author ： gyj
 * @date ：2025/12/27 14:30
 * @modifiedBy ：
 */
public class EndpointStartEvent extends EndpointEvent {
    public EndpointStartEvent(String instanceId, String endpointId) {
        super(instanceId, endpointId);
    }
}
