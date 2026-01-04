package com.guo.im.server.core.exception;

import com.guo.im.server.core.exception.model.MessageExceptionContext;

/**
 * @author ： gyj
 * @date ：2025/12/27 15:53
 * @modifiedBy ：
 */
public interface MessageExceptionHandler <T> {

    void handleException(MessageExceptionContext<T> messageExceptionContext);

}
