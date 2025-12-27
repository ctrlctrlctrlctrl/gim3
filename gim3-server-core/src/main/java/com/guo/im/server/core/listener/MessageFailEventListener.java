package com.guo.im.server.core.listener;

import com.guo.im.server.core.event.ClusterMessageFailAckEvent;
import com.guo.im.server.core.event.IMEvent;

/**
 * @author ： gyj
 * @date ：2025/12/27 15:22
 * @modifiedBy ：
 */
public class MessageFailEventListener implements IMEventListener {
    @Override
    public boolean supports(IMEvent imEvent) {
        return imEvent instanceof ClusterMessageFailAckEvent;
    }

    @Override
    public void onEvent(IMEvent imEvent) {
        //todo 处理
    }
}
