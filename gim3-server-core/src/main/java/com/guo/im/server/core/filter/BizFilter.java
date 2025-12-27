package com.guo.im.server.core.filter;

import com.guo.im.server.core.biz.model.BizCommand;

/**
 * @author ： gyj
 * @date ：2025/12/27 15:47
 * @modifiedBy ：
 */
public interface BizFilter {

    void doFilter(BizCommand bizCommand, BizFilterChain filterChain);

}
