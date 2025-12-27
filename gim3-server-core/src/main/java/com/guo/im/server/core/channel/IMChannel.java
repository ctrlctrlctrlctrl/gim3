package com.guo.im.server.core.channel;

import com.guo.im.server.common.message.OutboundMessageOuterClass;

public interface IMChannel {

    String getConnectId();

    boolean isActive();

    boolean write(OutboundMessageOuterClass.OutboundMessage outboundMessage);

    boolean close();

}
