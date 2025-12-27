package com.guo.im.server.core.exception;

import com.guo.im.server.core.exception.model.ClusterMessageErrorContext;

/**
 * @author ： gyj
 * @date ：2025/12/27 16:11
 * @modifiedBy ：
 */
public interface ClusterMessageErrorReporter {

    void reportMessageError(ClusterMessageErrorContext context);

}
