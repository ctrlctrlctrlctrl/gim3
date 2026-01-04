package com.guo.im.server.core.pipeline.dispatch;

import com.guo.im.server.core.pipeline.PushTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author ： gyj
 * @date ：2025/12/27 12:46
 * @modifiedBy ：
 */
@Getter
@Setter
public class DispatchMessage implements Serializable {
    private PushTypeEnum pushType;
    private Object payload;
}
