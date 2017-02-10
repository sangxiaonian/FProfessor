package finance.com.fp.mode.datafractory;

import java.util.ArrayList;
import java.util.List;

import finance.com.fp.R;
import finance.com.fp.mode.bean.Set_Item;

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


    private static ImprotFactory factory;

    public static ImprotFactory getInstance() {
        if (factory == null) {
            synchronized (ImprotFactory.class) {
                if (factory == null) {
                    factory = new ImprotFactory(  );
                }
            }
        }


        return factory;
    }


    @Override
    public List<Set_Item> creatDatas(int item_id) {
        List<Set_Item> list = null;
        switch (item_id) {
            case LOAN_STRAGE:
                list = getBalance();
                break;
            case LOAN_ONE_KEY_IPMORT://一键提额
                list = getOneKey();
                break;
        }


        return list;
    }

    public  List<Set_Item> getOneKey(){
        List<Set_Item> list = new ArrayList<>();
        int[] icons = {R.mipmap.icon_thebankofchina_phone,
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
        String tittles[] = context.getResources().getStringArray(R.array.home_balance_phone);
        for (int i = 0; i < icons.length; i++) {
            String[] split = tittles[i].split("_");
            list.add(new Set_Item(icons[i], split[0], split[1]));
        }

        return list;
    }





    /**
     * 获取所有银行
     *
     * @return
     */
    public List<Set_Item> getBalance() {
        List<Set_Item> list = new ArrayList<>();
        int[] icons = {R.mipmap.icon_thebankofchina_phone,
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
        String tittles[] = context.getResources().getStringArray(R.array.home_balance_phone);
        for (int i = 0; i < icons.length; i++) {
            String[] split = tittles[i].split("_");
            list.add(new Set_Item(icons[i], split[0], split[1]));
        }

        return list;
    }


    public List<Set_Item> getImport() {
        List<Set_Item> datas = new ArrayList<>();
        datas.add(new Set_Item(R.mipmap.icon_mentionthefrontalstrategy, "提额攻略", "各家银行提额攻略"));
        datas.add(new Set_Item(R.mipmap.icon_transactionanalysis, "交易分析", "刷卡多元化，贡献度权威评测"));
        datas.add(new Set_Item(R.mipmap.icon_aquiculturecardtotheforehead, "精养卡提额", "手把手教你最利于提额的用卡姿势"));

        return datas;
    }


    public List<Set_Item> getBalances() {
        List<Set_Item> datas = new ArrayList<>();
        int[] icons = {R.mipmap.icon_chinamerchants_small
                , R.mipmap.icon_chinaciticbank_small,
                R.mipmap.icon_thebankofchina,
                R.mipmap.icon_bankofcommunications,
                R.mipmap.icon_shanghaipudongdevelopmentbank_small,
                R.mipmap.icon_constructionbankccb,
                R.mipmap.icon_thebankofchina,
                R.mipmap.icon_other
        };
        for (int i = 0; i < icons.length; i++) {
            Set_Item item = new Set_Item();
            item.icon_id = icons[i];
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
        }
        return id;
    }
}
