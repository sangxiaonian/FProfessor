package finance.com.fp.mode.datafractory;

import com.google.gson.Gson;

import finance.com.fp.mode.bean.FinanceBean;
import finance.com.fp.mode.bean.HttpBean;
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
    private static String basicUrl = "http://192.168.0.110/phpcms/";
    private static String a = "192.168.0.110";

    /**
     * 消息中心
     *
     * @return
     */
    public static Observable<Set_Item> getMsg() {

        HttpService service = HttpClient.getClient(basicUrl);
        return service.getReslut("1","10")
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<HttpBean<FinanceBean>, Observable<Set_Item>>() {
                    @Override
                    public Observable<Set_Item> call(HttpBean<FinanceBean> financeBeanHttpBean) {
                        return Observable.from(financeBeanHttpBean.getTitle())
                                .map(new Func1<FinanceBean, Set_Item>() {
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
                                })
                                ;
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

        return  getMsg();


    }


    /**
     * 捞偏门
     *
     * @return
     */
    public static Observable<Set_Item> getPartialDoor() {

        return getMsg();
    }

    /**
     * 登录
     *
     * @return
     */
    public static Observable<String> login() {
        HttpService service = HttpClient.getClient(basicUrl);
        return service.getReslut("1","10")
                .map(new Func1<HttpBean<FinanceBean>, String>() {
                    @Override
                    public String call(HttpBean<FinanceBean> financeBeanHttpBean) {
                        return new Gson().toJson(financeBeanHttpBean);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 注册
     *
     * @return
     */
    public static Observable<String> register() {

        return login();
    }

    /**
     * 获取验证码
     *
     * @return
     */
    public static Observable<String> getDynamic() {

        return login();
    }

    /**
     * 获取验证码
     *
     * @return
     */
    public static Observable<String> setPassword() {

        return login();
    }


}
