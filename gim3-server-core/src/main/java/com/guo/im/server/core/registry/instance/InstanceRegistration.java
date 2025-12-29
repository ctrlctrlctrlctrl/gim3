package com.guo.im.server.core.registry.instance;

import java.io.Serializable;
import java.util.Map;

public record InstanceRegistration(
        String instanceId,
        Map<String,Object> metadata
) implements Serializable {
}
