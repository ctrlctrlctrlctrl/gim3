package com.guo.im.server.core.processor;

import com.guo.im.server.core.biz.model.BizCommand;
import com.guo.im.server.core.filter.BizFilter;

import java.util.List;

/**
 * @author ： gyj
 * @date ：2025/12/27 15:27
 * @modifiedBy ：
 */
public interface IMProcessor {

    void process(BizCommand bizCommand, List<BizFilter> filters);

}
