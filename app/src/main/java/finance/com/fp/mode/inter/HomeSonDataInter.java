package finance.com.fp.mode.inter;

import finance.com.fp.mode.bean.Set_Item;
import rx.Observable;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/20 16:36
 */
public interface HomeSonDataInter {


    int getItemID(int activityID, int item_id);

    Observable<Set_Item> getData(int activity_id, int item_id);
}
