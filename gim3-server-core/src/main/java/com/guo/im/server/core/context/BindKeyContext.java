package com.guo.im.server.core.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.guo.im.server.core.model.BindKey;

/**
 * @author ： gyj
 * @date ：2025/12/27 20:38
 * @modifiedBy ：
 */
public class BindKeyContext {

    private static final TransmittableThreadLocal<BindKey> BINDKEY_CONTEXT =new TransmittableThreadLocal<>();

    public static void setBindKey(BindKey bindKey){
        BINDKEY_CONTEXT.set(bindKey);
    }

    public static BindKey getBindKey(){
        return BINDKEY_CONTEXT.get();
    }

    public static void removeBindKey(){
        BINDKEY_CONTEXT.remove();
    }

}
