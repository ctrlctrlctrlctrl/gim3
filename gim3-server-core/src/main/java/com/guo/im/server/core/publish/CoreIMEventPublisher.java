package com.guo.im.server.core.publish;

import com.guo.im.server.core.event.IMEvent;
import com.guo.im.server.core.listener.IMEventListener;

import java.util.List;

/**
 * @author ： gyj
 * @date ：2025/12/27 15:19
 * @modifiedBy ：
 */
public class CoreIMEventPublisher implements IMEventPublisher {

    private final List<IMEventListener> coreIMEventListeners;

    public CoreIMEventPublisher(List<IMEventListener> coreIMEventListeners) {
        this.coreIMEventListeners = coreIMEventListeners;
    }


    @Override
    public void publish(IMEvent imEvent) {
        coreIMEventListeners.forEach(listener -> {
            if (listener.supports(imEvent)){
                listener.onEvent(imEvent);
            }
        });
    }
}
