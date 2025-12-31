import cn.hutool.core.collection.CollUtil;
import com.guo.im.server.core.registry.bindkey.BindkeyParam;
import com.guo.im.server.core.registry.bindkey.BindkeyRegistration;
import com.guo.im.server.core.registry.bindkey.DefaultSingleBindkeyRegistry;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ： gyj
 * @date ：2025/12/29 20:37
 * @modifiedBy ：
 */
public class DefaultSingleBindkeyRegistryTest {

    @Test
    public void testRegister() {
        DefaultSingleBindkeyRegistry registry = new DefaultSingleBindkeyRegistry();
        registry.register("test", "test", "test", null);
        registry.register("test", "test1", "test1", null);
        registry.register("test", "test2", "test2", null);
        registry.register("test", "test2", "test3", null);
        registry.register("test", "test3", "test4", null);
        registry.register("test1", "test2", "test", null);

        List<BindkeyRegistration> bindkeys = registry.getBindkeys(new ArrayList<>());

        for (BindkeyRegistration bindkey : bindkeys) {
            System.out.println(bindkey);
        }
    }

    @Test
    public void testQuery() {
        DefaultSingleBindkeyRegistry registry = new DefaultSingleBindkeyRegistry();
        registry.register("test", "test", "test", null);
        registry.register("test", "test1", "test1", null);
        registry.register("test", "test2", "test2", null);
        registry.register("test", "test2", "test3", null);
        registry.register("test", "test3", "test4", null);
        registry.register("test1", "test2", "test", null);

        BindkeyParam bindkeyParam1 = new BindkeyParam("test", "test2", null);
        BindkeyParam bindkeyParam2 = new BindkeyParam("test3", "test2", null);
        BindkeyParam bindkeyParam3 = new BindkeyParam("test1", "test2", null);
        BindkeyParam bindkeyParam4 = new BindkeyParam(null, "test2", null);
        List<BindkeyRegistration> bindkeys = registry.getBindkeys(CollUtil.newArrayList(bindkeyParam1, bindkeyParam2, bindkeyParam3, bindkeyParam4));

        for (BindkeyRegistration bindkey : bindkeys) {
            System.out.println(bindkey);
        }
    }

}
