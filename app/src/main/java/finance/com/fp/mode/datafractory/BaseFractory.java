package finance.com.fp.mode.datafractory;

import android.content.Context;

import java.util.List;

import finance.com.fp.CusApplication;
import finance.com.fp.mode.bean.Set_Item;
import rx.Observable;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/11 14:46
 */
public class BaseFractory {


    public Context context;


    public DataLoadLisetner lisetner;

    public BaseFractory(){
        this.context= CusApplication.getContext();
    }


    public List<Set_Item> creatDatas(int item_id) {
        return null;
    }

    public Observable creatObservable(int itemId, int page){return null;}
}
