package finance.com.fp.mode.datafractory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import finance.com.fp.mode.bean.FinanceBean;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.http.HttpClient;
import finance.com.fp.mode.http.HttpService;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/2/10 14:08
 */
public class HttpFactory {

    public static Observable<Set_Item> getMsg() {
        HttpService service = HttpClient.getClient("http://192.168.0.117/phpcms/").create(HttpService.class);
        return service.getReslut("content", "doserver", "get_app_content")
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, List<FinanceBean>>() {
                    @Override
                    public List<FinanceBean> call(String s) {
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<FinanceBean>>(){}.getType();
                        List<FinanceBean> appinfos = gson.fromJson(s, listType);
                        return appinfos;
                    }
                })
                .flatMap(new Func1<List<FinanceBean>, Observable<Set_Item>>() {
                    @Override
                    public Observable<Set_Item> call(List<FinanceBean> financeBeen) {
                        return Observable.from(financeBeen).map(new Func1<FinanceBean, Set_Item>() {
                            @Override
                            public Set_Item call(FinanceBean financeBean) {
                                Set_Item item = new Set_Item();
                                item.title=financeBean.getTitle();
                                item.describe=financeBean.getDescription();
                                item.img_url=financeBean.getThumb();
                                item.updatetime=financeBean.getUpdatetime();
                                item.content=financeBean.getContent();
                                return item;
                            }
                        });
                    }
                })
                .observeOn(Schedulers.io())

                .observeOn(AndroidSchedulers.mainThread());




    }


}
