package com.guo.im.server.core.listener;

import com.guo.im.server.core.event.IMEvent;
import com.guo.im.server.core.event.InstanceEvent;
import com.guo.im.server.core.event.InstanceReadyEvent;
import com.guo.im.server.core.event.InstanceStopEvent;
import com.guo.im.server.core.registry.instance.InstanceRegistration;
import com.guo.im.server.core.registry.instance.InstanceRegistry;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ： gyj
 * @date ：2026/1/4 16:59
 * @modifiedBy ：
 */
@Slf4j
public class InstanceLifecycleListener implements IMEventListener {

    private final InstanceRegistry instanceRegistry;

    public InstanceLifecycleListener(InstanceRegistry instanceRegistry) {
        this.instanceRegistry = instanceRegistry;
    }

    @Override
    public boolean supports(IMEvent imEvent) {
        return imEvent instanceof InstanceStopEvent;
    }

    @Override
    public void onEvent(IMEvent imEvent) {

        log.debug("监听到Endpoint事件：{}", imEvent);

        InstanceEvent instanceEvent = (InstanceEvent) imEvent;

        InstanceRegistration instance = instanceRegistry.getInstance(instanceEvent.getInstanceId());

        switch (imEvent) {
            case InstanceStopEvent event:
                this.onInstanceClosed(event, instance);
                break;
            case InstanceReadyEvent event:
            default:
        }
    }

    private void onInstanceClosed(InstanceStopEvent event, InstanceRegistration instance) {
        instanceRegistry.deregister(event.getInstanceId());
    }
}
