package com.guo.im.server.core.channel;

import com.google.protobuf.ByteString;
import com.guo.im.common.exception.channel.ChannelNotRegisteredException;
import com.guo.im.common.exception.serializer.SerializerNotFoundException;
import com.guo.im.server.common.message.InBoundMessage;
import com.guo.im.server.common.message.OutboundMessage;
import com.guo.im.server.core.context.BindKeyContext;
import com.guo.im.server.core.context.InstanceContext;
import com.guo.im.server.core.model.IMConnect;
import com.guo.im.server.core.processor.IMProcessorRouter;
import com.guo.im.server.core.route.ConnectRouter;
import com.guo.im.server.core.serializer.Serializer;
import com.guo.im.server.core.serializer.SerializerHolder;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.commons.lang3.Validate;

public abstract class IMChannel {

    private IMConnect imConnect;

    private IMProcessorRouter imProcessorRouter;

    public abstract String getConnectId();

    public abstract boolean isActive();

    public abstract boolean write(OutboundMessage outboundMessage);

    public abstract boolean close();

    @SneakyThrows
    public void read(InBoundMessage inboundMessage) {

        if (imConnect == null)
            throw new ChannelNotRegisteredException();

        InstanceContext.setInstanceId(imConnect.instanceId());
        BindKeyContext.setBindKey(imConnect.bindKey());

        Serializer serializer = SerializerHolder.getSerializer(inboundMessage.getPayloadType(), inboundMessage.getPayloadVersion());
        if (serializer == null) {
            throw new SerializerNotFoundException("找不到对应的序列化器");
        }
        Object data = serializer.deserialize(inboundMessage.getPayload().toByteArray());
        imProcessorRouter.route(inboundMessage.getCmd(), data);

        InstanceContext.removeInstanceId();
        BindKeyContext.removeBindKey();
    }

    protected void setIMConnect(IMConnect imConnect) {
        this.imConnect = imConnect;
    }

    protected IMConnect getIMConnect() {
        return imConnect;
    }

    public void setImProcessorRouter(IMProcessorRouter imProcessorRouter) {
        this.imProcessorRouter = imProcessorRouter;
    }
}
