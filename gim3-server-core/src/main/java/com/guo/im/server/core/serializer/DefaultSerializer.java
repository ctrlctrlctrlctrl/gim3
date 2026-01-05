package com.guo.im.server.core.serializer;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        return JSONUtil.toJsonStr(object).getBytes();
    }

    @Override
    protected Object doDeserialize(byte[] bytes) {
        String json = new String(bytes);
        return JSONUtil.toBean(json, Object.class);
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
