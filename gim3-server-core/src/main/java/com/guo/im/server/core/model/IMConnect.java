package com.guo.im.server.core.model;

import java.io.Serializable;

/**
 * @author ： gyj
 * @date ：2025/12/27 14:05
 * @modifiedBy ：
 */
public record IMConnect (
        String instanceId,
        String pointId,
        String connectId,
        BindKey bindKey
) implements Serializable {
}