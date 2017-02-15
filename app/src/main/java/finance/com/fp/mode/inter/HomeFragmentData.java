package finance.com.fp.mode.inter;


import java.util.List;

import finance.com.fp.mode.bean.Set_Item;
import rx.Observable;

public interface HomeFragmentData {
    List<Set_Item> getTools();

    /**
     * 最新金融口子
     * @return
     */
    Observable<Set_Item> getfinancialhole();

    /**
     * 捞偏门
     * @return
     */
    Observable<Set_Item> getPartialDoor();
}
