package net.dengzixu.bilvetools.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties("blive-tools")
public record BLiveToolsProperties(Long roomId,
                                   @NestedConfigurationProperty
                                   Auth auth) {
    public record Auth(Long uid, String sessdata) {
    }
}
