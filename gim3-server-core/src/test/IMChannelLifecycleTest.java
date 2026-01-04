import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.guo.im.server.common.message.OutboundMessage;
import com.guo.im.server.core.ServerManager;
import com.guo.im.server.core.channel.IMChannel;
import com.guo.im.server.core.channel.IMChannelLifecycle;
import com.guo.im.server.core.endpoint.IMEndpoint;
import com.guo.im.server.core.endpoint.IMEndpointHolder;
import com.guo.im.server.core.endpoint.IMEndpointLifecycle;
import com.guo.im.server.core.instance.InstanceHolder;
import com.guo.im.server.core.publish.CompositeIMEventPublisher;
import com.guo.im.server.core.registry.bindkey.BindkeyParam;
import com.guo.im.server.core.registry.bindkey.BindkeyRegistration;
import com.guo.im.server.core.registry.bindkey.BindkeyRegistry;
import com.guo.im.server.core.registry.bindkey.DefaultSingleBindkeyRegistry;
import com.guo.im.server.core.registry.channel.DefaultSingleIMChannelRegistry;
import com.guo.im.server.core.registry.channel.IMChannelRegistration;
import com.guo.im.server.core.registry.channel.IMChannelRegistry;
import org.junit.Test;

import java.util.List;

/**
 * @author ： gyj
 * @date ：2025/12/30 20:10
 * @modifiedBy ：
 */
public class IMChannelLifecycleTest {

    private static final IMChannelRegistry imChannelRegistry = new DefaultSingleIMChannelRegistry();

    private static final BindkeyRegistry bindkeyRegistry = new DefaultSingleBindkeyRegistry();

    private static final IMChannelLifecycle imChannelLifecycle =new IMChannelLifecycle("instanceId", imChannelRegistry, bindkeyRegistry){
        @Override
        public void onChannelCreated(IMChannel imChannel) {}
        @Override
        public void onChannelClosed(String connectId) {}
    };
    private static final IMEndpointLifecycle imEndpointLifecycle = new IMEndpointLifecycle("instanceId") {
        @Override
        public void onEndpointCreated(IMEndpoint imEndpoint) {}
        @Override
        public void onEndpointClosed(IMEndpoint imEndpoint) {}
    };

    @Test
    public void testCreate(){

        // 预处理
        TestIMEndpoint testIMEndpoint = new TestIMEndpoint();
        TestIMChannel testIMChannel = new TestIMChannel();
        IMEndpointHolder.putEndpoint(testIMEndpoint, "endpointId");
        InstanceHolder.register("instanceId", new ServerManager(null, new CompositeIMEventPublisher(null),imEndpointLifecycle));

        // 测试业务
        imChannelLifecycle.channelCreated(testIMChannel,testIMEndpoint);
        List<IMChannelRegistration> imChannels = imChannelRegistry.getIMChannels(CollUtil.newArrayList(testIMChannel.getConnectId()));
        for (IMChannelRegistration imChannel : imChannels) {
            System.out.println(imChannel);
        }
        List<BindkeyRegistration> bindkeys = bindkeyRegistry.getBindkeys(CollUtil.newArrayList(new BindkeyParam(null, null, testIMChannel.getConnectId())));
        for (BindkeyRegistration bindkey : bindkeys) {
            System.out.println(bindkey);
        }

        imChannelLifecycle.channelClosed(testIMChannel.getConnectId());
        imChannels = imChannelRegistry.getIMChannels(CollUtil.newArrayList(testIMChannel.getConnectId()));
        for (IMChannelRegistration imChannel : imChannels) {
            System.out.println(imChannel);
        }
        bindkeys = bindkeyRegistry.getBindkeys(CollUtil.newArrayList(new BindkeyParam(null, null, testIMChannel.getConnectId())));
        for (BindkeyRegistration bindkey : bindkeys) {
            System.out.println(bindkey);
        }

    }


    class TestIMChannel extends IMChannel{

        private String connectId;

        @Override
        public String getConnectId() {
            if (connectId == null){
                connectId = IdUtil.getSnowflakeNextIdStr();
            }
            return connectId;
        }

        @Override
        public boolean isActive() {
            return false;
        }

        @Override
        public boolean write(OutboundMessage outboundMessage) {
            return false;
        }

        @Override
        public boolean close() {
            return false;
        }
    }

    class TestIMEndpoint implements IMEndpoint {
        @Override
        public void start() {}
        @Override
        public void stop() {}
        @Override
        public boolean isReady() {return false;}
    }
}
