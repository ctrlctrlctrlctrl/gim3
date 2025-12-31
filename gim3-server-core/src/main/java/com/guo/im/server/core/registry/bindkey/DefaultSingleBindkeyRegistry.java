package com.guo.im.server.core.registry.bindkey;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.AntPathMatcher;
import com.guo.im.server.core.registry.enums.RegistryActionEnum;
import org.apache.commons.lang3.Validate;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author ： gyj
 * @date ：2025/12/29 19:22
 * @modifiedBy ：
 */
public class DefaultSingleBindkeyRegistry implements BindkeyRegistry {

    private static final PatternRouter<BindkeyRegistration> REGISTRY = new PatternRouter<>();

    private static final List<BindkeyChangeListener> LISTENERS = new ArrayList<>();


    @Override
    public void register(String bindKey, String deviceId, String connectId, Map<String, Object> metadata) {

        Validate.notBlank(bindKey, "bindkey不可为空");
        Validate.notBlank(deviceId, "deviceId不可为空");
        Validate.notBlank(connectId, "connectId不可为空");

        String matchPath = PatternRouter.joinPathWithDefaults(new String[]{bindKey, deviceId, null}, "*");

        RegistryActionEnum registryAction = null;

        String path = PatternRouter.joinPath(bindKey, deviceId, connectId);
        BindkeyRegistration bindkeyRegistration = new BindkeyRegistration(bindKey, deviceId, connectId, metadata);

        List<BindkeyRegistration> matched = REGISTRY.match(matchPath);
        if (CollUtil.isEmpty(matched)) {
            registryAction = RegistryActionEnum.REGISTRY;
        } else {

            this.deregister(bindKey, deviceId);

            registryAction = RegistryActionEnum.CHANGE;
        }

        REGISTRY.register(path, bindkeyRegistration);

        this.notifyListeners(registryAction, bindkeyRegistration);

    }

    @Override
    public boolean deregister(String bindKey, String deviceId) {

        String matchPath = PatternRouter.joinPathWithDefaults(new String[]{bindKey, deviceId, null}, "*");

        List<BindkeyRegistration> matched = REGISTRY.match(matchPath);
        for (BindkeyRegistration registration : matched) {
            String removePath = PatternRouter.joinPath(registration.bindKey(), registration.deviceId(), registration.connectId());
            REGISTRY.deregister(removePath);

            this.notifyListeners(RegistryActionEnum.DEREGISTRY, registration);
        }


        return false;
    }

    @Override
    public boolean deregister(String connectId) {

        String matchPath = PatternRouter.joinPathWithDefaults(new String[]{null, null, connectId}, "*");

        List<BindkeyRegistration> matched = REGISTRY.match(matchPath);
        for (BindkeyRegistration registration : matched) {
            String removePath = PatternRouter.joinPath(registration.bindKey(), registration.deviceId(), registration.connectId());
            REGISTRY.deregister(removePath);
        }

        return false;
    }

    @Override
    public List<BindkeyRegistration> getBindkeys(Collection<BindkeyParam> bindkeyParams) {
        if (CollUtil.isEmpty(bindkeyParams)) {
            return new ArrayList<>(REGISTRY.match(new ArrayList<>()));
        } else {
            HashSet<String> paths = new HashSet<>();
            for (BindkeyParam bindkeyParam : bindkeyParams) {
                paths.add(PatternRouter.joinPathWithDefaults(new String[]{bindkeyParam.bindKey(), bindkeyParam.deviceId(), bindkeyParam.connectId()}, "*"));
            }
            return new ArrayList<>(REGISTRY.match(paths));
        }
    }

    @Override
    public void watchBindkeys(BindkeyChangeListener listener) {
        if (listener != null) {
            LISTENERS.add(listener);
        }
    }


    private void notifyListeners(RegistryActionEnum registryAction, BindkeyRegistration bindkeyRegistration) {
        for (BindkeyChangeListener listener : LISTENERS) {
            listener.onBindkeysChanged(registryAction, bindkeyRegistration);
        }
    }


    public static class PatternRouter<V> {
        private final ConcurrentHashMap<String, V> router = new ConcurrentHashMap<>();
        private final AntPathMatcher matcher = new AntPathMatcher();

        public void register(String pattern, V handler) {
            router.put(pattern, handler);
        }

        public void deregister(String pattern) {
            router.remove(pattern);
        }

        // 注意：参数名为 pattern，表示这是一个通配符模式
        public List<V> match(String pattern) {
            return router.entrySet().stream()
                    .filter(e -> matcher.match(pattern, e.getKey()))
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());
        }

        public Set<V> match(Collection<String> patterns) {
            if (CollUtil.isEmpty(patterns)) {
                return new HashSet<>(router.values());
            }

            return router.entrySet().stream()
                    .filter(e -> {
                        String actualPath = e.getKey();
                        for (String pattern : patterns) {
                            if (matcher.match(pattern, actualPath)) {
                                return true;
                            }
                        }
                        return false;
                    })
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toSet());
        }

        public static String joinPath(Object... parts) {
            for (Object part : parts) {
                if (part == null || part.toString().isEmpty()) {
                    throw new IllegalArgumentException("路径中不能包含空值或空字符串");
                }
            }
            return Arrays.stream(parts)
                    .map(Object::toString)
                    .collect(Collectors.joining("/"));
        }

        public static String joinPathWithDefaults(Object[] parts, String fallback) {
            return Arrays.stream(parts)
                    .map(part -> part == null || part.toString().isEmpty() ? fallback : part.toString())
                    .collect(Collectors.joining("/"));
        }
    }
}
