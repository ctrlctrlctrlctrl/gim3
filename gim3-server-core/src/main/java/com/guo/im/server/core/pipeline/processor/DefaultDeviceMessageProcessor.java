package com.guo.im.server.core.pipeline.processor;

import cn.hutool.core.util.IdUtil;
import com.google.protobuf.ByteString;
import com.guo.im.common.enums.CMDEnum;
import com.guo.im.common.exception.serializer.SerializerNotFoundException;
import com.guo.im.server.common.message.OutboundMessage;
import com.guo.im.server.common.message.OutboundMessageOrBuilder;
import com.guo.im.server.core.channel.IMChannel;
import com.guo.im.server.core.channel.IMChannelHolder;
import com.guo.im.server.core.config.SerializerConfig;
import com.guo.im.server.core.instance.InstanceHolder;
import com.guo.im.server.core.internal.model.ClusterMessageOuterClass;
import com.guo.im.server.core.pipeline.MessageHandleModeEnum;
import com.guo.im.server.core.pipeline.dispatch.DefaultMessageDispatcher;
import com.guo.im.server.core.serializer.Serializer;
import com.guo.im.server.core.serializer.SerializerHolder;
import lombok.SneakyThrows;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author ： gyj
 * @date ：2026/1/3 10:59
 * @modifiedBy ：
 */
public class DefaultDeviceMessageProcessor extends AbstractClusterMessageProcessor {

    private final SerializerConfig serializerConfig;

    public DefaultDeviceMessageProcessor(SerializerConfig serializerConfig) {
        if (serializerConfig == null) {
            serializerConfig = new SerializerConfig();
        }
        this.serializerConfig = serializerConfig;
    }


    @Override
    public Integer topic() {
        return 1;
    }

    @Override
    public MessageHandleModeEnum handleMode() {
        return MessageHandleModeEnum.BATCH;
    }

    @Override
    public void onMessage(ClusterMessageOuterClass.ClusterMessage message) {
    }

    @SneakyThrows
    @Override
    public void onMessage(List<ClusterMessageOuterClass.ClusterMessage> messages) {

        Collection<IMChannel> channels = IMChannelHolder.getChannels();

        // 消息推送
        for (IMChannel channel : channels) {

            // 从消息储存取出消息
            List<Object> objects = DefaultMessageDispatcher.datastore.remove(channel.getConnectId());

            //消息不为空则进行推送
            for (Object object : objects) {

                // 正文序列化
                Serializer serializer = SerializerHolder.getSerializer(serializerConfig.getSerializerType(), serializerConfig.getSerializerVersion());
                if (serializer == null) {
                    throw new SerializerNotFoundException();
                }
                ByteString byteString = ByteString.copyFrom(serializer.serialize(object));
                OutboundMessage.Builder builder = OutboundMessage.newBuilder();
                builder.setMessageId(IdUtil.getSnowflakeNextIdStr())
                        .setCmd(CMDEnum.MESAAGE_DELIVER.getCmd())
                        .setTimestamp(System.currentTimeMillis())
                        .setPayloadType(serializer.type())
                        .setPayloadVersion(serializer.version())
                        .setPayload(byteString);
                channel.write(builder.build());//推送
            }

        }

    }
}
