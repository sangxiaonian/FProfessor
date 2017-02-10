package finance.com.fp.mode.inter;

import java.util.List;

import finance.com.fp.mode.bean.Set_Item;

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
    List<Set_Item> getGVbalances();

    /**
     * 获取力荐银行
     * @return
     */
    List<Set_Item> getCards();
}
