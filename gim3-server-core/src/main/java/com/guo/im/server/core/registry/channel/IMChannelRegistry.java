package com.guo.im.server.core.registry.channel;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IMChannelRegistry {

    void register(String instanceId, String connectId, Map<String, Object> metadata);

    boolean deregister(String connectId);

    List<IMChannelRegistration> getIMChannels(Collection<String> connectIds);

    void watchIMChannels(IMChannelChangeListener listener);

}
