package com.guo.im.server.core.bootstrap.assembler;

import cn.hutool.core.collection.CollUtil;
import com.guo.im.server.core.bootstrap.BootstrapContext;
import com.guo.im.server.core.listener.EndpointLifecycleListener;
import com.guo.im.server.core.listener.IMEventListener;
import com.guo.im.server.core.listener.InstanceLifecycleListener;
import com.guo.im.server.core.publish.CompositeIMEventPublisher;
import com.guo.im.server.core.publish.CoreIMEventPublisher;
import com.guo.im.server.core.publish.IMEventPublisher;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ： gyj
 * @date ：2026/1/5 19:02
 * @modifiedBy ：
 */
public class IMEventPublisherAssembler {

    /**
     * 组装并创建一个复合IM事件发布器
     * 该方法从BootstrapContext中获取事件监听器和发布器，构建核心监听器列表，
     * 创建核心事件发布器，然后将所有发布器组合成一个复合发布器返回
     *
     * @param context 启动上下文，包含IM事件监听器、发布器以及相关的注册表和生命周期管理器
     * @return 组合的IM事件发布器，包含核心发布器和上下文中的其他发布器
     */
    public static CompositeIMEventPublisher assemble(BootstrapContext context) {

        List<IMEventListener> imEventListeners = context.getImEventListeners();

        if (CollUtil.isEmpty(imEventListeners)) {
            imEventListeners = new ArrayList<>();
        }

        // 构建核心IM监听器列表，包括端点生命周期监听器和实例生命周期监听器
        ArrayList<IMEventListener> coreIMListeners = new ArrayList<>();
        coreIMListeners.add(new EndpointLifecycleListener(context.getInstanceRegistry(), context.getImChannelRegistry(), context.getImChannelLifecycle()));
        coreIMListeners.add(new InstanceLifecycleListener(context.getInstanceRegistry()));
        coreIMListeners.addAll(imEventListeners);
        CoreIMEventPublisher coreIMEventPublisher = new CoreIMEventPublisher(coreIMListeners);

        // 构建IM事件发布器列表，首先添加核心发布器
        ArrayList<IMEventPublisher> imEventPublishers = new ArrayList<>();
        imEventPublishers.add(coreIMEventPublisher);

        // 如果上下文中有其他IM事件发布器，则添加到列表中
        if (CollUtil.isNotEmpty(context.getImEventPublishers())){
            imEventPublishers.addAll(context.getImEventPublishers());
        }

        return new CompositeIMEventPublisher(imEventPublishers);
    }


}
