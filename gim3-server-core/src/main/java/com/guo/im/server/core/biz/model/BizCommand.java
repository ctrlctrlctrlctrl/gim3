package com.guo.im.server.core.biz.model;

import java.io.Serializable;

/**
 * @author ： gyj
 * @date ：2025/12/27 12:44
 * @modifiedBy ：
 */
public record BizCommand (
        String bindKey,
        String deviceId,
        Object payload
) implements Serializable {
}
