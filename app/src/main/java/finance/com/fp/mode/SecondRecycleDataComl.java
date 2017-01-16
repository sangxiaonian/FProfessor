package finance.com.fp.mode;

import android.content.Context;

import java.util.List;

import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.bean.TranInfor;
import finance.com.fp.mode.datafractory.BaseFractory;
import finance.com.fp.mode.datafractory.HomeDataFractory;
import finance.com.fp.mode.inter.SecondRecycleData;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/11 11:40
 */
public class SecondRecycleDataComl implements SecondRecycleData {

    BaseFractory fractory;
    private List<Set_Item> lists;

    @Override
    public int getItemId(int activity_id) {
//        switch (activity_id)
        return 0;
    }

    @Override
    public List<Set_Item> getItemDatas(Context context, TranInfor tranInfor) {
        switch (tranInfor.activity_id){
            case 0://0:homeFragment
                lists= HomeDataFractory.getInstance(context).creatDatas(tranInfor.item_id);
                break;

        }

        return lists;
    }




}
