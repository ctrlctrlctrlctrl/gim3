package com.guo.im.server.core.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ： gyj
 * @date ：2025/12/27 14:33
 * @modifiedBy ：
 */
@Getter
@AllArgsConstructor
public class BindKeyEvent extends IMEvent{

    private final String bindKey;
    private final String deviceId;
    private final String connectId;

}
