package com.guo.im.server.core.registry.instance;

import com.guo.im.server.core.registry.enums.RegistryActionEnum;

public interface InstanceChangeListener {

    void onInstancesChanged(RegistryActionEnum registryAction, InstanceRegistration instance);

}
