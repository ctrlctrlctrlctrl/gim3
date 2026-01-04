package com.guo.im.server.core.serializer;

import com.guo.im.common.enums.SerializerVersionEnum;

/**
 * @author ： gyj
 * @date ：2026/1/2 16:16
 * @modifiedBy ：
 */
public class DefaultSerializer extends AbstractSerializer {

    private static final SerializerVersionEnum serializerVersion = SerializerVersionEnum.DEFAULT;

    @Override
    protected byte[] doSerialize(Object object) {
        return new byte[0];
    }

    @Override
    protected Object doDeserialize(byte[] bytes) {
        return null;
    }

    @Override
    public int type() {
        return serializerVersion.getType();
    }

    @Override
    public int version() {
        return serializerVersion.getVersion();
    }
}
