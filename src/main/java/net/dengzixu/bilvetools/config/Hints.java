package net.dengzixu.bilvetools.config;

import org.springframework.aot.hint.ExecutableMode;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeReference;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Hints implements RuntimeHintsRegistrar {
    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        final Class<?>[] classes = {
                net.dengzixu.blivedanmaku.data.DanmuMessageResolver.class,
                net.dengzixu.blivedanmaku.data.GuardBugResolver.class,
                net.dengzixu.blivedanmaku.data.IgnoreResolver.class,
                net.dengzixu.blivedanmaku.data.InteractWordResolver.class,
                net.dengzixu.blivedanmaku.data.SendGiftResolver.class,
                net.dengzixu.blivedanmaku.data.WatchedChangeResolver.class,

        };

        try {
            for (Class<?> clazz : classes) {
                Constructor<?> constructor = ReflectionUtils.accessibleConstructor(clazz, String.class);
                hints.reflection().registerConstructor(constructor, ExecutableMode.INVOKE);

                Method method = ReflectionUtils.findMethod(clazz, "resolve");

                if (null != method) {
                    hints.reflection().registerMethod(method, ExecutableMode.INVOKE);
                }
            }

        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }


//        hints.reflection().registerMethod(
//                ReflectionUtils.findMethod(net.dengzixu.blivedanmaku.data.InteractWordResolver.class,
//                        "resolve"),
//                ExecutableMode.INVOKE);

        hints.resources().registerPattern("ssl/certificate.jks");

        hints.proxies().registerJdkProxy(TypeReference.of("net.dengzixu.blivedanmaku.api.bilibili.live.BLiveAPI$API"));
        hints.proxies().registerJdkProxy(TypeReference.of("net.dengzixu.blivedanmaku.api.bilibili.live.BiliAPI$API"));
    }
}
