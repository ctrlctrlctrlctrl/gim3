package com.guo.im.server.core.event;

import cn.hutool.core.util.IdUtil;

public class IMEvent {

    public String getEventId() {
        return IdUtil.getSnowflake().nextIdStr();
    }

    public Long getTimestamp() {
        return System.currentTimeMillis();
    }

}