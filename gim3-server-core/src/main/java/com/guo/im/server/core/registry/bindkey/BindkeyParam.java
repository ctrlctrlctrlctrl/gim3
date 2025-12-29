package com.guo.im.server.core.registry.bindkey;

/**
 * @author ： gyj
 * @date ：2025/12/29 20:28
 * @modifiedBy ：
 */
public record BindkeyParam (
        String bindKey,
        String deviceId,
        String connectId
) {
}
