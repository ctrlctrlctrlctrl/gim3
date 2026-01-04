package com.guo.im.common.exception.channel;

import com.guo.im.common.exception.IMException;
import lombok.AllArgsConstructor;

/**
 * @author ： gyj
 * @date ：2025/12/30 20:07
 * @modifiedBy ：
 */
@AllArgsConstructor
public class ChannelRegistryRepeatException extends IMException {

    private String connectId;

    public ChannelRegistryRepeatException(String connectId,String message) {
        super(message);
        this.connectId = connectId;
    }
}
