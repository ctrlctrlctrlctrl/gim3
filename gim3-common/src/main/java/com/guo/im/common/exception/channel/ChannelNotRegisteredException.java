package com.guo.im.common.exception.channel;

import com.guo.im.common.exception.IMException;

/**
 * @author ： gyj
 * @date ：2025/12/27 20:32
 * @modifiedBy ：
 */
public class ChannelNotRegisteredException extends IMException {

    public ChannelNotRegisteredException() {
        super();
    }

    public ChannelNotRegisteredException(String message) {
        super(message);
    }
}
