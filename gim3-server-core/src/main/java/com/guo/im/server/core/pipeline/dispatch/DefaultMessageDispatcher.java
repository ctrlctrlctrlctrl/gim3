package com.guo.im.server.core.pipeline.dispatch;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import com.google.protobuf.ByteString;
import com.guo.im.common.exception.serializer.SerializerNotFoundException;
import com.guo.im.server.core.config.SerializerConfig;
import com.guo.im.server.core.constants.metadatakey.IMChannelKey;
import com.guo.im.server.core.enums.ClusterCMDEnum;
import com.guo.im.server.core.internal.model.ClusterMessageOuterClass;
import com.guo.im.server.core.pipeline.outlet.Outlet;
import com.guo.im.server.core.registry.bindkey.BindkeyParam;
import com.guo.im.server.core.registry.bindkey.BindkeyRegistration;
import com.guo.im.server.core.registry.bindkey.BindkeyRegistry;
import com.guo.im.server.core.registry.channel.IMChannelRegistration;
import com.guo.im.server.core.route.ConnectRouter;
import com.guo.im.server.core.serializer.Serializer;
import com.guo.im.server.core.serializer.SerializerHolder;
import lombok.SneakyThrows;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ： gyj
 * @date ：2026/1/2 19:15
 * @modifiedBy ：
 */
public class DefaultMessageDispatcher extends AbstractMessageDispatcher<DefaultDispatchMessage> {

    public static final ConcurrentHashMap<String, List<Object>> datastore = new ConcurrentHashMap<>();

    private final BindkeyRegistry bindkeyRegistry;

    private final ConnectRouter connectRouter;

    private final Outlet outlet;

    private final SerializerConfig serializerConfig;

    public DefaultMessageDispatcher(BindkeyRegistry bindkeyRegistry, ConnectRouter connectRouter, Outlet outlet, SerializerConfig serializerConfig) {
        this.bindkeyRegistry = bindkeyRegistry;
        this.connectRouter = connectRouter;
        this.outlet = outlet;
        this.serializerConfig = Objects.requireNonNullElseGet(serializerConfig, SerializerConfig::new);
    }

    @SneakyThrows
    @Override
    protected void doDispatch(DefaultDispatchMessage dispatchMessage) {

        //获取序列化器
        Serializer serializer = SerializerHolder.getSerializer(serializerConfig.getSerializerType(), serializerConfig.getSerializerVersion());
        if (serializer == null) {
            throw new SerializerNotFoundException("找不到对应的序列化器");
        }

        ArrayList<BindkeyParam> bindkeyParams = CollUtil.newArrayList(new BindkeyParam(dispatchMessage.getBindKey(), dispatchMessage.getDeviceId(), null));
        List<BindkeyRegistration> bindkeys = bindkeyRegistry.getBindkeys(bindkeyParams);
        if (CollUtil.isEmpty(bindkeys)) {
            return;
        }

        // 消息构造
        HashSet<String> instanceIds = new HashSet<>();// 需要推送的实例id集合
        HashMap<String, List<Object>> messages = new HashMap<>();
        for (BindkeyRegistration bindkey : bindkeys) {
            IMChannelRegistration route = connectRouter.route(bindkey.connectId());
            if (route != null) {
                List<Object> orDefault = messages.getOrDefault(bindkey.connectId(), new ArrayList<>());
                orDefault.add(dispatchMessage.getPayload());
                messages.put(bindkey.connectId(), orDefault);
                instanceIds.add(IMChannelKey.INSTANCE_ID.get(route.metadata()));
            }
        }

        //消息保存至储存
        messages.forEach((k,v)->{
            List<Object> orDefault = datastore.getOrDefault(k, new ArrayList<>());
            orDefault.addAll(v);
            messages.put(k, orDefault);
        });

        // 构造集群消息
        ClusterMessageOuterClass.ClusterMessage.Builder messageBuilder = ClusterMessageOuterClass.ClusterMessage.newBuilder();
        messageBuilder.setClusterCMD(ClusterCMDEnum.MESSAGE_DELIVER.getCmd());
        messageBuilder.setPayloadType(serializer.type());
        messageBuilder.setPayloadVersion(serializer.version());

        // 通知目标实例拉取自己的消息进行推送
        for (String instanceId : instanceIds) {
            messageBuilder.setMessageId(IdUtil.getSnowflakeNextIdStr());
            messageBuilder.setTargetInstanceId(instanceId);
            outlet.send(instanceId, messageBuilder.build());
        }
    }

}
