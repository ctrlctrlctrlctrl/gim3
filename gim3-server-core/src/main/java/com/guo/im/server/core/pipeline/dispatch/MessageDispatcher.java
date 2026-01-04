package com.guo.im.server.core.pipeline.dispatch;

public interface MessageDispatcher <T extends DispatchMessage> {

    void dispatch(T dispatchMessage);

}
