package net.dengzixu.bilvetools.mvc.web;


import net.dengzixu.bilvedanmaku.api.bilibili.live.BiliBiliLiveAPI;
import net.dengzixu.bilvetools.properties.BLiveToolsProperties;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/proxy")
public class ProxyController {
    // Looger
    private static final org.slf4j.Logger Logger = org.slf4j.LoggerFactory.getLogger(ProxyController.class);

    private final BLiveToolsProperties bLiveToolsProperties;

    @Autowired
    public ProxyController(BLiveToolsProperties bLiveToolsProperties) {
        this.bLiveToolsProperties = bLiveToolsProperties;
    }

    @GetMapping("/**")
    public ResponseEntity<String> proxy(HttpServletRequest httpServletRequest) {

        // 获取 URI
        String requestURI = httpServletRequest.getRequestURI();

        // 判断 URL 中是否包含 api.live.bilibili.com
        if (!requestURI.contains("api.live.bilibili.com")) {
            Logger.error("[Proxy] URL: {} 不在白名单内", requestURI);
            return ResponseEntity.status(403).body("Proxy URL 不在白名单内");
        }

        // 获取完整的 QueryString
        String queryString = httpServletRequest.getQueryString();

        // 去除 URI 中的 /proxy/
        String biliAPI = requestURI.replaceFirst("/proxy/", "");

        // 合成 URL
        String fullURL = biliAPI + "?" + queryString;

        // 发送请求
        OkHttpClient okHttpClient = new OkHttpClient();

        // 构建 Request
        Request request = new Request.Builder().url(fullURL).get().build();

        try (Response response = okHttpClient.newCall(request).execute()) {

            Logger.info("[Proxy] 代理 {} 成功", fullURL);

            return ResponseEntity.status(response.code())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response.body() != null ? response.body().string() : null);

        } catch (IOException e) {
            Logger.error("[Proxy] 代理 {}  出现异常 ", fullURL, e);
            return ResponseEntity.status(503).build();
        }
    }

    @GetMapping("/getDanmuInfo")
    public ResponseEntity<String> proxyRoomInfo() {
        String apiResponse = new BiliBiliLiveAPI().getDanmuInfo(bLiveToolsProperties.roomId(), bLiveToolsProperties.auth().sessdata());

        return ResponseEntity.ok(apiResponse);
    }
}
