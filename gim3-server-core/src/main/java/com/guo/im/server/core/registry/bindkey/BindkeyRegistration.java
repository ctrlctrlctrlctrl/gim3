package com.guo.im.server.core.registry.bindkey;

import java.io.Serializable;

public record BindkeyRegistration(
        String bindKey,
        String deviceId,
        String connectId
) implements Serializable {
}
