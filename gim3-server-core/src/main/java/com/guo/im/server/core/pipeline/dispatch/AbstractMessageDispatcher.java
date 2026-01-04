package com.guo.im.server.core.pipeline.dispatch;

import com.guo.im.server.core.exception.MessageExceptionHandler;
import com.guo.im.server.core.exception.enums.MessageFailStageEnum;
import com.guo.im.server.core.exception.model.MessageExceptionContext;
import lombok.Setter;

/**
 * @author ： gyj
 * @date ：2026/1/2 14:58
 * @modifiedBy ：
 */
public abstract class AbstractMessageDispatcher <T extends DispatchMessage> implements MessageDispatcher<T> {

    @Setter
    private static MessageExceptionHandler<DispatchMessage> messageExceptionHandler = new MessageExceptionHandler<DispatchMessage>() {
        @Override
        public void handleException(MessageExceptionContext<DispatchMessage> messageExceptionContext) {
            long startTime = System.currentTimeMillis();
            System.out.println("消息处理失败，开始重试，耗时：" + (System.currentTimeMillis() - startTime) + "ms");
        }
    };


    @Override
    public void dispatch(T dispatchMessage) {
        try {
            //前置处理 日志
            doDispatch(dispatchMessage);
            //后置处理 失败后重试等逻辑
        } catch (Exception e) {
            MessageExceptionContext<DispatchMessage> dispatchMessageMessageExceptionContext = new MessageExceptionContext<>(MessageFailStageEnum.DISPATCH_FAIL, dispatchMessage, e);
            messageExceptionHandler.handleException(dispatchMessageMessageExceptionContext);
        }
    }

    protected abstract void doDispatch(T dispatchMessage);

}
