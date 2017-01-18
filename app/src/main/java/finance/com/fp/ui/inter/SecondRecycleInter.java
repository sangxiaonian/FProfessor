package finance.com.fp.ui.inter;

import android.view.View;

import finance.com.fp.mode.bean.Set_Item;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/11 11:42
 */
public interface SecondRecycleInter {
    /**
     * 初始化条目
     * @param position
     * @param data
     * @param context
     */
    void initBodyHolder(int position, Set_Item data, View context);

    /**
     * 设置Title
     * @param title
     */
    void setTitle(String title);

    void showPhoneDialog(Set_Item data);

    void showHotApply(int position, Set_Item data, View itemView);
}
