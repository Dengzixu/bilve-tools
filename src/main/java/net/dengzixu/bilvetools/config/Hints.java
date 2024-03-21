package net.dengzixu.bilvetools.config;

import net.dengzixu.bilvetools.websocket.RawWebsocketServer;
import net.dengzixu.bilvetools.websocket.WebsocketServer;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeReference;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class Hints implements RuntimeHintsRegistrar {
    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        hints.reflection().registerType(WebsocketServer.class,
                MemberCategory.INVOKE_PUBLIC_METHODS,
                MemberCategory.INTROSPECT_PUBLIC_METHODS,
                MemberCategory.INVOKE_DECLARED_METHODS,
                MemberCategory.INTROSPECT_DECLARED_METHODS,
                MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS,
                MemberCategory.INTROSPECT_PUBLIC_CONSTRUCTORS);

        hints.reflection().registerType(RawWebsocketServer.class,
                MemberCategory.INVOKE_PUBLIC_METHODS,
                MemberCategory.INTROSPECT_PUBLIC_METHODS,
                MemberCategory.INVOKE_DECLARED_METHODS,
                MemberCategory.INTROSPECT_DECLARED_METHODS,
                MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS,
                MemberCategory.INTROSPECT_PUBLIC_CONSTRUCTORS);


        hints.proxies().registerJdkProxy(TypeReference.of("net.dengzixu.blivedanmaku.api.bilibili.live.BLiveAPI$API"));
        hints.proxies().registerJdkProxy(TypeReference.of("net.dengzixu.blivedanmaku.api.bilibili.live.BiliAPI$API"));
    }
}
