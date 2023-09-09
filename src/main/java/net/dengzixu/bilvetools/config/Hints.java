package net.dengzixu.bilvetools.config;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeReference;

public class Hints implements RuntimeHintsRegistrar {
    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        hints.resources().registerPattern("ssl/certificate.jks");

        hints.proxies().registerJdkProxy(TypeReference.of("net.dengzixu.blivedanmaku.api.bilibili.live.BLiveAPI$API"));
        hints.proxies().registerJdkProxy(TypeReference.of("net.dengzixu.blivedanmaku.api.bilibili.live.BiliAPI$API"));
    }
}
