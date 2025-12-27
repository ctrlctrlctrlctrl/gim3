package com.guo.im.server.core.registry.bindkey;

import java.util.List;

public interface BindkeyChangeListener {

    void onBindkeysChanged(String type, List<BindkeyRegistration> bindkeys);

}
