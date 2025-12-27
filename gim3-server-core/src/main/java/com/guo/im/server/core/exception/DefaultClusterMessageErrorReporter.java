package com.guo.im.server.core.exception;

import com.guo.im.server.core.exception.model.ClusterMessageErrorContext;

/**
 * @author ： gyj
 * @date ：2025/12/27 16:13
 * @modifiedBy ：
 */
public class DefaultClusterMessageErrorReporter implements ClusterMessageErrorReporter{


    @Override
    public void reportMessageError(ClusterMessageErrorContext context) {
        //todo 默认将消息重新投递会源节点，如果源节点下线则调用本实例实现的MessageExceptionHandler接口的实现类
    }
}
