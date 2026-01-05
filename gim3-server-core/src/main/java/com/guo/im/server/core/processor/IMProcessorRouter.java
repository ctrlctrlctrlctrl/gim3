package com.guo.im.server.core.processor;

import cn.hutool.core.collection.CollUtil;
import com.guo.im.server.core.annotation.GIMHandler;
import com.guo.im.server.core.biz.model.BizCommand;
import com.guo.im.server.core.context.BindKeyContext;
import com.guo.im.server.core.filter.BizFilter;
import com.guo.im.server.core.model.BindKey;
import org.apache.commons.lang3.Validate;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @author ： gyj
 * @date ：2025/12/27 20:06
 * @modifiedBy ：
 */
public class IMProcessorRouter {

    private final Map<Integer, AbstractIMProcessor> processorMap;

    private final List<BizFilter> filters;

    public IMProcessorRouter(Collection<AbstractIMProcessor> processors, List<BizFilter> filters) {
        this.filters = filters;

        HashMap<Integer, AbstractIMProcessor> processorHashMap = new HashMap<>();
        if (CollUtil.isNotEmpty(processors)) {
            for (AbstractIMProcessor processor : processors) {

                GIMHandler handler = processor.getClass().getAnnotation(GIMHandler.class);
                if (handler != null) {
                    if (handler.cmd() == 0) {
                        throw new IllegalArgumentException(processor.getClass().getSimpleName() + "的IMHandler注解cmd不能为0");
                    }
                    if (processorHashMap.containsKey((handler).cmd())) {
                        throw new DuplicateFormatFlagsException("cmd:" + handler.cmd() + "已存在");
                    }
                    processorHashMap.put(handler.cmd(), processor);
                } else {
                    throw new IllegalArgumentException("请为" + processor.getClass().getSimpleName() + "处理器添加IMHandler注解");
                }
            }
        }
        this.processorMap = processorHashMap;
    }

    public void route(int cmd, Object data) {
        AbstractIMProcessor processor = processorMap.get(cmd);

        Validate.notNull(processor, "未找到cmd:" + cmd + "对应的处理器");

        BindKey bindKey = BindKeyContext.getBindKey();
        processor.process(new BizCommand(bindKey.bindKey(), bindKey.deviceId(), data), filters);
    }

}
