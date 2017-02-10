package finance.com.fp.ui.inter;

import android.view.View;

import finance.com.fp.mode.bean.Set_Item;

public interface HomeSonView {

    /**
     * 初始化页面标题
     * @param title
     */
    void initTitle(String title);

    /**
     * home 消息中心条目
     * @param itemView
     * @param item
     */
    void msgItemClick(View itemView, Set_Item item);

    /**
     * 征信查询
     * @param itemView
     * @param item
     */
    void creditItemClick(View itemView, Set_Item item);

    /**
     * 使用工具
     * @param itemView
     * @param item
     */
    void utilityItemClick(View itemView, Set_Item item);

    void initItemView(View itemView, Set_Item item, int position);

    /**
     * 银行电话拨打
     * @param item
     */
    void showPhoneDialog(Set_Item item);

    /**
     * 所有银行
     * @param item
     */
    void allBalanceItemClick(Set_Item item);

    /**
     * 申卡进度查询
     * @param itemView
     * @param item
     */
    void applyQueryItemClick(View itemView, Set_Item item);

    /**
     * 热门申请
     * @param itemView
     * @param item
     */
    void hotapplyItemClick(View itemView, Set_Item item);

    /**
     * 提额攻略
     * @param itemView
     * @param item
     */
    void loan_strage_item(View itemView, Set_Item item);

    /**
     * 一键提额
     * @param itemView
     * @param item
     */
    void loan_one_key_item(View itemView, Set_Item item);



    void loadSuccess();

    void loadFail();
}
