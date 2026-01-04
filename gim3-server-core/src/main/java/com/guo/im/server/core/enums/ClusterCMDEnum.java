package com.guo.im.server.core.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClusterCMDEnum {

    MESSAGE_DELIVER(1, "消息投递至客户端"),;


    private final int cmd;
    private final String desc;


}
