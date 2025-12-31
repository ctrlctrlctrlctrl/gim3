package com.guo.im.common.collection;

import java.util.Map;

/**
 * @author ： gyj
 * @date ：2025/12/30 16:18
 * @modifiedBy ：
 */
public class KeyRef<T> {

    private final String key;

    public KeyRef(String key) {
        this.key = key;
    }

    public void put(Map<String, Object> map, T value) {
        map.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public T get(Map<String, Object> map) {
        if (map == null) {
            return null;
        }
        return (T) map.get(key);
    }

    public String key() {
        return key;
    }
}
