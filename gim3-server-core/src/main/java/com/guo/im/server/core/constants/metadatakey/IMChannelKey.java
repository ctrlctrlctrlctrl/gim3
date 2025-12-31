package com.guo.im.server.core.constants.metadatakey;

import com.guo.im.common.collection.KeyRef;

/**
 * @author ： gyj
 * @date ：2025/12/31 10:02
 * @modifiedBy ：
 */
public interface IMChannelKey {

    KeyRef<String> INSTANCE_ID = GeneralKey.INSTANCE_ID;

    KeyRef<String> ENDPOINT_ID = GeneralKey.ENDPOINT_ID;

    KeyRef<String> BINDKEY = GeneralKey.BINDKEY;

    KeyRef<String> DEVICE_ID = GeneralKey.DEVICE_ID;

}