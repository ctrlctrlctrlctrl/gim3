package com.guo.im.server.core.cache;

import java.util.concurrent.TimeUnit;

public interface Cache {

    void put(String key, Object value);

    <E> E get(String key, Class<E> clazz);

    void delete(String key);

    void expire(String key, long timeout, TimeUnit timeUnit);

    void increment(String key, long delta);

}