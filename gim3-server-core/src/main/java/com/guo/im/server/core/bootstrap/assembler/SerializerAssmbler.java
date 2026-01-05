package com.guo.im.server.core.bootstrap.assembler;

import com.guo.im.server.core.bootstrap.BootstrapContext;
import com.guo.im.server.core.serializer.DefaultSerializer;
import com.guo.im.server.core.serializer.Serializer;
import com.guo.im.server.core.serializer.SerializerHolder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ： gyj
 * @date ：2026/1/5 20:01
 * @modifiedBy ：
 */
@Slf4j
public class SerializerAssmbler {

    public static Serializer assemble(BootstrapContext bootstrapContext) {
        if (bootstrapContext.getSerializer() != null){
            return bootstrapContext.getSerializer();
        } else {

            Serializer serializer = SerializerHolder.getSerializer(bootstrapContext.getSerializerConfig().getSerializerType(), bootstrapContext.getSerializerConfig().getSerializerVersion());
            if (serializer != null){
                return serializer;
            } else {
                log.warn("配置未找到对应的序列化器，使用默认序列化器");
                return new DefaultSerializer();
            }
        }
    }

}
