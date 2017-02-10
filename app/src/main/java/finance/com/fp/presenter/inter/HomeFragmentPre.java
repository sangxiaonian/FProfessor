package finance.com.fp.presenter.inter;


import android.content.Context;

import em.sang.com.allrecycleview.adapter.RefrushAdapter;
import finance.com.fp.mode.bean.Set_Item;

public interface HomeFragmentPre  {

    /**
     * 获取Adapter
     */
    RefrushAdapter<Set_Item> getAdapter(Context context);

    /**
     * 初始化Adapter
     */
    RefrushAdapter<Set_Item> initAdapter(Context context);

    /**
     * 初始化FindAdapter
     * @param context
     * @return
     */
    RefrushAdapter<Set_Item> initFindAdapter(Context context);
}
