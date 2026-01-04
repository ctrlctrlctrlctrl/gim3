package com.guo.im.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SerializerVersionEnum {

    DEFAULT(1, 1, "default");

    private final int type;
    private final int version;
    private final String name;

}
