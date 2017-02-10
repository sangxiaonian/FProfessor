package finance.com.fp.mode.inter;

import java.util.List;

import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.ui.inter.HomeSonView;
import rx.Observable;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/20 16:36
 */
public interface HomeSonDataInter {
    /**
     * 银行电话详情
     * @return
     */
    List<Set_Item> getAllBalances();

    /**
     * 征信查询
     * @return
     */
    List<Set_Item>  getCredit();

    List<Set_Item> getUtility();

    List<Set_Item> getDataByID(int activityID, int item_id);

    int getItemID(int activityID, int item_id);

    Observable<Set_Item> getData(int activity_id, int item_id, HomeSonView view);
}
