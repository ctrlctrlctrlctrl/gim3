package com.guo.im.server.core.listener;

import com.guo.im.server.core.event.IMEvent;

public interface IMEventListener {

    boolean supports(IMEvent imEvent);

    void onEvent(IMEvent imEvent);

}
