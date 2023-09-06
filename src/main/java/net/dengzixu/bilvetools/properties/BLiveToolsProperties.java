package net.dengzixu.bilvetools.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("blive-tools")
public record BLiveToolsProperties(Long roomId, Auth auth) {
    public record Auth(Long uid, String sessdata) {
    }
}
