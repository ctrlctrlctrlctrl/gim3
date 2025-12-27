package com.guo.im.server.core;

import cn.hutool.core.collection.CollUtil;
import com.guo.im.server.core.endpoint.IMEndpoint;
import com.guo.im.server.core.event.IMEvent;
import com.guo.im.server.core.publish.CompositeIMEventPublisher;

import java.util.Collection;

/**
 * @author ： gyj
 * @date ：2025/12/27 15:31
 * @modifiedBy ：
 */
public class ServerManager {


    private final Collection<IMEndpoint> IMEndpoints;
    private final CompositeIMEventPublisher publisher;

    public ServerManager(Collection<IMEndpoint> IMEndpoints, CompositeIMEventPublisher publisher) {
        this.IMEndpoints = IMEndpoints;
        this.publisher = publisher;
    }

    public void start() {
        if (CollUtil.isNotEmpty(IMEndpoints)){
            for (IMEndpoint imEndpoint : IMEndpoints) {
                if (!imEndpoint.isReady()){
                    imEndpoint.start();
                }
            }
        }
    }

    public void stop() {
        if (CollUtil.isNotEmpty(IMEndpoints)){
            for (IMEndpoint imEndpoint : IMEndpoints) {
                if (imEndpoint.isReady()){
                    imEndpoint.stop();
                }
            }
        }
    }

    public boolean isReady() {
        if (CollUtil.isNotEmpty(IMEndpoints)){
            for (IMEndpoint imEndpoint : IMEndpoints) {
                if (!imEndpoint.isReady()){
                    return false;
                }
            }
        }
        return true;
    }

    public void publish(IMEvent imEvent) {
        publisher.publish(imEvent);
    }

}
