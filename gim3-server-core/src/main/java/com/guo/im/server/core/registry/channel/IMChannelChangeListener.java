package com.guo.im.server.core.registry.channel;

import java.util.List;

public interface IMChannelChangeListener {

    void onIMChannelsChanged(String type, List<IMChannelRegistration> imChannels);

}