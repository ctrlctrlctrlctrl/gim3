package com.guo.im.server.core.channel;

import com.guo.im.common.exception.channel.ChannelNotRegisteredException;
import com.guo.im.server.common.message.InBoundMessage;
import com.guo.im.server.common.message.OutboundMessage;
import com.guo.im.server.core.context.BindKeyContext;
import com.guo.im.server.core.context.InstanceContext;
import com.guo.im.server.core.model.IMConnect;
import lombok.SneakyThrows;
import org.apache.commons.lang3.Validate;

public abstract class IMChannel {

    private IMConnect imConnect;

    abstract String getConnectId();

    abstract boolean isActive();

    abstract boolean write(OutboundMessage outboundMessage);

    abstract boolean close();

    @SneakyThrows
    void read(InBoundMessage inboundMessage) {

        if (imConnect == null)
            throw new ChannelNotRegisteredException();

        InstanceContext.setInstanceId(imConnect.instanceId());
        BindKeyContext.setBindKey(imConnect.bindKey());

        //todo 调用业务处理中心

        InstanceContext.removeInstanceId();
        BindKeyContext.removeBindKey();
    }

    protected void setIMConnect(IMConnect imConnect) {
        this.imConnect = imConnect;
    }

}
