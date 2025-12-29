package com.guo.im.server.core.context;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * @author ： gyj
 * @date ：2025/12/27 19:28
 * @modifiedBy ：
 */
public class InstanceContext {

    private static final  TransmittableThreadLocal<String> INSTANCE_ID =new TransmittableThreadLocal<>();

    public static void setInstanceId(String instanceId){
        INSTANCE_ID.set(instanceId);
    }

    public static String getInstanceId(){
        return INSTANCE_ID.get();
    }

    public static void removeInstanceId(){
        INSTANCE_ID.remove();
    }

}
