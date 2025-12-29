package com.guo.im.server.core.registry.instance;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author ： gyj
 * @date ：2025/12/27 13:07
 * @modifiedBy ：
 */
public interface InstanceRegistry {

    void register(String instanceId, Map<String, Object> metadata);

    boolean deregister(String instanceId);

    List<InstanceRegistration> getInstances(Collection<String> instanceIds);

    void watchInstances(InstanceChangeListener listener);

}
