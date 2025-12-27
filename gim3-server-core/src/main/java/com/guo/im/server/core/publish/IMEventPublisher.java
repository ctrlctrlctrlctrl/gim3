package com.guo.im.server.core.publish;

import com.guo.im.server.core.event.IMEvent;

public interface IMEventPublisher {

    void publish(IMEvent imEvent);

}