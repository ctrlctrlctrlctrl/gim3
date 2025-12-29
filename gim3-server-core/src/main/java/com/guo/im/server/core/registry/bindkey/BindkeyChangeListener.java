package com.guo.im.server.core.registry.bindkey;

import com.guo.im.server.core.registry.enums.RegistryActionEnum;

import java.util.List;

public interface BindkeyChangeListener {

    void onBindkeysChanged(RegistryActionEnum registryAction, BindkeyRegistration bindkey);

}
