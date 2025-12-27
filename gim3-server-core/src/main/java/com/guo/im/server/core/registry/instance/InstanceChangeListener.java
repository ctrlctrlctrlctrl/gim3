package com.guo.im.server.core.registry.instance;

public interface InstanceChangeListener {

    void onInstancesChanged(String type,InstanceRegistration instance);

}
