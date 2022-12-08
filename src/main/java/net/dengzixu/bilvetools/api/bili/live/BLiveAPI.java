package net.dengzixu.bilvetools.api.bili.live;

import net.dengzixu.bilvetools.api.RetrofitUtils;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

import java.io.IOException;
import java.util.Objects;

public class BLiveAPI {
    // Logger
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(BLiveAPI.class);

    private final API api;

    public BLiveAPI() {
        this.api = RetrofitUtils.create(API.class, "https://api.live.bilibili.com/");
    }

    /**
     * # 请求 https://api.live.bilibili.com/xlive/web-room/v1/giftPanel/giftConfig?platform=pc
     *
     * @return 上游服务器响应
     * @throws IOException IOException
     */
    public String giftConfig() throws IOException {
        Response<String> response = api.giftConfig().execute();

        return Objects.requireNonNull(response.body());
    }

    /**
     * # 请求 https://api.live.bilibili.com/xlive/web-room/v1/giftPanel/giftData?platform=pc
     *
     * @return 上游服务器响应
     * @throws IOException IOException
     */
    public String giftData(long roomID) throws IOException {
        Response<String> response = api.giftData(roomID).execute();

        return Objects.requireNonNull(response.body());
    }


    private interface API {
        @GET("/xlive/web-room/v1/giftPanel/giftConfig?platform=pc")
        @Headers({"Content-Type: application/json; charset=UTF-8"})
        Call<String> giftConfig();

        @GET("/xlive/web-room/v1/giftPanel/giftData?platform=pc")
        @Headers({"Content-Type: application/json; charset=UTF-8"})
        Call<String> giftData(@Query("room_id") long roomID);
    }
}
