package com.guo.im.server.core.processor;

import cn.hutool.core.collection.CollUtil;
import com.guo.im.server.core.annotation.GIMHandler;
import com.guo.im.server.core.biz.model.BizCommand;
import org.apache.commons.lang3.Validate;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.DuplicateFormatFlagsException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ： gyj
 * @date ：2025/12/27 20:06
 * @modifiedBy ：
 */
public class IMProcessorRouter {

    private final Map<Integer, IMProcessor> processorMap;

    public IMProcessorRouter(Collection<IMProcessor> processors) {

        HashMap<Integer, IMProcessor> processorHashMap = new HashMap<>();
        if (CollUtil.isNotEmpty(processors)) {
            for (IMProcessor processor : processors) {

                boolean hasIMHandler = false;

                Annotation[] annotations = processor.getClass().getAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation instanceof GIMHandler) {
                        if (((GIMHandler) annotation).cmd() == 0) {
                            throw new IllegalArgumentException(processor.getClass().getSimpleName() + "的IMHandler注解cmd不能为0");
                        }
                        if (processorHashMap.containsKey(((GIMHandler) annotation).cmd())) {
                            throw new DuplicateFormatFlagsException("cmd:" + ((GIMHandler) annotation).cmd() + "已存在");
                        }
                        processorHashMap.put(((GIMHandler) annotation).cmd(), processor);
                        hasIMHandler = true;
                        break;
                    }
                }
                if (!hasIMHandler) {
                    throw new IllegalArgumentException("请为" + processor.getClass().getSimpleName() + "处理器添加IMHandler注解");
                }
            }
        }
        this.processorMap = processorHashMap;
    }

    public void route(int cmd, Object data) {
        IMProcessor processor = processorMap.get(cmd);

        Validate.notNull(processor, "未找到cmd:" + cmd + "对应的处理器");

        //todo 待补充获取bindkey的逻辑
        processor.process(new BizCommand(null, null, data));
    }

}
