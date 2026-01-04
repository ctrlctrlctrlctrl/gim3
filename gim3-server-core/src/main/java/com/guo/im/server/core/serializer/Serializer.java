package com.guo.im.server.core.serializer;

import com.guo.im.common.exception.serializer.DeSerializerFailException;
import com.guo.im.common.exception.serializer.SerializerFailException;

/**
 * @author ： gyj
 * @date ：2026/1/2 16:13
 * @modifiedBy ：
 */
public interface Serializer extends SerializationProtocol {

    /**
     * 将对象序列化为字节数组
     */
    byte[] serialize(Object object) throws SerializerFailException;

    /**
     * 将字节数组反序列化为对象
     */
    Object deserialize(byte[] bytes) throws DeSerializerFailException;
}
