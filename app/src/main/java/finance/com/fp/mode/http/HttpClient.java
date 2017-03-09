package finance.com.fp.mode.http;

import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import finance.com.fp.CusApplication;
import finance.com.fp.utlis.NetworkUtils;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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


    private static HttpService service, cacheServices;

    public static HttpService getClient() {

        if (service == null) {
            synchronized (HttpClient.class) {
                if (service == null) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.connectTimeout(60, TimeUnit.SECONDS)
                            .addInterceptor(getLogInterceptor())
//                            .addNetworkInterceptor(getIntercepot())
//                            .cache(getCache())
                    ;
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

    public static HttpService getClient(String url) {
        if (cacheServices == null) {
            synchronized (HttpClient.class) {
                if (cacheServices == null) {

                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.connectTimeout(60, TimeUnit.SECONDS)
                            .addInterceptor(getLogInterceptor())
                    ;
                    Retrofit retrofit = new Retrofit.Builder()
                            .client(builder.build())
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .baseUrl(url)
                            .build();
                    cacheServices = retrofit.create(HttpService.class);
                }
            }
        }
        return cacheServices;
    }

    @NonNull
    private static Cache getCache() {
        File cacheFile = new File(CusApplication.getContext().getCacheDir(), "rongcache");
        return new Cache(cacheFile, 1024 * 1024 * 10);
    }

    private static HttpLoggingInterceptor getLogInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        return interceptor;
    }


    private static Interceptor getIntercepot() {

        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                // 如果没有网络，则启用 FORCE_CACHE
                if (!NetworkUtils.isConnected()) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }

                Response response = chain.proceed(request);

                /** 设置max-age为5分钟之后，这5分钟之内不管有没有网, 都读缓存。
                 * max-stale设置为5天，意思是，网络未连接的情况下设置缓存时间为1天 */
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge(5, TimeUnit.MINUTES)
                        .maxStale(5, TimeUnit.DAYS)
                        .build();
                Logger.i("此处开始进行缓存");
                return response.newBuilder()
                        //在这里生成新的响应并修改它的响应头
                        .header("Cache-Control", cacheControl.toString())
                        .removeHeader("Pragma").build();

            }
        };


    }


}
