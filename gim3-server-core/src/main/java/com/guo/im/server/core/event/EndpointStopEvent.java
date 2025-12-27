package com.guo.im.server.core.event;

/**
 * @author ： gyj
 * @date ：2025/12/27 14:31
 * @modifiedBy ：
 */
public class EndpointStopEvent extends EndpointEvent{
    public EndpointStopEvent(String instanceId, String endpointId) {
        super(instanceId, endpointId);
    }
}
