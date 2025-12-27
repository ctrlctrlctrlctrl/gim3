package com.guo.im.server.core.publish;

import com.guo.im.server.core.event.IMEvent;

import java.util.List;

/**
 * @author ： gyj
 * @date ：2025/12/27 15:18
 * @modifiedBy ：
 */
public class CompositeIMEventPublisher implements IMEventPublisher {


    private final List<IMEventPublisher> imEventPublishers;

    public CompositeIMEventPublisher(List<IMEventPublisher> imEventPublishers) {
        this.imEventPublishers = imEventPublishers;
    }

    @Override
    public void publish(IMEvent imEvent) {
        imEventPublishers.forEach(imEventPublisher -> imEventPublisher.publish(imEvent));
    }
}
