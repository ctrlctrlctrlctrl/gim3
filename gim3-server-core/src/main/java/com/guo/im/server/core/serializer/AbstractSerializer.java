package com.guo.im.server.core.serializer;

/**
 * @author ： gyj
 * @date ：2026/1/2 16:14
 * @modifiedBy ：
 */
public abstract class AbstractSerializer implements Serializer {
    @Override
    public byte[] serialize(Object object) {
        return new byte[0];
    }

    @Override
    public Object deserialize(byte[] bytes) {
        return null;
    }

    protected abstract byte[] doSerialize(Object object);

    protected abstract Object doDeserialize(byte[] bytes);
}
