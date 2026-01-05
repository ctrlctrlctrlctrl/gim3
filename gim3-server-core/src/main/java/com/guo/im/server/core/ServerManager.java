package com.guo.im.server.core;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.IdUtil;
import com.guo.im.server.core.endpoint.IMEndpoint;
import com.guo.im.server.core.endpoint.IMEndpointHolder;
import com.guo.im.server.core.endpoint.IMEndpointLifecycle;
import com.guo.im.server.core.event.*;
import com.guo.im.server.core.instance.InstanceHolder;
import com.guo.im.server.core.publish.CompositeIMEventPublisher;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.*;

/**
 * @author ： gyj
 * @date ：2025/12/27 15:31
 * @modifiedBy ：
 */
@Slf4j
public class ServerManager {

    private final String instanceId;
    private volatile boolean isReady = false;

    private final Collection<IMEndpoint> imEndpoints = new ArrayList<>();
    private final CompositeIMEventPublisher publisher;
    private final IMEndpointLifecycle lifecycle;
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public ServerManager(String instanceId, Collection<IMEndpoint> imEndpoints, CompositeIMEventPublisher publisher, IMEndpointLifecycle lifecycle) {
        this.instanceId = instanceId;
        if (CollUtil.isNotEmpty(imEndpoints)) {
            this.imEndpoints.addAll(imEndpoints);
        }
        this.publisher = publisher;
        this.lifecycle = lifecycle;
        InstanceHolder.register(instanceId, this);
        executorService.scheduleWithFixedDelay(new EndpointMonitorTask(imEndpoints), 0, 500, TimeUnit.MILLISECONDS);
    }

    public ServerManager(Collection<IMEndpoint> imEndpoints, CompositeIMEventPublisher publisher, IMEndpointLifecycle lifecycle) {
        this(IdUtil.getSnowflakeNextIdStr(), imEndpoints, publisher, lifecycle);
    }

    public synchronized void start() {

        if (CollUtil.isNotEmpty(imEndpoints)) {
            for (IMEndpoint imEndpoint : imEndpoints) {
                String endpointId = IdUtil.getSnowflakeNextIdStr();
                IMEndpointHolder.putEndpoint(imEndpoint, endpointId);

                if (!imEndpoint.isReady()) {
                    imEndpoint.start();

                    try {
                        // 发布端点启动事件
                        this.publish(new EndpointStartEvent(instanceId, endpointId));
                    } catch (Exception e) {
                        // 记录日志，不影响其他端点
                        log.error("Endpoint {} 启动失败", imEndpoint, e);
                    }
                }
            }
        }

        // 发布实例启动事件
        this.publish(new InstanceStartEvent(instanceId));
    }

    public synchronized void stop() {
        if (CollUtil.isNotEmpty(imEndpoints)) {
            for (IMEndpoint imEndpoint : imEndpoints) {
                if (imEndpoint.isReady()) {
                    imEndpoint.stop();

                    try {
                        // 发布端点停止事件
                        String endpointId = IMEndpointHolder.getEndpointId(imEndpoint);
                        this.publish(new EndpointStopEvent(instanceId, endpointId));
                    } catch (Exception e) {
                        // 记录日志，不影响其他端点
                        log.error("Endpoint {} 停止失败", imEndpoint, e);
                    }
                }
            }
        }

        InstanceHolder.unregister(instanceId);

        this.publish(new InstanceStopEvent(instanceId));
    }

    public boolean isReady() {
        return isReady;
    }

    // 停止 ServerManager 时调用
    public void shutdown() {
        executorService.shutdown(); // 立刻打断 sleep
    }

    public void publish(IMEvent imEvent) {
        publisher.publish(imEvent);
    }

    private class EndpointMonitorTask implements Runnable {

        private final ConcurrentHashMap<IMEndpoint, Boolean> endpointReadyMap;

        private EndpointMonitorTask(Collection<IMEndpoint> IMEndpoints) {
            endpointReadyMap = new ConcurrentHashMap<>();
            for (IMEndpoint imEndpoint : IMEndpoints) {
                endpointReadyMap.put(imEndpoint, false);
            }
        }

        @Override
        public void run() {

            if (Thread.currentThread().isInterrupted()) return;// 线程被中断则退出

            boolean oldMark = isReady;

            endpointReadyMap.forEach((key, ready) -> {
                if (!ready) {
                    if (key.isReady()) {
                        endpointReadyMap.put(key, true);
                        lifecycle.endpointCreated(key);// 触发Endpoint的开始生命周期
                    }
                } else {
                    if (!key.isReady()) {
                        endpointReadyMap.put(key, false);
                        lifecycle.endpointClosed(key);// 触发Endpoint的结束生命周期
                    }
                }
            });

            // 重新计算整体就绪状态
            isReady = endpointReadyMap.values().stream().allMatch(Boolean::booleanValue);

            // 如果整体就绪状态发生变化，可以考虑发布相应事件
            if (oldMark != isReady && isReady) {
                publish(new InstanceReadyEvent(instanceId));
            }
        }
    }

}
