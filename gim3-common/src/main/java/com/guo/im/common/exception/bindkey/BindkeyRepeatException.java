package com.guo.im.common.exception.bindkey;

import com.guo.im.common.exception.IMException;
import lombok.AllArgsConstructor;

/**
 * @author ： gyj
 * @date ：2025/12/30 19:52
 * @modifiedBy ：
 */
@AllArgsConstructor
public class BindkeyRepeatException extends IMException {

    private String bindKey;

    private String deviceId;

}
