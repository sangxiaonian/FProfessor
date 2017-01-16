package finance.com.fp.mode.inter;

import android.content.Context;

import java.util.List;

import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.bean.TranInfor;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/11 11:40
 */
public interface SecondRecycleData {
    /**
     * 根据不同页面获取不同的条目
     * @param activity_id
     * @return
     */
    int getItemId(int activity_id);

    /**
     * 根据不同页面获取数据
     * @param context
     * @param tranInfor
     * @return
     */
    List<Set_Item> getItemDatas(Context context, TranInfor tranInfor);


}
