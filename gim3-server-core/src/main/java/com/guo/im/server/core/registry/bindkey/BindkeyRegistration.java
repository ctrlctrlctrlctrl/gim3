package com.guo.im.server.core.registry.bindkey;

import java.io.Serializable;
import java.util.Map;

public record BindkeyRegistration(
        String bindKey,
        String deviceId,
        String connectId,
        Map<String,Object> metadata
) implements Serializable {
}
