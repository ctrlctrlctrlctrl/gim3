package com.guo.im.server.core.bootstrap;

import cn.hutool.core.util.IdUtil;
import com.guo.im.server.core.ServerManager;
import com.guo.im.server.core.bootstrap.assembler.*;
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
import com.guo.im.server.core.publish.CompositeIMEventPublisher;
import com.guo.im.server.core.publish.IMEventPublisher;
import com.guo.im.server.core.registry.bindkey.BindkeyRegistry;
import com.guo.im.server.core.registry.channel.IMChannelRegistry;
import com.guo.im.server.core.registry.instance.InstanceRegistry;
import com.guo.im.server.core.route.ConnectRouter;
import com.guo.im.server.core.serializer.Serializer;

import java.util.List;

/**
 * @author ： gyj
 * @date ：2025/12/27 16:18
 * @modifiedBy ：
 */
public class InstanceBootstrap {

    public Builder builder() {
        return new Builder();
    }

    public static final class Builder {

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

        private List<MessageDispatcher> messageDispatcher;
        private Inlet inlet;
        private Outlet outlet;
        private List<ClusterMessageProcessor> clusterMessageProcessors;
        private MessagePump messagePump;

        public Builder imChannelLifecycle(IMChannelLifecycle imChannelLifecycle) {
            this.imChannelLifecycle = imChannelLifecycle;
            return this;
        }

        public Builder serializerConfig(SerializerConfig serializerConfig) {
            this.serializerConfig = serializerConfig;
            return this;
        }

        public Builder imEndpoints(List<IMEndpoint> imEndpoints) {
            this.imEndpoints = imEndpoints;
            return this;
        }

        public Builder imEndpointLifecycle(IMEndpointLifecycle imEndpointLifecycle) {
            this.imEndpointLifecycle = imEndpointLifecycle;
            return this;
        }

        public Builder bizFilters(List<BizFilter> bizFilters) {
            this.bizFilters = bizFilters;
            return this;
        }

        public Builder imEventListeners(List<IMEventListener> imEventListeners) {
            this.imEventListeners = imEventListeners;
            return this;
        }

        public Builder imEventPublishers(List<IMEventPublisher> imEventPublishers) {
            this.imEventPublishers = imEventPublishers;
            return this;
        }

        public Builder imProcessors(List<AbstractIMProcessor> imProcessors) {
            this.imProcessors = imProcessors;
            return this;
        }

        public Builder bindkeyRegistry(BindkeyRegistry bindkeyRegistry) {
            this.bindkeyRegistry = bindkeyRegistry;
            return this;
        }

        public Builder imChannelRegistry(IMChannelRegistry imChannelRegistry) {
            this.imChannelRegistry = imChannelRegistry;
            return this;
        }

        public Builder instanceRegistry(InstanceRegistry instanceRegistry) {
            this.instanceRegistry = instanceRegistry;
            return this;
        }

        public Builder connectRouter(ConnectRouter connectRouter) {
            this.connectRouter = connectRouter;
            return this;
        }

        public Builder serializer(Serializer serializer) {
            this.serializer = serializer;
            return this;
        }

        public Builder messageDispatcher(List<MessageDispatcher> messageDispatcher) {
            this.messageDispatcher = messageDispatcher;
            return this;
        }

        public Builder inlet(Inlet inlet) {
            this.inlet = inlet;
            return this;
        }

        public Builder outlet(Outlet outlet) {
            this.outlet = outlet;
            return this;
        }

        public Builder clusterMessageProcessor(List<ClusterMessageProcessor> clusterMessageProcessors) {
            this.clusterMessageProcessors = clusterMessageProcessors;
            return this;
        }

        public Builder messagePump(MessagePump messagePump) {
            this.messagePump = messagePump;
            return this;
        }

        public ServerManager build() {

            String instanceId = IdUtil.getSnowflakeNextIdStr();

            BootstrapContext bootstrapContext = new BootstrapContext();
            bootstrapContext.setBindkeyRegistry(bindkeyRegistry);
            bootstrapContext.setBindkeyRegistry(BindkeyRegistryAssembler.assemble(bootstrapContext));
            bootstrapContext.setImChannelRegistry(imChannelRegistry);
            bootstrapContext.setImChannelRegistry(IMChannelRegistryAssembler.assemble(bootstrapContext));
            bootstrapContext.setInstanceRegistry(instanceRegistry);
            bootstrapContext.setInstanceRegistry(InstanceRegistryAssembler.assemble(bootstrapContext));
            bootstrapContext.setBizFilters(bizFilters);
            bootstrapContext.setImProcessors(imProcessors);
            bootstrapContext.setImProcessorRouter(IMProcessorRouterAssembler.assemble(bootstrapContext));
            bootstrapContext.setInstanceId(instanceId);
            bootstrapContext.setImChannelLifecycle(imChannelLifecycle);
            bootstrapContext.setImChannelLifecycle(IMChannelLifecycleAssembler.assemble(bootstrapContext));
            bootstrapContext.setSerializerConfig(serializerConfig);
            bootstrapContext.setSerializerConfig(SerializerConfigAssembler.assemble(bootstrapContext));
            bootstrapContext.setImEndpoints(imEndpoints);
            bootstrapContext.setImEndpointLifecycle(imEndpointLifecycle);
            bootstrapContext.setImEndpointLifecycle(IMEndpointLifecycleAssembler.assemble(bootstrapContext));
            bootstrapContext.setImEventListeners(imEventListeners);
            bootstrapContext.setImEventPublishers(imEventPublishers);
            bootstrapContext.setConnectRouter(connectRouter);
            bootstrapContext.setConnectRouter(ConnectRouterAssembler.assemble(bootstrapContext));
            bootstrapContext.setSerializer(serializer);
            bootstrapContext.setSerializer(SerializerAssmbler.assemble(bootstrapContext));

            bootstrapContext.setInlet(inlet);
            bootstrapContext.setInlet(InletAssmbler.assemble(bootstrapContext));
            bootstrapContext.setOutlet(outlet);
            bootstrapContext.setOutlet(OutletAssmbler.assemble(bootstrapContext));
            bootstrapContext.setMessageDispatcher(messageDispatcher);
            bootstrapContext.setMessageDispatcher(MessageDispatcherAssmbler.assemble(bootstrapContext));
            bootstrapContext.setClusterMessageProcessors(clusterMessageProcessors);
            bootstrapContext.setClusterMessageProcessors(ClusterMessageProcessorAssmbler.assemble(bootstrapContext));
            bootstrapContext.setMessagePump(messagePump);
            bootstrapContext.setMessagePump(MessagePumpAssmbler.assemble(bootstrapContext));

            CompositeIMEventPublisher compositeIMEventPublisher = IMEventPublisherAssembler.assemble(bootstrapContext);

            return new ServerManager(instanceId, imEndpoints, compositeIMEventPublisher, bootstrapContext.getImEndpointLifecycle());
        }


    }
}
