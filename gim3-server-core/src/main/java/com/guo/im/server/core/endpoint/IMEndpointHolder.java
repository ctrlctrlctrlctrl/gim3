package com.guo.im.server.core.endpoint;

import com.guo.im.server.core.channel.IMChannel;
import org.apache.commons.lang3.Validate;

import java.util.IdentityHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ： gyj
 * @date ：2025/12/27 20:49
 * @modifiedBy ：
 */
public class IMEndpointHolder {

    private static final IdentityHashMap<IMEndpoint, String> IMENDPOINT_MAP = new IdentityHashMap<>();

    public static void putEndpoint(IMEndpoint imEndpoint, String endpointId) {

        Validate.isTrue(!IMENDPOINT_MAP.containsKey(imEndpoint), "端点已存在，不可重复注册");

        IMENDPOINT_MAP.put(imEndpoint, endpointId);
    }

    public static String getEndpointId(IMEndpoint imEndpoint) {
        return IMENDPOINT_MAP.get(imEndpoint);
    }

    public static void removeEndpoint(IMEndpoint imEndpoint) {
        IMENDPOINT_MAP.remove(imEndpoint);
    }

}
