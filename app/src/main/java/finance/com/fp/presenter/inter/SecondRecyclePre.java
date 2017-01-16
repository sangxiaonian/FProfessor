package finance.com.fp.presenter.inter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;

import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.bean.TranInfor;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/11 11:38
 */
public interface SecondRecyclePre {
    /**
     * 获取从其他界面传来的数据
     * @param context
     * @param tranInfo
     */
    TranInfor getTranData(Activity context, String tranInfo);

    /**
     * 获取recycleView使用的Manager
     * @param context
     * @return
     */
    RecyclerView.LayoutManager getLayoutManager(Context context);

    /**
     * 根据信息获取Adapter
     * @param context
     * @return
     */
    RecyclerView.Adapter getAdapter(Context context);

    RecyclerView.ItemDecoration getDivider(Context context);

    /**
     * 点击条目的时候
     * @param data
     */
    void clickItem(Set_Item data);
}
