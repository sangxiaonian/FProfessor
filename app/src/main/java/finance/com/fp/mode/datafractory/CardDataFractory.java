package finance.com.fp.mode.datafractory;

import java.util.ArrayList;
import java.util.List;

import finance.com.fp.R;
import finance.com.fp.mode.bean.Set_Item;


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
    public List<Set_Item> creatDatas(int item_id) {
        List<Set_Item> list = null;
        switch (item_id) {

            case 0:
                break;
            case HOT_APPLY://热申排行榜
                list = getHotApply();
                break;
            case ALL_BALANCE:
                list = getAllBalances();
                break;
            case APPLY_PROGRESS:
                list = getCardProgress();
                break;

        }
        return list;
    }

    private List<Set_Item> getCardProgress() {
        List<Set_Item> list = new ArrayList<>();
        int[] icons = {R.mipmap.icon_pinganbank_phone,
                R.mipmap.icon_guangdongdevelopmentbankk_phone,
                R.mipmap.icon_chinaciticbank_phone,
                R.mipmap.icon_chinamerchants_phone,
                R.mipmap.icon_everbrightbank_phone,
                R.mipmap.icon_societegenerale_phone,
                R.mipmap.icon_bankofcommunications_phone,
                R.mipmap.icon_shanghaipudongdevelopmentbank_phone,
                R.mipmap.icon_huaxiabank_phone,
                R.mipmap.icon_agricuralbankof_phone,
                R.mipmap.icon_thebankofchina_phone,
                R.mipmap.icon_bankofbeijing

        };
        String[] titles = context.getResources().getStringArray(R.array.card_pro_query);
        for (int i = 0; i < titles.length; i++) {
            Set_Item item = new Set_Item(icons[i], titles[i]);
            list.add(item);
        }
        return list;
    }


    public List<Set_Item> getAllBalances(){
        List<Set_Item> list = new ArrayList<>();
        int[] icons = {R.mipmap.icon_thebankofchina_phone,
                R.mipmap.icon_agricuralbankof_phone,
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
        String tittles[] = context.getResources().getStringArray(R.array.home_balance_phone);
        for (int i = 0; i < icons.length; i++) {
            String[] split = tittles[i].split("_");
            list.add(new Set_Item(icons[i], split[0]));
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
//        Integer[] icons = {R.mipmap.icon_creditcardofbankofcommunicationsstangards,
//                R.mipmap.icon_citiclpiatinumcreditcard,
//                R.mipmap.icon_shanghaipudongdevelopmentbankstandardcreditcard,
//                R.mipmap.icon_societegeneralestandardcreditcard,
//                R.mipmap.icon_shanghaipudongdevelopmentyoungcreditcard,
//                R.mipmap.icon_everbrightfcreditcard,
//                R.mipmap.icon_citicqatthegoldcard
//        };

        String[] tittles = context.getResources().getStringArray(R.array.card_hot_apply);
        for (int i = 0; i < tittles.length; i++) {
          list.add(  new Set_Item(R.mipmap.icon_citiclpiatinumcreditcard, tittles[i]));
        }
        return list;
    }


    /***
     * 获取工具
     * @return
     */
    public List<Set_Item> getTools() {
        ArrayList<Set_Item> tools = new ArrayList<>();
        Integer[] icons = {R.mipmap.icon_handlecardstrategy, R.mipmap.icon_list, R.mipmap.icon_circleoffeiengs,
                R.mipmap.icon_learningpianner};
        String[] titles = context.getResources().getStringArray(R.array.card_items_title);
        for (int i = 0; i < titles.length; i++) {
            Set_Item item = new Set_Item(icons[i], titles[i]);
            tools.add(item);
        }

        return tools;
    }

    public List<Set_Item> getGVbalances() {
        List<Set_Item> datas = new ArrayList<>();
        String[] tltles = context.getResources().getStringArray(R.array.card_items_balances);
        int[] icons = {R.mipmap.icon_chinaciticbank, R.mipmap.icon_cib,
                R.mipmap.icon_shanghaipudongdevelopmentbank, R.mipmap.icon_everbrightbank,
                R.mipmap.icon_bankofcommunictions, R.mipmap.icon_minshengbank, R.mipmap.icon_chinamerchants
                , R.mipmap.icon_more_balabce};
        for (int i = 0; i < tltles.length; i++) {
            datas.add(new Set_Item(icons[i],tltles[i]));
        }
        return datas;
    }

    /**
     * 本期力荐卡
     * @return
     */
    public List<Set_Item> getCards() {
        List<Set_Item> datas = new ArrayList<>();

        int[] icons = {R.mipmap.home_picture,R.mipmap.home_picture,R.mipmap.home_picture };
        for (int i = 0; i < icons.length; i++) {
            Set_Item item = new Set_Item();
            item.icon_id=icons[i];
            datas.add(item);
        }
        return datas;
    }

    public int getCardLayoutID(int item_id) {
        int id = 0;
        switch (item_id){
            case HOT_APPLY:
                id=R.layout.item_hot_apply;
                break;
            case ALL_BALANCE:
                id=R.layout.item_card_allbalance;
                break;
            case APPLY_PROGRESS:
                id=R.layout.item_card_progress;
                break;
        }

        return id;
    }


}
