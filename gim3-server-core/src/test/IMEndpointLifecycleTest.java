import com.guo.im.server.core.ServerManager;
import com.guo.im.server.core.endpoint.IMEndpoint;
import com.guo.im.server.core.endpoint.IMEndpointHolder;
import com.guo.im.server.core.endpoint.IMEndpointLifecycle;
import com.guo.im.server.core.instance.InstanceHolder;
import com.guo.im.server.core.publish.CompositeIMEventPublisher;
import org.junit.Test;

/**
 * @author ： gyj
 * @date ：2025/12/31 17:38
 * @modifiedBy ：
 */
public class IMEndpointLifecycleTest {

    private static final IMEndpointLifecycle imEndpointLifecycle = new IMEndpointLifecycle("instanceId") {
        @Override
        public void onEndpointCreated(IMEndpoint imEndpoint) {}
        @Override
        public void onEndpointClosed(IMEndpoint imEndpoint) {}
    };

    @Test
    public void testCreate(){
        TestIMEndpoint testIMEndpoint = new TestIMEndpoint();
        InstanceHolder.register("instanceId", new ServerManager(null, new CompositeIMEventPublisher(null),imEndpointLifecycle));

        imEndpointLifecycle.endpointCreated(testIMEndpoint);

        String endpointId = IMEndpointHolder.getEndpointId(testIMEndpoint);
        System.out.println(endpointId);

        imEndpointLifecycle.endpointClosed(testIMEndpoint);
        System.out.println(IMEndpointHolder.getEndpointId(testIMEndpoint));

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
