package finance.com.fp.presenter.inter;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import finance.com.fp.mode.bean.Set_Item;

public interface HomeSonPreInter {

    /**
     * 获取其他页面传递的信息
     * @param activity
     * @param name
     */
    void getTranInfor(Activity activity, String name);


    RecyclerView.LayoutManager getManager(Context context);

    RecyclerView.ItemDecoration getDivider(Context context);

    RecyclerView.Adapter getAdapter(Context  context);

    void onItemClick(View itemView, Set_Item item);

    void setDatas(Context context);

    void unsubscribe();

    void pageAdd();
}
