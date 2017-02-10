package finance.com.fp.mode.inter;


import android.app.Activity;

import java.util.List;

import finance.com.fp.mode.bean.Set_Item;

public interface IDSonDataInter {

    List<Set_Item> getDataById(Activity activity, int item_id);

    List<String> getDataByposition(int position);


    /**
     * 根据资源ID获取集合
     * @param resousID
     * @return
     */
    List<String> getStringResous(int resousID);
}
