package finance.com.fp.mode.datafractory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import finance.com.fp.mode.bean.FinanceBean;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.http.HttpClient;
import finance.com.fp.mode.http.HttpService;
import finance.com.fp.utlis.Utils;
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
    private static String basicUrl = "http://192.168.0.107/phpcms/";
    private static String a = "192.168.0.107";

    /**
     * 消息中心
     *
     * @return
     */
    public static Observable<Set_Item> getMsg() {
        HttpService service = HttpClient.getClient(basicUrl);
        return service.getReslut("content", "doserver", "get_app_content")
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, List<FinanceBean>>() {
                    @Override
                    public List<FinanceBean> call(String s) {
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<FinanceBean>>() {
                        }.getType();
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
                                item.title = financeBean.getTitle();
                                item.describe = financeBean.getDescription();
                                item.img_url = financeBean.getThumb().replace("localhost", a);
                                String s = financeBean.getUpdatetime() + "000";
                                item.content = financeBean.getContent();
                                item.updatetime = Utils.fromatTime(s);

                                return item;
                            }
                        });
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());

    }

    /**
     * 最新金融口子
     *
     * @return
     */
    public static Observable<Set_Item> getFinance() {
        HttpService service = HttpClient.getClient(basicUrl);
        return service.getReslut("content", "doserver", "get_app_content")
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, List<FinanceBean>>() {
                    @Override
                    public List<FinanceBean> call(String s) {
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<FinanceBean>>() {
                        }.getType();
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
                                item.title = financeBean.getTitle();
                                item.describe = financeBean.getDescription();
                                item.img_url = financeBean.getThumb().replace("localhost", a);
                                String s = financeBean.getUpdatetime() + "000";
                                item.updatetime = Utils.fromatTime(s);
                                item.content = financeBean.getContent();
                                return item;
                            }
                        });
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());

    }


    /**
     * 捞偏门
     *
     * @return
     */
    public static Observable<Set_Item> getPartialDoor() {
        HttpService service = HttpClient.getClient(basicUrl);
        return service.getReslut("content", "doserver", "get_app_content")
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, List<FinanceBean>>() {
                    @Override
                    public List<FinanceBean> call(String s) {
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<FinanceBean>>() {
                        }.getType();
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
                                item.title = financeBean.getTitle();
                                item.describe = financeBean.getDescription();
                                item.img_url = financeBean.getThumb().replace("localhost", a);
                                String s = financeBean.getUpdatetime() + "000";
                                item.updatetime = Utils.fromatTime(s);
                                item.content = financeBean.getContent();
                                return item;
                            }
                        });
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 登录
     *
     * @return
     */
    public static Observable<String> login() {
        HttpService service = HttpClient.getClient(basicUrl);
        return service.getReslut("content", "doserver", "get_app_content")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 注册
     *
     * @return
     */
    public static Observable<String> register() {
        HttpService service = HttpClient.getClient(basicUrl);
        return service.getReslut("content", "doserver", "get_app_content")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取验证码
     * @return
     */
    public static Observable<String> getDynamic() {
        HttpService service = HttpClient.getClient(basicUrl);
        return service.getReslut("content", "doserver", "get_app_content")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
