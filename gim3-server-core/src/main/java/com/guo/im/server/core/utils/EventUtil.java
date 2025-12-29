package com.guo.im.server.core.utils;

import com.guo.im.server.core.ServerManager;
import com.guo.im.server.core.context.InstanceContext;
import com.guo.im.server.core.event.IMEvent;
import com.guo.im.server.core.instance.InstanceHolder;
import org.apache.commons.lang3.Validate;

/**
 * @author ： gyj
 * @date ：2025/12/29 19:04
 * @modifiedBy ：
 */
public class EventUtil {

    public void pulishEvent(IMEvent imEvent) {

        String instanceId = InstanceContext.getInstanceId();

        Validate.notBlank(instanceId, "实例上下文不存在");
    }

    public void pulishEvent(String instanceId, IMEvent imEvent) {

        ServerManager serverManager = InstanceHolder.getServerManager(instanceId);

        Validate.notNull(serverManager, "实例不存在");

        serverManager.publish(imEvent);

    }

}
