package finance.com.fp.mode.inter;

import java.util.List;

import finance.com.fp.mode.bean.Set_Item;
import rx.Observable;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/19 17:36
 */
public interface CardDataInter {
    List<Set_Item> getTools();

    /**
     * 获取办卡专区的银行图标
     * @return
     */
    Observable<Set_Item> getGVbalances();


}
