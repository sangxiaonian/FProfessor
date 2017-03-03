package finance.com.fp.mode.datafractory;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

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
 * @Data：2017/1/20 15:23
 */
public class ImprotFactory extends BaseFractory {
    /**
     * 提额攻略
     */
    public static final int LOAN_STRAGE = 0;
    public static final int LOAN_ONE_KEY_IPMORT = 4;
    public static final int LOAN_JING = 2;


    private static ImprotFactory factory;

    public static ImprotFactory getInstance() {
        if (factory == null) {
            synchronized (ImprotFactory.class) {
                if (factory == null) {
                    factory = new ImprotFactory();
                }
            }
        }
        return factory;
    }


    @Override
    public Observable creatObservable(int itemId, int page) {
        Observable observable = null;
        switch (itemId) {
            case LOAN_STRAGE:
                observable = getBalance(page);
                break;
            case LOAN_ONE_KEY_IPMORT://一键提额
                observable = getOneKey(page);
                break;
            case LOAN_JING:
                observable = getJingCard(page);
                break;
            default:
                throw new ParameterException("传入ID参数错误");
        }

        return observable;
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


    public Observable<Set_Item> getOneKey(int page) {
        Integer[] icons = {
                R.mipmap.icon_chinamerchants_phone,
                R.mipmap.icon_guangdongdevelopmentbankk_phone,
                R.mipmap.icon_bankofcommunications_phone,
                R.mipmap.icon_shanghaipudongdevelopmentbank_phone,
                R.mipmap.icon_constructionbankccb_phone,
                R.mipmap.icon_chinaciticbank_phone,
                R.mipmap.icon_agricuralbankof_phone,
                R.mipmap.icon_pinganbank_phone,
                R.mipmap.icon_societegenerale_phone,
                R.mipmap.icon_icbc_phone,
                R.mipmap.icon_minsheengbank_phone,
                R.mipmap.icon_thebankofchina_phone,
                R.mipmap.icon_everbrightbank_phone,
                R.mipmap.icon_huaxiabank_phone,
        };

        String[] tittles;
        if (page == 0) {
            tittles = context.getResources().getStringArray(R.array.one_key);
        } else {
            tittles = new String[0];
        }
        return getZip(icons, tittles);
    }



    /**
     * 提额攻略
     *
     * @return
     */
    public Observable<Set_Item> getBalance(int page) {

        return HttpFactory.getLoan(String.valueOf(page), "20");

    }

    /**
     * 精养卡提额
     *
     * @return
     */
    public Observable<Set_Item> getJingCard(int page) {

        return HttpFactory.getJingCard(String.valueOf(page));

    }


    public List<Set_Item> getImport() {
        List<Set_Item> datas = new ArrayList<>();
        datas.add(new Set_Item(R.mipmap.icon_mentionthefrontalstrategy, "提额攻略", "各家银行提额攻略"));

        datas.add(new Set_Item(R.mipmap.icon_aquiculturecardtotheforehead, "精养卡提额", "手把手教你最利于提额的用卡姿势"));

        return datas;
    }


    public List<Set_Item> getBalances() {
        List<Set_Item> datas = new ArrayList<>();
        int[] icons = {R.mipmap.icon_chinamerchants_small//招商银行
                , R.mipmap.icon_chinaciticbank_small,//中信银行
                R.mipmap.icon_thebankofchina,//中国银行
                R.mipmap.icon_bankofcommunications,//交通银行
                R.mipmap.icon_shanghaipudongdevelopmentbank_small,//浦发银行
                R.mipmap.icon_constructionbankccb,//建设银行
                R.mipmap.icon_guangdongdevelopmentbankk_icon,//广发银行
                R.mipmap.icon_other//其他
        };
        String[] titles={"招商银行","中信银行","中国银行","交通银行","浦发银行","建设银行","广发银行","其他"};
        for (int i = 0; i < icons.length; i++) {
            Set_Item item = new Set_Item();
            item.icon_id = icons[i];
            item.title=titles[i];
            datas.add(item);
        }
        return datas;
    }

    public int getImportID(int item_id) {
        int id = 0;
        switch (item_id) {
            case LOAN_STRAGE:
                id = R.layout.item_import_strage;
                break;
            case LOAN_ONE_KEY_IPMORT:
                id = R.layout.item_import_one_key_import;
                break;
            case LOAN_JING:
                id = R.layout.item_loan_strategy;
                break;
        }
        return id;
    }
}
