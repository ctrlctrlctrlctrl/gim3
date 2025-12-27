package com.guo.im.server.core.dispatch;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ： gyj
 * @date ：2025/12/27 13:47
 * @modifiedBy ：
 */
public abstract class MessagePump {

    private final Collection<MessageProcessor> messageProcessors;

    private boolean isStart = false;

    private ExecutorService work = Executors.newSingleThreadExecutor();

    public MessagePump(Collection<MessageProcessor> messageProcessors) {
        this.messageProcessors = messageProcessors;
    }

    abstract void run();

    public void start() {
        synchronized (this) {
            if (!isStart){
                isStart = true;
                work.submit(this::run);
            }
        }
    }

    public  void stop() {
        synchronized (this){
            if (isStart){
                work.shutdown();
                work = Executors.newSingleThreadExecutor();
                isStart = false;
            }
        }
    }
}
