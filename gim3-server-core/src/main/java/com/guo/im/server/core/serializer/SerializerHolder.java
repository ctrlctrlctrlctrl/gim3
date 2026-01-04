package com.guo.im.server.core.serializer;

import com.guo.im.common.enums.SerializerVersionEnum;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ： gyj
 * @date ：2026/1/2 16:32
 * @modifiedBy ：
 */
public class SerializerHolder {

    private static final Map<Integer, Map<Integer, Serializer>> SERIALIZER_MAP = new HashMap<>();

    static {

        DefaultSerializer defaultSerializer = new DefaultSerializer();
        SerializerHolder.register(defaultSerializer.type(), SerializerVersionEnum.DEFAULT.getVersion(), defaultSerializer);

    }

    public static void register(Integer type, Integer version, Serializer serializer) {
        Map<Integer, Serializer> value = SERIALIZER_MAP.getOrDefault(type, new HashMap<>());
        value.put(version, serializer);
    }

    public static Serializer getSerializer(Integer type, Integer version) {
        Map<Integer, Serializer> value = SERIALIZER_MAP.get(type);
        if (value == null) {
            return null;
        }
        return value.get(version);
    }

}
