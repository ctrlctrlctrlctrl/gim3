package com.guo.im.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CMDEnum {


    //****************************** 900-999 系统内置异常 ******************************//
    CHANNEL_NOT_REGISTERED(900, "channel未注册");

    private int cmd;
    private String desc;


}
