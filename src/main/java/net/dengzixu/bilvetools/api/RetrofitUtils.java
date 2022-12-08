package net.dengzixu.bilvetools.api;

import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitUtils {
    private static final Logger logger = LoggerFactory.getLogger(RetrofitUtils.class);

    public static <T> T create(final Class<T> service, String url) {
        OkHttpClient okHttpClient;
        Retrofit retrofit;

        okHttpClient = new OkHttpClient();

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        return retrofit.create(service);
    }
}
