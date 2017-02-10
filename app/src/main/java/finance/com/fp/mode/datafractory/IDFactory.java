package finance.com.fp.mode.datafractory;

import java.util.ArrayList;
import java.util.List;

import finance.com.fp.R;
import finance.com.fp.mode.bean.Set_Item;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/2/9 15:13
 */
public class IDFactory extends BaseFractory {


    private static IDFactory factory;

    public static IDFactory getInstance() {
        if (factory == null) {
            synchronized (CardDataFractory.class) {
                if (factory == null) {
                    factory = new IDFactory();
                }
            }
        }
        return factory;
    }


    public List<Set_Item> getPersonCredit(){
        List<Set_Item> list=new ArrayList<>();
        String[] array = context.getResources().getStringArray(R.array.person_credit);
        for (int i = 0; i < array.length; i++) {
            list.add(new Set_Item(0,array[i]));
        }
        return list;
    }

    public List<Set_Item> getHouse(){
        List<Set_Item> list=new ArrayList<>();
        String[] array = context.getResources().getStringArray(R.array.person_house);
        for (int i = 0; i < array.length; i++) {
            Set_Item item = new Set_Item(0, array[i]);
            if (i==1||i==3){
                item.isCheck=true;
            }else {
                item.isCheck=false;

            }
            list.add(item);
        }
        return list;
    }

    /**
     * 保单
     * @return
     */
    public List<Set_Item> getChit(){
        List<Set_Item> list=new ArrayList<>();
        String[] array = context.getResources().getStringArray(R.array.person_chit);
        for (int i = 0; i < array.length; i++) {
            Set_Item item = new Set_Item(0, array[i]);
            list.add(item);
        }
        return list;
    }

    /**
     * 车产
     * @return
     */
    public List<Set_Item> getCar(){
        List<Set_Item> list=new ArrayList<>();
        String[] array = context.getResources().getStringArray(R.array.person_chit);
        for (int i = 0; i < array.length; i++) {
            Set_Item item = new Set_Item(0, array[i]);
            if (i==1){
                item.isCheck=true;
            }else {
                item.isCheck=false;

            }
            list.add(item);
        }
        return list;
    }


    public List<String> getList(int resousID) {
        List<String> list=new ArrayList<>();
        String[] array = context.getResources().getStringArray(resousID);
        for (int i = 0; i < array.length; i++) {
            list.add(array[i]);
        }
        return list;
    }


}
