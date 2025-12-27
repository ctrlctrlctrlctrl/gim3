package com.guo.im.server.core.message;

import com.guo.im.server.core.dispatch.PushTypeEnum;

import java.io.Serializable;

/**
 * @author ： gyj
 * @date ：2025/12/27 12:46
 * @modifiedBy ：
 */
public record DispatchMessage (
        String bindKey,
        String deviceId,
        PushTypeEnum pushType,
        Object payload
) implements Serializable {
}
