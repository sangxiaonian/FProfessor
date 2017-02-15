package finance.com.fp.mode.datafractory;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import finance.com.fp.CusApplication;
import finance.com.fp.R;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.utlis.ParameterException;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/11 14:49
 */
public class HomeDataFractory extends BaseFractory {
    public static final int BALANCE_CALL = 1;
    public static final int CREDIT = 2;
    public static final int UTILITY_TOLL = 3;
    public static final int MSG_CENTER = 4;


    public static HomeDataFractory fractory;

    public static HomeDataFractory getInstance() {
        if (fractory == null) {
            synchronized (HomeDataFractory.class) {
                if (fractory == null) {
                    fractory = new HomeDataFractory();
                }
            }
        }
        return fractory;
    }

//    @Override
//    public List<Set_Item> creatDatas(int item_id) {
//        List<Set_Item> list ;
//        switch (item_id){
//            case BALANCE_CALL:
//                list= getBalanceCall();
//                break;
//            case CREDIT:
//                list=getCredit();
//                break;
//            case UTILITY_TOLL:
//                list=getUtility();
//                break;
//            case MSG_CENTER:
//                list=new ArrayList<>();
//                break;
//            default:
//                list=new ArrayList<>();
//                break;
//        }
//        return list;
//    }

    public Observable creatObservable(int itemId) {
        Observable observable;
        switch (itemId) {
            case MSG_CENTER:
                observable = HttpFactory.getMsg();
                break;
            case BALANCE_CALL:
                observable = getBalanceCall();
                break;
            case CREDIT:
                observable = getCredit();
                break;
            case UTILITY_TOLL:
                observable = getUtility();
                break;
            default:
                throw new ParameterException("传入ID参数错误");
        }
        return observable;
    }


    /**
     * 征信查询数据
     */
    public Observable<Set_Item> getCredit() {
        Integer[] icons = {R.mipmap.icon_creditreport,
                R.mipmap.icon_creditsesame,
                R.mipmap.icon_hisensebeforeuse,
                R.mipmap.icon_thekoalainquiry
        };
        String tittles[] = context.getResources().getStringArray(R.array.home_pboc);


        return getZip(icons, tittles);

    }

    /**
     * 获取实用工具
     *
     * @return
     */
    public Observable<Set_Item> getUtility() {
        Integer[] icons = {R.mipmap.icon_thtenterpriseinformationqueries,
                R.mipmap.icon_enterprisecreditreportingqueries,
                R.mipmap.icon_personalcreditregistryquery,
                R.mipmap.icon_reportthelossofmyidcard,
                R.mipmap.icon_idquery,
                R.mipmap.icon_laolaiquery,
                R.mipmap.icon_mcccodequery,
                R.mipmap.icon_unionpaytransactionquery,
                R.mipmap.icon_androidroot,
                R.mipmap.icon_packtoforcecrtifact,
                R.mipmap.icon_postersgenerator,
                R.mipmap.icon_comingsoon
        };
        String tittles[] = context.getResources().getStringArray(R.array.home_utility);


        return Observable.zip(Observable.from(icons)
                , Observable.from(tittles), new Func2<Integer, String, Set_Item>() {
                    @Override
                    public Set_Item call(Integer integer, String split) {

                        return new Set_Item(integer, split);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                ;
    }

    public Observable<Set_Item> getBalanceCall() {

        Integer[] icons = {R.mipmap.icon_thebankofchina_phone,
                R.mipmap.icon_agricuralbankof_phone,
                R.mipmap.icon_icbc_phone,
                R.mipmap.icon_constructionbankccb_phone,
                R.mipmap.icon_bankofcommunications_phone,
                R.mipmap.icon_chinamerchants_phone,
                R.mipmap.icon_shanghaipudongdevelopmentbank_phone,
                R.mipmap.icon_guangdongdevelopmentbankk_phone,
                R.mipmap.icon_huaxiabank_phone,
                R.mipmap.icon_pinganbank_phone,
                R.mipmap.icon_everbrightbank_phone,
                R.mipmap.icon_chinaciticbank_phone,
                R.mipmap.icon_societegenerale_phone,
                R.mipmap.icon_minsheengbank_phone,
                R.mipmap.icon_zheshangbank_phone,
                R.mipmap.icon_citibank_phone
        };
        String[] tittles = context.getResources().getStringArray(R.array.home_balance_phone);

        return getZip(icons, tittles);

    }

    @NonNull
    private Observable<Set_Item> getZip(Integer[] icons, String[] tittles) {
        return Observable.zip(Observable.from(icons)
                , Observable.from(tittles).map(new Func1<String, String[]>() {
                    @Override
                    public String[] call(String s) {

                        return s.split("_");
                    }
                }), new Func2<Integer, String[], Set_Item>() {
                    @Override
                    public Set_Item call(Integer integer, String[] split) {

                        return new Set_Item(integer, split[0], split[1]);
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 获取主页实用工具四个图标
     *
     * @return
     */
    public List<Set_Item> getTools() {
        final List<Set_Item> list = new ArrayList<>();
        context = CusApplication.getContext();
        String[] titles = context.getResources().getStringArray(R.array.home_items_title);

        Integer[] icons = {R.mipmap.icon_planner, R.mipmap.icon_telephone, R.mipmap.icon_creditreportingqueries, R.mipmap.icon_tool};

        for (int i = 0; i < icons.length; i++) {
            list.add(new Set_Item(icons[i], titles[i]));
        }

        return list;
    }

    public int getHomeLayoutID(int item_id) {
        int id = 0;
        switch (item_id) {
            case 1://银行电话
                id = R.layout.item_phones;
                break;
            case 3://使用工具
                id = R.layout.view_gv_card;
                break;
            case 2://征信查询
                id = R.layout.item_tool_pboc;
                break;
            case 4://消息中心
                id = R.layout.item_msg;
                break;
        }
        return id;
    }
}