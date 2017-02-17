package finance.com.fp.mode.http;


import finance.com.fp.mode.bean.FinanceBean;
import finance.com.fp.mode.bean.HttpBean;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface HttpService {



        @FormUrlEncoded
        @POST("index.php?m=content&c=doserver&a=get_app_content")
        Observable<HttpBean<FinanceBean>> getReslut(@Field("page") String page, @Field("strip") String strip);

}
