package finance.com.fp.presenter.inter;


import android.content.Context;

import em.sang.com.allrecycleview.adapter.DefaultAdapter;
import finance.com.fp.mode.bean.Set_Item;

public interface CardActivityPre {

    /**
     * 获取Adapter
     */
    DefaultAdapter<Set_Item> getAdapter(Context context);


    void clearThread();

    void initAllData();
}
