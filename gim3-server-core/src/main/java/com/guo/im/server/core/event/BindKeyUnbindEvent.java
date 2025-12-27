package com.guo.im.server.core.event;

/**
 * @author ： gyj
 * @date ：2025/12/27 14:35
 * @modifiedBy ：
 */
public class BindKeyUnbindEvent extends BindKeyEvent{
    public BindKeyUnbindEvent(String bindKey, String deviceId, String connectId) {
        super(bindKey, deviceId, connectId);
    }
}
