package com.guo.im.server.core.constants.metadatakey;

import com.guo.im.common.collection.KeyRef;

import java.util.Set;

public interface InstanceKey {

    KeyRef<Set<String>> ENDPOINT_IDS = new KeyRef<>("ENDPOINT_IDS");
}