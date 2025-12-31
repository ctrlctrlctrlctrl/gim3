import com.guo.im.common.collection.KeyRef;
import org.junit.Test;

import java.util.HashMap;

/**
 * @author ： gyj
 * @date ：2025/12/30 16:21
 * @modifiedBy ：
 */
public class KeyRefTest {

    @Test
    public void test(){

        HashMap<String, Object> map = new HashMap<>();

        KeyRef<Integer> key1 = new KeyRef<>("key1");

        Integer value = key1.get(map);
        System.out.println(value);
        key1.put(map, 1);
        System.out.println(map.get("key1"));

    }

}
