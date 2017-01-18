package finance.com.fp.mode.http;


import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface HttpService {


        @GET("index.php")
        Observable<String> getReslut(@QueryMap Map<String,String> map);

        @GET("index.php")
        Observable<String> getReslut(@Query("m") String city, @Query("c") String key, @Query("a") String a);

}
