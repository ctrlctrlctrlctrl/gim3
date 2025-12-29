package com.guo.im.server.core.exception;

import com.guo.im.server.core.dispatch.DispatchMessage;

/**
 * @author ： gyj
 * @date ：2025/12/27 15:53
 * @modifiedBy ：
 */
public interface MessageExceptionHandler {

    void handleException(DispatchMessage dispatchMessage, String reason);

}
