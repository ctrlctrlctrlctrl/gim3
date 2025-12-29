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

        String matchPath = PatternRouter.joinPathWithDefaults(new String[]{bindKey, deviceId,null}, "[a-zA-Z0-9]+");

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

        String matchPath = PatternRouter.joinPathWithDefaults(new String[]{bindKey, deviceId,null}, "[a-zA-Z0-9]+");

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
                paths.add(PatternRouter.joinPathWithDefaults(new String[]{bindkeyParam.bindKey(), bindkeyParam.deviceId()}, "[a-zA-Z0-9]+"));
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

        public List<V> match(String path) {
            return router.entrySet().stream()
                    .filter(e -> matcher.match(e.getKey(), path))
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());
        }

        public Set<V> match(Collection<String> paths) {
            if (CollUtil.isEmpty(paths)) {
                return new HashSet<>(router.values());
            }

            return router.entrySet().stream()
                    .filter(e -> {
                        String path = e.getKey();
                        for (String paramt : paths) {
                            if (matcher.match(path, paramt)) {
                                return true;
                            }
                        }
                        return false;
                    })
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toSet());
        }

        public static String joinPath(Object... parts) {
            // 检查是否有空值，若有则抛出异常
            for (Object part : parts) {
                if (part == null || part.toString().isEmpty()) {
                    throw new IllegalArgumentException("路径中不能包含空值或空字符串");
                }
            }

            // 拼接路径
            return Arrays.stream(parts)
                    .map(Object::toString)  // 使用toString拼接，为了可以将其base64编码解决拼接符的问题
                    .collect(Collectors.joining("/"));
        }

        public static String joinPathWithDefaults(Object[] parts, String fallback) {
            return Arrays.stream(parts)
                    .map(part -> part == null || part.toString().isEmpty() ? fallback : part.toString())
                    .collect(Collectors.joining("/"));
        }
    }


}
