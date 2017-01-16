package finance.com.fp.mode.datafractory;

import android.content.Context;

import java.util.List;

import finance.com.fp.mode.bean.Set_Item;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/11 14:46
 */
public class BaseFractory {

    public static BaseFractory factory;
    public Context context;

    public BaseFractory(Context context){
        this.context=context;
    }


    public List<Set_Item> creatDatas(int item_id) {
        return null;
    }
}
