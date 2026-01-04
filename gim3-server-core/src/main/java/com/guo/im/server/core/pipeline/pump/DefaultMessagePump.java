package com.guo.im.server.core.pipeline.pump;

import cn.hutool.core.collection.CollUtil;
import com.guo.im.common.utils.ThreadPoolExecutorFactory;
import com.guo.im.server.core.instance.InstanceHolder;
import com.guo.im.server.core.internal.model.ClusterMessageOuterClass;
import com.guo.im.server.core.pipeline.Inlet.Inlet;
import com.guo.im.server.core.pipeline.MessageHandleModeEnum;
import com.guo.im.server.core.pipeline.processor.ClusterMessageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ： gyj
 * @date ：2026/1/2 20:00
 * @modifiedBy ：
 */
public class DefaultMessagePump extends MessagePump {

    private static final Logger log = LoggerFactory.getLogger(DefaultMessagePump.class);
    private final Inlet inlet;

    private static final ScheduledThreadPoolExecutor EXECUTOR = ThreadPoolExecutorFactory.getThreadPoolExecutor();

    private final Set<String> instanceIds = InstanceHolder.getInstanceIds();

    public DefaultMessagePump(Collection<ClusterMessageProcessor> messageProcessors, Inlet inlet) {
        super(messageProcessors);
        this.inlet = inlet;
    }

    @Override
    void doRun() {
        for (ClusterMessageProcessor messageProcessor : super.messageProcessors) {

            for (String instanceId : instanceIds) {

                EXECUTOR.schedule(new Runnable() {

                    private final String instance = instanceId;

                    @Override
                    public void run() {
                        Integer topic = messageProcessor.topic();
                        List<ClusterMessageOuterClass.ClusterMessage> receive = null;
                        try {
                            receive = inlet.receive(instance, topic, messageProcessor.batchSize());
                        } catch (Exception _e) {
                            log.error("DefaultMessagePump 的 {} 队列拉取失败。异常信息：{}", topic, _e.getMessage());
                            EXECUTOR.schedule(this, 10, TimeUnit.SECONDS);
                        }
                        if (CollUtil.isNotEmpty(receive)) {
                            if (Objects.equals(messageProcessor.handleMode(), MessageHandleModeEnum.SINGLE)) {// 单数据处理
                                receive.forEach(messageProcessor::onMessage);
                            } else if (Objects.equals(messageProcessor.handleMode(), MessageHandleModeEnum.BATCH)) {// 批量数据处理
                                messageProcessor.onMessage(receive);
                            } else {
                                log.error("DefaultMessagePump 的 {} 队列处理方式错误。", topic);
                            }
                        }
                        EXECUTOR.schedule(this, messageProcessor.period(), TimeUnit.MILLISECONDS);
                    }
                }, messageProcessor.period(), TimeUnit.MILLISECONDS);
            }

        }
    }
}
