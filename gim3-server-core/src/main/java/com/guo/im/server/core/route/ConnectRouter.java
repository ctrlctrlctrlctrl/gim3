package com.guo.im.server.core.route;

import com.guo.im.server.core.registry.channel.IMChannelRegistration;

public interface ConnectRouter {

    IMChannelRegistration route(String connectId);

}
