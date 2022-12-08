package net.dengzixu.bilvetools;

import net.dengzixu.bilvetools.api.bili.live.BLiveAPI;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class BLiveAPITest {
    private final BLiveAPI bLiveAPI = new BLiveAPI();

    @Test
    public void testGiftConfig() {
        try {
            System.out.println(bLiveAPI.giftConfig());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGiftData() {
        try {
            System.out.println(bLiveAPI.giftData(77274));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
