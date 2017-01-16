package finance.com.fp.adapter;

import android.content.Context;

import java.util.List;

import em.sang.com.allrecycleview.adapter.DefaultAdapter;
import em.sang.com.allrecycleview.inter.DefaultAdapterViewLisenter;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/3 10:21
 */
public class HomeFragmentAdapter extends DefaultAdapter {
    public HomeFragmentAdapter(Context context, List lists, int itemID, DefaultAdapterViewLisenter lisenter) {
        super(context, lists, itemID, lisenter);
    }

}
