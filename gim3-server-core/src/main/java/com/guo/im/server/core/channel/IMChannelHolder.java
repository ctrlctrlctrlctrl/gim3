package com.guo.im.server.core.channel;

import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ： gyj
 * @date ：2025/12/27 15:39
 * @modifiedBy ：
 */
public class IMChannelHolder {

    private static final ConcurrentHashMap<String, IMChannel> channelMap =new ConcurrentHashMap<>();

    public static void putChannel(String connectId, IMChannel channel) {
        channelMap.put(connectId, channel);
    }

    public static IMChannel getChannel(String connectId) {
        return channelMap.get(connectId);
    }

    public static Collection<IMChannel> getChannels() {
        return channelMap.values();
    }

    public static void removeChannel(String connectId) {
        channelMap.remove(connectId);
    }

}
