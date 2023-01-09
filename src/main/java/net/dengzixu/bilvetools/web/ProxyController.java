package net.dengzixu.bilvetools.web;

import net.dengzixu.bilvetools.api.bili.live.BLiveAPI;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/proxy")
public class ProxyController {

    private final BLiveAPI bLiveAPI = new BLiveAPI();

    @GetMapping("/giftConfig")
    public ResponseEntity<String> proxyGiftConfig() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");

            return ResponseEntity.ok().headers(headers).body(bLiveAPI.giftConfig());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("");
        }
    }

    @GetMapping("/giftData")
    public ResponseEntity<String> proxyGiftData(@RequestParam(name = "room_id") String roomID) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");

            return ResponseEntity.ok().headers(headers).body(bLiveAPI.giftData(Long.parseLong(roomID)));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("");
        }
    }
}
