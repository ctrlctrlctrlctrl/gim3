package com.guo.im.server.core.instance;

import com.guo.im.server.core.ServerManager;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ： gyj
 * @date ：2025/12/29 19:05
 * @modifiedBy ：
 */
public class InstanceHolder {

    private static final ConcurrentHashMap<String, ServerManager> SERVER_MANAGERS = new ConcurrentHashMap<>();

    public static ServerManager getServerManager(String instanceId) {
        return SERVER_MANAGERS.get(instanceId);
    }

    public static void register(String instanceId, ServerManager serverManager) {
        SERVER_MANAGERS.put(instanceId, serverManager);
    }

    public static void unregister(String instanceId) {
        SERVER_MANAGERS.remove(instanceId);
    }

}
