package net.dengzixu.bilvetools;

import net.dengzixu.bilvedanmaku.BLiveDanmakuClient;
import net.dengzixu.bilvedanmaku.profile.BLiveAuthProfile;
import net.dengzixu.bilvetools.constant.Constant;
import net.dengzixu.bilvetools.properties.BLiveToolsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(BLiveToolsProperties.class)
public class BilveToolsApplication implements CommandLineRunner {
    // LOGGER
    private static final org.slf4j.Logger Logger = org.slf4j.LoggerFactory.getLogger(BilveToolsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BilveToolsApplication.class, args);
    }


    // 配置文件
    private final BLiveToolsProperties bLiveToolsProperties;

    @Autowired
    public BilveToolsApplication(BLiveToolsProperties bLiveToolsProperties) {
        this.bLiveToolsProperties = bLiveToolsProperties;
    }

    @Override
    public void run(String... args) {
        // 设置房间号
        Constant.ROOM_ID = bLiveToolsProperties.roomId();
        Logger.info("直播间ID: {}", Constant.ROOM_ID);

        // 获取认证配置
        BLiveAuthProfile bLiveAuthProfile;
        if (null == bLiveToolsProperties.auth().uid() || null == bLiveToolsProperties.auth().sessdata()) {
            bLiveAuthProfile = BLiveAuthProfile.getAnonymous();
        } else {
            bLiveAuthProfile = new BLiveAuthProfile(bLiveToolsProperties.auth().uid(),
                    bLiveToolsProperties.auth().sessdata());
        }

        // 建立链接
        Constant.bLiveDanmakuClient = BLiveDanmakuClient.getInstance(Constant.ROOM_ID, bLiveAuthProfile).connect();
    }
}
