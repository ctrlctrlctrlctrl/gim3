package com.guo.im.server.core.bootstrap;

import com.guo.im.server.core.channel.IMChannelLifecycle;
import com.guo.im.server.core.config.SerializerConfig;
import com.guo.im.server.core.endpoint.IMEndpoint;
import com.guo.im.server.core.endpoint.IMEndpointLifecycle;
import com.guo.im.server.core.filter.BizFilter;
import com.guo.im.server.core.listener.IMEventListener;
import com.guo.im.server.core.pipeline.Inlet.Inlet;
import com.guo.im.server.core.pipeline.dispatch.MessageDispatcher;
import com.guo.im.server.core.pipeline.outlet.Outlet;
import com.guo.im.server.core.pipeline.processor.ClusterMessageProcessor;
import com.guo.im.server.core.pipeline.pump.MessagePump;
import com.guo.im.server.core.processor.AbstractIMProcessor;
import com.guo.im.server.core.processor.IMProcessor;
import com.guo.im.server.core.processor.IMProcessorRouter;
import com.guo.im.server.core.publish.IMEventPublisher;
import com.guo.im.server.core.registry.bindkey.BindkeyRegistry;
import com.guo.im.server.core.registry.channel.IMChannelRegistry;
import com.guo.im.server.core.registry.instance.InstanceRegistry;
import com.guo.im.server.core.route.ConnectRouter;
import com.guo.im.server.core.serializer.Serializer;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author ： gyj
 * @date ：2026/1/5 18:41
 * @modifiedBy ：
 */
@Setter
@Getter
public class BootstrapContext {

    private String instanceId;
    private IMChannelLifecycle imChannelLifecycle;
    private SerializerConfig serializerConfig;
    private List<IMEndpoint> imEndpoints;
    private IMEndpointLifecycle imEndpointLifecycle;
    private List<BizFilter> bizFilters;
    private List<IMEventListener> imEventListeners;
    private List<IMEventPublisher> imEventPublishers;
    private List<AbstractIMProcessor> imProcessors;
    private BindkeyRegistry bindkeyRegistry;
    private IMChannelRegistry imChannelRegistry;
    private InstanceRegistry instanceRegistry;
    private ConnectRouter connectRouter;
    private Serializer serializer;
    private IMProcessorRouter imProcessorRouter;

    private List<MessageDispatcher> messageDispatcher;
    private Inlet inlet;
    private Outlet outlet;
    private List<ClusterMessageProcessor> clusterMessageProcessors;
    private MessagePump messagePump;

}
