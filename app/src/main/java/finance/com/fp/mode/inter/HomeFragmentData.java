package finance.com.fp.mode.inter;


import java.util.List;

import finance.com.fp.mode.bean.Set_Item;

public interface HomeFragmentData {
    List<Set_Item> getTools();

    /**
     * 最新金融口子
     * @return
     */
    List<Set_Item> getfinancialhole();
}
