package com.guo.im.server.core.pipeline.pump;

import com.guo.im.server.core.pipeline.processor.ClusterMessageProcessor;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ： gyj
 * @date ：2025/12/27 13:47
 * @modifiedBy ：
 */
public abstract class MessagePump {

    protected final Collection<ClusterMessageProcessor> messageProcessors;

    protected boolean isStart = false;

    private ExecutorService work = Executors.newSingleThreadExecutor();

    public MessagePump(Collection<ClusterMessageProcessor> messageProcessors) {
        this.messageProcessors = messageProcessors;
    }

    abstract void doRun();

    public void start() {
        synchronized (this) {
            if (!isStart){
                isStart = true;
                work.submit(this::doRun);
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
