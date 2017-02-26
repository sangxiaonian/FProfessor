package finance.com.fp.presenter.inter;


import android.content.Context;

import em.sang.com.allrecycleview.adapter.DefaultAdapter;
import finance.com.fp.mode.bean.LoanSearchBean;

public interface LoanInter {

    DefaultAdapter<LoanSearchBean> getAdapter(Context context);

    void stopCarousel();
}
