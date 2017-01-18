package finance.com.fp.mode.datafractory;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import finance.com.fp.R;
import finance.com.fp.mode.bean.Set_Item;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;


/**
 * Description：办卡功能数据
 *
 * @Author：桑小年
 * @Data：2017/1/11 14:49
 */
public class CardDataFractory extends BaseFractory {
    public CardDataFractory(Context context) {
        super(context);
    }
    public static CardDataFractory cardDataFractory;
    public static CardDataFractory getInstance(Context context) {
        if (cardDataFractory == null) {
            synchronized (CardDataFractory.class) {
                if (cardDataFractory == null) {
                    cardDataFractory = new CardDataFractory(context);
                }
            }
        }
        return cardDataFractory;
    }

    @Override
    public List<Set_Item> creatDatas(int item_id) {
        List<Set_Item> list = null;
        switch (item_id) {

            case 0:
                break;
            case 1://热申排行榜
                list = getHotApply();
                break;
            case 2:
                list = getpbocQuery();
                break;
            case 3:
                list = getUtility();
                break;

        }
        return list;
    }

      List<Set_Item> lists;
    public List<Set_Item> getAllBanances(final DataLoadLisetner<Set_Item> lisetner){
        Integer[] icons = {R.mipmap.icon_chinaciticbank,
                R.mipmap.icon_societegenerale,
                R.mipmap.icon_shanghaipudongdevelopmentbank,
                R.mipmap.icon_bankofcommunications,
                R.mipmap.icon_minsheengbank,
                R.mipmap.icon_chinamerchants,
                R.mipmap.icon_everbrightbank,
                R.mipmap.icon_agricuralbankof,
                R.mipmap.icon_huaxiabank,
                R.mipmap.icon_constructionbankccb,
                R.mipmap.icon_pinganbank,
                R.mipmap.icon_guangdongdevelopmentbankk,
                R.mipmap.icon_thebankofchina,
                R.mipmap.icon_zheshangbank,
                R.mipmap.icon_citibank,
        };

        String[] titles = context.getResources().getStringArray(R.array.all_balances);
        Observable.zip(Observable.from(icons), Observable.from(titles), new Func2<Integer, String, Set_Item>() {
            @Override
            public Set_Item call(Integer integer, String s) {
                return new Set_Item(integer,s);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Set_Item>() {
                    @Override
                    public void onCompleted() {
                        if (lisetner!=null){
                            lisetner.loadOver(lists);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Set_Item set_item) {
                        lists.add(set_item);
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        if (lists==null){
                            lists=new ArrayList<Set_Item>();
                        }else {
                            lists.clear();
                        }
                    }
                });

        return lists;

    }
    /**
     * 征信查询数据
     */
    private List<Set_Item> getpbocQuery() {
        List<Set_Item> list = new ArrayList<>();
        int[] icons = {R.mipmap.icon_creditreport,
                R.mipmap.icon_creditsesame,
                R.mipmap.icon_hisensebeforeuse,
                R.mipmap.icon_thekoalainquiry
        };
        String tittles[] = context.getResources().getStringArray(R.array.home_pboc);
        for (int i = 0; i < icons.length; i++) {
            String[] split = tittles[i].split("_");
            list.add(new Set_Item(icons[i], split[0], split[1]));
        }
        return list;

    }

    /**
     * 获取实用工具
     *
     * @return
     */
    private List<Set_Item> getUtility() {
        List<Set_Item> list = new ArrayList<>();
        int[] icons = {R.mipmap.icon_thtenterpriseinformationqueries,
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
        for (int i = 0; i < icons.length; i++) {
            list.add(new Set_Item(icons[i], tittles[i]));
        }

        return list;
    }

    /**
     * 热审排行榜
     *
     * @return
     */
    private List<Set_Item> getHotApply() {
        final List<Set_Item> list = new ArrayList<>();
        Integer[] icons = {R.mipmap.icon_creditcardofbankofcommunicationsstangards,
                R.mipmap.icon_citiclpiatinumcreditcard,
                R.mipmap.icon_shanghaipudongdevelopmentbankstandardcreditcard,
                R.mipmap.icon_societegeneralestandardcreditcard,
                R.mipmap.icon_shanghaipudongdevelopmentyoungcreditcard,
                R.mipmap.icon_everbrightfcreditcard,
                R.mipmap.icon_citicqatthegoldcard
        };

        String[] tittles = context.getResources().getStringArray(R.array.card_hot_apply);
        Observable.zip(Observable.from(icons), Observable.from(tittles), new Func2<Integer, String, Set_Item>() {

            @Override
            public Set_Item call(Integer integer, String s) {
                return new Set_Item(integer, s);
            }
        }).subscribe(new Subscriber<Set_Item>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Set_Item set_item) {
                list.add(set_item);
            }
        });

        return list;
    }



}
