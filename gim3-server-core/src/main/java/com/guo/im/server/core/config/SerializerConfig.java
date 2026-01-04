package com.guo.im.server.core.config;

import com.guo.im.common.enums.SerializerVersionEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ： gyj
 * @date ：2026/1/2 16:26
 * @modifiedBy ：
 */
@Getter
@Setter
public class SerializerConfig {

    private int serializerType = SerializerVersionEnum.DEFAULT.getType();

    private int serializerVersion = SerializerVersionEnum.DEFAULT.getVersion();

}
