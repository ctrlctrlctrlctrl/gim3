package com.guo.im.server.core.dispatch;

import com.guo.im.server.core.message.DispatchMessage;

public interface MessageDispatcher {

    void dispatch(DispatchMessage dispatchMessage);

}
