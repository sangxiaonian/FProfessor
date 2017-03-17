package finance.com.fp.mode.datafractory;

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
 * Description：办卡功能数据
 *
 * @Author：桑小年
 * @Data：2017/1/11 14:49
 */
public class CardDataFractory extends BaseFractory {
    public static final int HOT_APPLY = 1;
    public static final int ALL_BALANCE = 2;
    public static final int APPLY_PROGRESS = 3;

    private static CardDataFractory cardDataFractory;

    public static CardDataFractory getInstance() {
        if (cardDataFractory == null) {
            synchronized (CardDataFractory.class) {
                if (cardDataFractory == null) {
                    cardDataFractory = new CardDataFractory();
                }
            }
        }
        return cardDataFractory;
    }


    @Override
    public Observable creatObservable(int itemId, int page) {
        Observable observable;
        switch (itemId) {

            case HOT_APPLY://热申排行榜
                observable = getHotApply(page);
                break;
            case ALL_BALANCE:
                observable = getAllBalances(page);
                break;
            case APPLY_PROGRESS:
                observable = getCardProgress(page);
                break;
            default:
                throw new ParameterException("传入ID参数错误");
        }

        return observable;
    }

    private Observable<Set_Item> getCardProgress(int page) {
        Integer[] icons = {R.mipmap.icon_pinganbank_phone,
                R.mipmap.icon_guangdongdevelopmentbankk_phone,
                R.mipmap.icon_chinaciticbank_phone,
                R.mipmap.icon_chinamerchants_phone,
                R.mipmap.icon_everbrightbank_phone,
                R.mipmap.icon_societegenerale_phone,
                R.mipmap.icon_bankofcommunications_phone,
                R.mipmap.icon_shanghaipudongdevelopmentbank_phone,
                R.mipmap.icon_agricuralbankof_phone,
                R.mipmap.icon_bankofbeijing

        };
        String[] tittles;
        if (page==0){
            tittles = context.getResources().getStringArray(R.array.card_pro_query);
        }else {
            tittles=new String[0];
        }

        return Observable.zip(Observable.from(icons), Observable.from(tittles)
                        .map(new Func1<String, String[]>() {
                            @Override
                            public String[] call(String s) {
                                return s.split("_");
                            }
                        })

                , new Func2<Integer, String[], Set_Item>() {
                    @Override
                    public Set_Item call(Integer integer, String[] s) {
                        Set_Item item = new Set_Item(integer, s[0]);
                        item.content=s[1];
                        for (int i = 2; i <s.length ; i++) {
                            item.content+="_"+s[i];
                        }
                        return item;
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<Set_Item> getAllBalances(int page) {


        return HttpFactory.getAllBance();
    }

    /**
     * 热审排行榜
     *
     * @return
     * @param page
     */
    private Observable<Set_Item> getHotApply(int page) {

        return HttpFactory.getHotApply(page);
    }




    /***
     * 获取工具
     *
     * @return
     */
    public List<Set_Item> getTools() {
        ArrayList<Set_Item> tools = new ArrayList<>();
        Integer[] icons = {R.mipmap.icon_handlecardstrategy, R.mipmap.icon_list,
//                R.mipmap.icon_circleoffeiengs,
                R.mipmap.icon_learningpianner};
        String[] titles = context.getResources().getStringArray(R.array.card_items_title);
        for (int i = 0; i < titles.length; i++) {
            Set_Item item = new Set_Item(icons[i], titles[i]);
            tools.add(item);
        }

        return tools;
    }

    public Observable<Set_Item> getGVbalances() {

        return HttpFactory.getAllBance().take(7);
    }




    public int getCardLayoutID(int item_id) {
        int id = 0;
        switch (item_id) {
            case HOT_APPLY:
                id = R.layout.item_hot_apply;
                break;
            case ALL_BALANCE:
                id = R.layout.item_card_allbalance;
                break;
            case APPLY_PROGRESS:
                id = R.layout.item_card_progress;
                break;
        }

        return id;
    }

}
