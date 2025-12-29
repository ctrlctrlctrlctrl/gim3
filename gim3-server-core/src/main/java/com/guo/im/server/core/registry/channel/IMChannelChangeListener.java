package com.guo.im.server.core.registry.channel;

import com.guo.im.server.core.registry.enums.RegistryActionEnum;

public interface IMChannelChangeListener {

    void onIMChannelsChanged(RegistryActionEnum registryAction, IMChannelRegistration imChannels);

}