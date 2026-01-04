package com.guo.im.server.core.serializer;

/**
 * @author ： gyj
 * @date ：2026/1/2 16:12
 * @modifiedBy ：
 */
public interface SerializationProtocol {

    /**
     * 序列化类型（可理解为业务类型 / 协议类型）
     */
    int type();

    /**
     * 序列化版本
     */
    int version();

}
