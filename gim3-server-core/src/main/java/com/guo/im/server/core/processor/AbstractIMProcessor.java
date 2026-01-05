package com.guo.im.server.core.processor;

import com.guo.im.server.core.biz.model.BizCommand;
import com.guo.im.server.core.filter.BizFilter;
import com.guo.im.server.core.filter.BizFilterChain;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author ： gyj
 * @date ：2026/1/4 15:14
 * @modifiedBy ：
 */
public abstract class AbstractIMProcessor implements IMProcessor {

    @Override
    public final void process(BizCommand bizCommand, List<BizFilter> filters) {
        BizFilterChain chain = new BizFilterChain(filters, () -> doProcess(bizCommand));
        chain.doFilter(bizCommand);
    }

    protected abstract void doProcess(BizCommand bizCommand);
}
