package com.guo.im.server.core.publish;

import cn.hutool.core.collection.CollUtil;
import com.guo.im.server.core.event.IMEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ： gyj
 * @date ：2025/12/27 15:18
 * @modifiedBy ：
 */
public class CompositeIMEventPublisher implements IMEventPublisher {


    private final List<IMEventPublisher> imEventPublishers = new ArrayList<>();

    public CompositeIMEventPublisher(List<IMEventPublisher> imEventPublishers) {
        if (CollUtil.isNotEmpty(imEventPublishers)){
            this.imEventPublishers.addAll(imEventPublishers);
        }
    }

    @Override
    public void publish(IMEvent imEvent) {
        imEventPublishers.forEach(imEventPublisher -> imEventPublisher.publish(imEvent));
    }
}
