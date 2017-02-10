package finance.com.fp.mode;

import java.util.ArrayList;
import java.util.List;

import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.datafractory.HomeDataFractory;
import finance.com.fp.mode.inter.HomeFragmentData;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/19 15:08
 */
public class HomeFragmentDataCom implements HomeFragmentData {
    private HomeDataFractory fractory;

    public HomeFragmentDataCom(){
        fractory = HomeDataFractory.getInstance();
    }


    @Override
    public List<Set_Item> getTools() {
        return fractory.getTools();
    }

    @Override
    public List<Set_Item> getfinancialhole() {
        ArrayList<Set_Item> list = new ArrayList<>();
        list.add(new Set_Item(0,"测试数据"));
        return list;
    }
}
