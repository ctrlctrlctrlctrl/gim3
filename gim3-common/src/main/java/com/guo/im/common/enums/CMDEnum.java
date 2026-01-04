package com.guo.im.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CMDEnum {


    //****************************** 900-999 系统内置异常 ******************************//
    CHANNEL_NOT_REGISTERED(900, "channel未注册"),




    //****************************** 1000 业务处理 ******************************//
    MESAAGE_DELIVER(1000, "消息投递"),
    ;

    private final int cmd;
    private final String desc;


}
