package finance.com.fp.presenter.inter;

import android.app.Activity;
import android.content.Context;

import em.sang.com.allrecycleview.adapter.DefaultAdapter;
import finance.com.fp.mode.bean.Set_Item;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/2/9 14:56
 */
public interface IDSonPreInter {
    void getTrans(Activity activity, String infors);

    DefaultAdapter<Set_Item> getAdapter(Context context, int item_card_more);

    void click(int position, Set_Item item);

    /**
     * 更改Item数据
     * @param o
     *
     */
    void changeItem(String o);

    String getData();

    boolean getIsChange();
}
