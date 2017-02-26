package finance.com.fp.mode.inter;


import java.util.List;

import finance.com.fp.mode.bean.LoanSearchBean;
import finance.com.fp.mode.bean.Set_Item;
import rx.Observable;

public interface HomeFragmentData {
    List<Set_Item> getTools();

    /**
     * 最新金融口子
     * @return
     * @param page
     */
    Observable<Set_Item> getfinancialhole(int page);

    /**
     * 捞偏门
     * @return
     * @param d
     */
    Observable<Set_Item> getPartialDoor(int d);

    Observable<LoanSearchBean> getLoanSearch();
}
