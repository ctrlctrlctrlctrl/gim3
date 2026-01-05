package com.guo.im.server.core.endpoint;

import com.guo.im.server.core.channel.IMChannelLifecycle;

public interface IMEndpoint {

    void start();

    void stop();

    boolean isReady();

    void setIMChannelLifecycle(IMChannelLifecycle imChannelLifecycle);
}
