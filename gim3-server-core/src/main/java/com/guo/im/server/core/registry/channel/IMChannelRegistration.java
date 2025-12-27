package com.guo.im.server.core.registry.channel;

import java.io.Serializable;
import java.util.Map;

public record IMChannelRegistration(
        String connectId,
        String instanceId,
        Map<String,Object> metadata
) implements Serializable {
}
