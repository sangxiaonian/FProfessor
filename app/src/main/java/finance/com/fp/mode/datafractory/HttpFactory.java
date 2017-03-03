package finance.com.fp.mode.datafractory;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.util.List;

import finance.com.fp.CusApplication;
import finance.com.fp.R;
import finance.com.fp.mode.bean.DynamicBean;
import finance.com.fp.mode.bean.FinanceBean;
import finance.com.fp.mode.bean.FriendBean;
import finance.com.fp.mode.bean.HttpBean;
import finance.com.fp.mode.bean.IDBean;
import finance.com.fp.mode.bean.LoanSearchBean;
import finance.com.fp.mode.bean.MsgContentBean;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.http.Config;
import finance.com.fp.mode.http.HttpClient;
import finance.com.fp.mode.http.HttpParams;
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

    private static String a = "192.168.0.111";


    /**
     * 获取消息中心
     *
     * @param page  页数
     * @param strip 条目数
     * @return
     */
    public static Observable<Set_Item> getMsg(String page, String strip) {

        HttpService service = HttpClient.getClient();
        return service.getMessageContent(page, strip)
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<HttpBean<MsgContentBean>, Observable<Set_Item>>() {
                    @Override
                    public Observable<Set_Item> call(HttpBean<MsgContentBean> financeBeanHttpBean) {
                        return Observable.from(financeBeanHttpBean.getTitle())
                                .map(new Func1<MsgContentBean, Set_Item>() {
                                    @Override
                                    public Set_Item call(MsgContentBean financeBean) {
                                        Set_Item item = new Set_Item();
                                        item.title = financeBean.title;
                                        item.describe = financeBean.description;

                                        String s = financeBean.updatetime + "000";
                                        item.content = financeBean.content;
                                        item.updatetime = em.sang.com.allrecycleview.utils.Utils.formatDateTime(s);
                                        return item;
                                    }
                                })
                                ;
                    }
                })
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    /**
     * 最新金融口子
     *
     * @param page  页数
     * @param strip 条目数
     * @return
     */
    public static Observable<Set_Item> getFinance(String page, String strip) {
        HttpService service = HttpClient.getClient();
        return service.getContent("10", page, strip).subscribeOn(Schedulers.io())

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
                                        String s = financeBean.getUpdatetime() + "000";
                                        item.content = financeBean.getContent();
                                        item.updatetime = em.sang.com.allrecycleview.utils.Utils.formatDateTime(s);
                                        item.img_url = financeBean.getThumb();
                                        return item;
                                    }
                                })
                                ;
                    }
                })
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());


    }


    /**
     * 捞偏门
     *
     * @param page  页数
     * @param strip 条目数
     * @return
     */
    public static Observable<Set_Item> getPartialDoor(String page, String strip) {

        HttpService service = HttpClient.getClient();
        return service.getContent("22", page, strip).subscribeOn(Schedulers.io())

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
                                        String s = financeBean.getUpdatetime() + "000";
                                        item.content = financeBean.getContent();
                                        item.updatetime = em.sang.com.allrecycleview.utils.Utils.formatDateTime(s);
                                        item.img_url = financeBean.getThumb();
                                        return item;
                                    }
                                })
                                ;
                    }
                })

                .observeOn(AndroidSchedulers.mainThread());

    }

    /**
     * 办卡攻略
     *
     * @return
     */
    public static Observable<Set_Item> getCardStrategy(int id, int page) {

        HttpService service = HttpClient.getClient();
        return service.getCardStrategy(id, page, 20).subscribeOn(Schedulers.io())

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
                                        String s = financeBean.getUpdatetime() + "000";
                                        item.content = financeBean.getContent();
                                        item.updatetime = em.sang.com.allrecycleview.utils.Utils.formatDateTime(s);
                                        item.img_url = financeBean.getThumb();
                                        return item;
                                    }
                                })
                                ;
                    }
                })

                .observeOn(AndroidSchedulers.mainThread());

    }

    /**
     * 提额攻略
     *
     * @param page  页数
     * @param strip 条目数
     * @return
     */
    public static Observable<Set_Item> getLoan(String page, String strip) {

        HttpService service = HttpClient.getClient();
        return service.getContent("33", page, strip).subscribeOn(Schedulers.io())

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
                                        String s = financeBean.getUpdatetime() + "000";
                                        item.content = financeBean.getContent();
                                        item.updatetime = em.sang.com.allrecycleview.utils.Utils.formatDateTime(s);
                                        item.img_url = financeBean.getThumb();
                                        return item;
                                    }
                                })
                                ;
                    }
                })

                .observeOn(AndroidSchedulers.mainThread());

    }

    /**
     * 网贷攻略
     *
     * @param page  页数
     * @param strip 条目数
     * @return
     */
    public static Observable<Set_Item> getLoanStrage(String page, String strip) {

        HttpService service = HttpClient.getClient();
        return service.getContent("21", page, strip).subscribeOn(Schedulers.io())

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
                                        String s = financeBean.getUpdatetime() + "000";
                                        item.content = financeBean.getContent();
                                        item.updatetime = em.sang.com.allrecycleview.utils.Utils.formatDateTime(s);
                                        item.img_url = financeBean.getThumb();
                                        return item;
                                    }
                                })
                                ;
                    }
                })

                .observeOn(AndroidSchedulers.mainThread());

    }


    /**
     * 登录
     *
     * @return
     */
    public static Observable<String> login(String username,String password,boolean dynamic) {
        HttpService service = HttpClient.getClient(Config.base_url);
        return service.login( username, password,dynamic)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 注册
     *
     * @return
     */
    public static Observable<String> register(String username, String member) {

        return HttpClient.getClient(Config.base_url).register(username, member).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 密码
     *
     * @return
     */
    public static Observable<String> setPassword(String username, String member) {

        return HttpClient.getClient(Config.base_url).setPassWord(username, member).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取验证码
     *
     * @return
     */
    public static Observable<String> getDynamic(String phone, DynamicBean bean) {

        HttpParams params = HttpParams.getInstance();


        String select = "f0d323ebed036b0f59b312334d65e80a";

        //公共参数
        params.put("method", "alibaba.aliqin.fc.sms.num.send");
        params.put("app_key", "23650862");
        params.put("sign_method", "md5");
        String time = Utils.getCurrentTimeo("yyyy-MM-dd HH:mm:ss");
        params.put("timestamp", time);
        params.put("format", "json");
        params.put("v", "2.0");

        //请求参数
        params.put("sms_type", "normal");
        params.put("extend", bean.getCode());
        params.put("sms_free_sign_name", "融教授");//短信签名
        params.put("rec_num", phone);//电话号
        params.put("sms_template_code", "SMS_48830127");//短信模板
        params.put("sms_param", new Gson().toJson(bean));//短信模板
        String value = params.getValue(select);
        String md5 = Utils.MD5(value);
        Logger.i(value);
        params.put("sign", md5);//签名
        return HttpClient.getClient(Config.msg_url).getDynamic(params.getParams()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 学习规划师
     *
     * @return
     * @param msg
     */
    public static Observable<Set_Item> getPlanner(String msg,String phone) {
        return HttpClient.getClient().getPlanner(msg,phone).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<HttpBean<FinanceBean>, Observable<Set_Item>>() {
                    @Override
                    public Observable<Set_Item> call(HttpBean<FinanceBean> financeBeanHttpBean) {
                        return Observable.from(financeBeanHttpBean.getTitle())
                                .map(new Func1<FinanceBean, Set_Item>() {
                                    @Override
                                    public Set_Item call(FinanceBean financeBean) {
                                        Set_Item item = new Set_Item();
                                        item.title = financeBean.getContent();

                                        item.turl = financeBean.getTurl();
                                        item.content = financeBean.getTurl();
                                        item.img_url = financeBean.getThumb();

                                        return item;
                                    }
                                })
                                ;
                    }
                });
    }


    /***
     * 本期力荐卡
     *
     * @return
     */
    public static Observable<Set_Item> getRecommends() {
        return HttpClient.getClient().getRecommend().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<HttpBean<FinanceBean>, Observable<Set_Item>>() {
                    @Override
                    public Observable<Set_Item> call(HttpBean<FinanceBean> financeBeanHttpBean) {
                        return Observable.from(financeBeanHttpBean.getTitle())
                                .map(new Func1<FinanceBean, Set_Item>() {
                                    @Override
                                    public Set_Item call(FinanceBean financeBean) {
                                        Set_Item item = new Set_Item();
                                        item.title = financeBean.getTitle();
                                        if (TextUtils.isEmpty(item.title)) {
                                            item.title = CusApplication.getContext().getString(R.string.card_hot);
                                        }
                                        item.turl = financeBean.getTurl();
                                        item.content = financeBean.getTurl();
                                        item.img_url = financeBean.getThumb();

                                        return item;
                                    }
                                })
                                ;
                    }
                })
                ;
    }

    /**
     * 轮播图片
     *
     * @param id
     * @return
     */
    public static Observable<Set_Item> getCarousel(String id) {
        return HttpClient.getClient().getCarousel(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<HttpBean<FinanceBean>, Observable<Set_Item>>() {
                    @Override
                    public Observable<Set_Item> call(HttpBean<FinanceBean> financeBeanHttpBean) {
                        return Observable.from(financeBeanHttpBean.getTitle())
                                .map(new Func1<FinanceBean, Set_Item>() {
                                    @Override
                                    public Set_Item call(FinanceBean financeBean) {
                                        Set_Item item = new Set_Item();
                                        item.title = financeBean.getTitle();
                                        item.describe = financeBean.getTurl();
                                        String s = financeBean.getUpdatetime() + "000";
                                        item.content = financeBean.getContent();
                                        item.updatetime = em.sang.com.allrecycleview.utils.Utils.formatDateTime(s);
                                        item.img_url = financeBean.getThumb();
                                        return item;
                                    }
                                })
                                ;
                    }
                })


                ;


    }


    /**
     * 网袋搜索
     *
     * @param position
     * @param page
     * @return
     */
    public static Observable<LoanSearchBean> getLoanSearch(int position, int page) {
        return HttpClient.getClient().getLoanSearch(position, page, 15).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<HttpBean<LoanSearchBean>, Observable<LoanSearchBean>>() {
                    @Override
                    public Observable<LoanSearchBean> call(HttpBean<LoanSearchBean> financeBeanHttpBean) {
                        return Observable.from(financeBeanHttpBean.getTitle());
                    }
                })


                ;
    }

    /**
     * 精养卡提额
     *
     * @param page
     * @return
     */
    public static Observable<Set_Item> getJingCard(String page) {
        HttpService service = HttpClient.getClient();
        return service.getContent("34", page, "20").subscribeOn(Schedulers.io())

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
                                        String s = financeBean.getUpdatetime() + "000";
                                        item.content = financeBean.getContent();
                                        item.updatetime = em.sang.com.allrecycleview.utils.Utils.formatDateTime(s);
                                        item.img_url = financeBean.getThumb();
                                        return item;
                                    }
                                })
                                ;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());

    }

    /**
     * 热身排行榜
     * @param page
     * @return
     */
    public static Observable<Set_Item> getHotApply(int page) {

        return HttpClient.getClient().getHotApply(37,page,20)
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
                                        String s = financeBean.getUpdatetime() + "000";
                                        item.content = financeBean.getContent();
                                        item.updatetime = em.sang.com.allrecycleview.utils.Utils.formatDateTime(s);
                                        item.img_url = financeBean.getThumb();
                                        item.content = financeBean.getF_url();
                                        return item;
                                    }
                                })
                                ;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());

    }


    /**
     * 快速微带
     * @return
     */
    public static Observable<Set_Item> getQuckWei() {

        return HttpClient.getClient().getHotApply(36,0,20)
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
                                        String s = financeBean.getUpdatetime() + "000";
                                        item.content = financeBean.getContent();
                                        item.updatetime = em.sang.com.allrecycleview.utils.Utils.formatDateTime(s);
                                        item.img_url = financeBean.getThumb();
                                        return item;
                                    }
                                })
                                ;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 快速微带
     * @return
     */
    public static Observable<FriendBean> getFriend(int page) {

        return HttpClient.getClient().getFriend(page,15)
                .subscribeOn(Schedulers.io())
                 .map(new Func1<HttpBean<FriendBean>, List<FriendBean>>() {
                     @Override
                     public List<FriendBean> call(HttpBean<FriendBean> friendBeanHttpBean) {
                         return friendBeanHttpBean.getTitle();
                     }
                 })
                .flatMap(new Func1<List<FriendBean>, Observable<FriendBean>>() {
                    @Override
                    public Observable<FriendBean> call(List<FriendBean> friendBeen) {
                        return Observable.from(friendBeen);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<IDBean> getID(String phone){
        return HttpClient.getClient( ).getPerson(phone).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
