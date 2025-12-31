package com.guo.im.server.core.registry.bindkey;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface BindkeyRegistry {

    void register(String bindKey, String deviceId, String connectId, Map<String, Object> metadata);

    boolean deregister(String bindKey, String deviceId);

    boolean deregister(String connectId);

    List<BindkeyRegistration> getBindkeys(Collection<BindkeyParam> bindkeyParams);

    void watchBindkeys(BindkeyChangeListener listener);

}
