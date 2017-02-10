package finance.com.fp.presenter.inter;


import android.content.Context;

import em.sang.com.allrecycleview.adapter.DefaultAdapter;
import finance.com.fp.mode.bean.Set_Item;

public interface ImportInter {

    DefaultAdapter<Set_Item> getAdapter(Context context);

    void stopCarousel();
}
