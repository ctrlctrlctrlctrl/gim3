package com.guo.im.server.core.filter;

import cn.hutool.core.collection.CollUtil;
import com.guo.im.server.core.biz.model.BizCommand;

import java.util.List;

/**
 * @author ： gyj
 * @date ：2025/12/27 15:50
 * @modifiedBy ：
 */
public class BizFilterChain {

    private final List<BizFilter> filters;
    private final Runnable target;
    private int index = 0;

    public BizFilterChain(List<BizFilter> filters, Runnable target) {
        this.filters = filters;
        this.target = target;
    }

    public void doFilter(BizCommand command) {
        if (CollUtil.isNotEmpty(filters) && index < filters.size()) {
            BizFilter current = filters.get(index++);
            current.doFilter(command, this);
        } else {
            target.run(); // 唯一 doProcess 入口
        }
    }
}

