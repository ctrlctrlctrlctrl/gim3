import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.guo.im.server.common.message.OutboundMessage;
import com.guo.im.server.core.ServerManager;
import com.guo.im.server.core.bootstrap.InstanceBootstrap;
import com.guo.im.server.core.channel.IMChannel;
import com.guo.im.server.core.channel.IMChannelLifecycle;
import com.guo.im.server.core.endpoint.IMEndpoint;
import io.netty.util.AttributeKey;

import java.util.ArrayList;

/**
 * @author ： gyj
 * @date ：2025/12/30 16:07
 * @modifiedBy ：
 */
public class Test {

    @org.junit.Test
    public void test1() throws InterruptedException {
        a a = new a();

        ArrayList<IMEndpoint> imEndpoints = new ArrayList<>();
        imEndpoints.add(a);

        InstanceBootstrap.Builder builder = new InstanceBootstrap().builder();
        builder.imEndpoints(imEndpoints);
        ServerManager serverManager = builder.build();
        serverManager.start();

        Thread.sleep(500L);

        a.addChannel();
    }

    class a implements IMEndpoint {

        private static IMChannelLifecycle imChannelLifecycle;

        @Override
        public void start() {

        }

        @Override
        public void stop() {

        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setIMChannelLifecycle(IMChannelLifecycle imChannelLifecycle) {
            this.imChannelLifecycle = imChannelLifecycle;
        }

        public void addChannel(){
            imChannelLifecycle.channelCreated(new b(), this);
        }


    }

    class b extends IMChannel {

        @Override
        public String getConnectId() {
            return IdUtil.getSnowflakeNextIdStr();
        }

        @Override
        public boolean isActive() {
            return true;
        }

        @Override
        public boolean write(OutboundMessage outboundMessage) {
            System.out.println(JSONUtil.toJsonStr(outboundMessage));
            return true;
        }

        @Override
        public boolean close() {
            return true;
        }
    }

}
