package com.guo.im.server.core.exception.model;

import com.guo.im.server.core.exception.enums.MessageFailStageEnum;
import lombok.Getter;

import java.io.Serializable;

/**
 * @param stage   阶段类型
 * @param message 原始消息类型，通过泛型 T 保持类型安全
 * @param cause   异常原因
 * @author ： gyj
 * @date ：2026/1/2 19:46
 * @modifiedBy ：
 */
public record MessageExceptionContext<T>(
        MessageFailStageEnum stage,
        T message,
        Throwable cause
) implements Serializable {
}