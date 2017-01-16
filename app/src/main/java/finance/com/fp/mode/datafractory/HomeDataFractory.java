package finance.com.fp.mode.datafractory;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import finance.com.fp.R;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.utlis.ListDatasFractary;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/11 14:49
 */
public class HomeDataFractory extends BaseFractory {
    public HomeDataFractory(Context context) {
        super(context);
    }

    public static HomeDataFractory getInstance(Context context){
        if (factory==null){
            synchronized (ListDatasFractary.class){
                if (factory==null){
                    factory= new HomeDataFractory(context);
                }
            }
        }
        return (HomeDataFractory) factory;
    }
    @Override
    public List<Set_Item> creatDatas(int item_id) {
        List<Set_Item> list=null;
        switch (item_id){

            case 0:
                break;
            case 1:
                list=getBananceDetials();
                break;
            case 2:
                list=getpbocQuery();
                break;
            case 3:
                list=getUtility();
                break;

        }
        return list;
    }

    /**
     * 征信查询数据
     */
    private List<Set_Item> getpbocQuery() {
        List<Set_Item> list=new ArrayList<>();
        int[] icons = {R.mipmap.icon_creditreport,
            R.mipmap.icon_creditsesame,
            R.mipmap.icon_hisensebeforeuse,
            R.mipmap.icon_thekoalainquiry
        };
        String tittles[] = context.getResources().getStringArray(R.array.home_pboc);
        for (int i = 0; i < icons.length; i++) {
            String[] split = tittles[i].split("_");
            list.add(new Set_Item(icons[i],split[0],split[1]));
        }
        return list;

    }

    /**
     * 获取实用工具
     * @return
     */
    private List<Set_Item> getUtility(){
        List<Set_Item> list=new ArrayList<>();
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
            list.add(new Set_Item(icons[i],tittles[i]));
        }

        return list;
    }

    private  List<Set_Item> getBananceDetials(){
        List<Set_Item> list=new ArrayList<>();
        int[] icons = {R.mipmap.icon_thebankofchina_item,
                R.mipmap.icon_agricuralbankof,
                R.mipmap.icon_icbc,
                R.mipmap.icon_constructionbankccb_icon,
                R.mipmap.icon_bankofcommunications_icon,
                R.mipmap.icon_chinamerchants_icon,
                R.mipmap.icon_shanghaipudongdevelopmentbank_icon,
                R.mipmap.icon_guangdongdevelopmentbankk_icon,
                R.mipmap.icon_huaxiabank,
                R.mipmap.icon_pinganbank,
                R.mipmap.icon_chinaciticbank_icon,
                R.mipmap.icon_minsheengbank,
                R.mipmap.icon_minsheengbank,
        };
        String tittles[] = context.getResources().getStringArray(R.array.home_balance_phone);
        for (int i = 0; i < icons.length; i++) {
            String[] split = tittles[i].split("_");
            list.add(new Set_Item(icons[i],split[0],split[1]));
        }

        return list;
    }
}
