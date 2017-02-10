package finance.com.fp.mode;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.datafractory.IDFactory;
import finance.com.fp.mode.inter.IDSonDataInter;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/2/9 14:54
 */
public class IDSonDataCom implements IDSonDataInter{

    private IDFactory factory;
    public IDSonDataCom(){
        factory=IDFactory.getInstance();
    }


    @Override
    public List<Set_Item> getDataById(Activity activity, int item_id) {
        List<Set_Item> list = new ArrayList<>();
        switch (item_id) {
            case 5://个人资信
                list.addAll(factory.getPersonCredit());
                break;
            case 6://房产
                list.addAll(factory.getHouse());
                break;
            case 7://车产
                list.addAll(factory.getCar());
                break;
            case 8://保单
                list.addAll(factory.getChit());
                break;
        }
         return list;
    }

    @Override
    public List<String> getDataByposition(int position) {
        return null;
    }



    @Override
    public List<String> getStringResous(int resousID) {
        return factory.getList(resousID);
    }
}
