package com.guo.im.server.core.endpoint;

public interface IMEndpoint {

    void start();

    void stop();

    boolean isReady();
}
