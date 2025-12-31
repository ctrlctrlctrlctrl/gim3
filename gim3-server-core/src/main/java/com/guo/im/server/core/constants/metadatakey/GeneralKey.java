package com.guo.im.server.core.constants.metadatakey;

import com.guo.im.common.collection.KeyRef;

/**
 * @author ： gyj
 * @date ：2025/12/31 10:16
 * @modifiedBy ：
 */
public interface GeneralKey {

    KeyRef<String> INSTANCE_ID = new KeyRef<>("INSTANCE_ID");

    KeyRef<String> ENDPOINT_ID = new KeyRef<>("ENDPOINT_ID");

    KeyRef<String> CONNECT_ID = new KeyRef<>("CONNECT_ID");

    KeyRef<String> BINDKEY = new KeyRef<>("BINDKEY");

    KeyRef<String> DEVICE_ID = new KeyRef<>("DEVICE_ID");

}
