package com.guo.im.common.exception.serializer;

import com.guo.im.common.exception.IMException;

/**
 * @author ： gyj
 * @date ：2026/1/2 16:50
 * @modifiedBy ：
 */
public class SerializerNotFoundException extends IMException {

    public SerializerNotFoundException(String message) {
        super(message);
    }

    public SerializerNotFoundException() {
    }
}
