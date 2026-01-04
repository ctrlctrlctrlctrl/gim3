package com.guo.im.server.core.pipeline.dispatch;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ： gyj
 * @date ：2026/1/2 19:16
 * @modifiedBy ：
 */
@Getter
@Setter
public class DefaultDispatchMessage extends DispatchMessage {

    private String bindKey;
    private String deviceId;

}
