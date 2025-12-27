package com.guo.im.server.core.filter;

import com.guo.im.server.core.biz.model.BizCommand;

import java.util.List;

/**
 * @author ： gyj
 * @date ：2025/12/27 15:50
 * @modifiedBy ：
 */
public class BizFilterChain {

    private final List<BizFilter> filters;
    private int index = 0;

    public BizFilterChain(List<BizFilter> filters) {
        this.filters = filters;
    }

    public void doFilter(BizCommand command) {
        if (index < filters.size()) {
            BizFilter current = filters.get(index++);
            doFilter(command);
        }
    }
}
