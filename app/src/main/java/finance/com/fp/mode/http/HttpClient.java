package finance.com.fp.mode.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/17 16:35
 */
public class HttpClient {


    private static HttpService service;

    public static HttpService getClient(String url) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(60, TimeUnit.SECONDS);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);
        Retrofit retrofit = new Retrofit.Builder()

                .client(builder.build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(url)
                .build();
        return retrofit.create(HttpService.class);

    }

    public static HttpService getClient() {
        if (service == null) {
            synchronized (HttpClient.class) {
                if (service == null) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.connectTimeout(60, TimeUnit.SECONDS);
                    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    builder.addInterceptor(interceptor);
                    Retrofit retrofit = new Retrofit.Builder()

                            .client(builder.build())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .baseUrl(Config.base_url)
                            .build();
                    service = retrofit.create(HttpService.class);

                }
            }
        }


        return service;
    }



}
