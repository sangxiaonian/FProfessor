package sang.com.xdialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import sang.com.xdialog.adapter.DialogAdapter;
import sang.com.xdialog.inter.OnEntryClickListener;
import sang.com.xdialog.utils.JLog;
import sang.com.xdialog.utils.RecycleViewDivider;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/2/9 16:23
 */
public class SelectDialog extends XDialog<List<String>> {

    private RecyclerView recyclerView;

    private DialogAdapter adapter;
    private int itemID=R.layout.item_text;
    public SelectDialog(Context context) {
        this(context, R.style.DialogCutTheme);
    }

    public SelectDialog(Context context, int themeResId) {
        super(context, themeResId);
        JLog.i(R.style.DialogCutTheme+">>>>>>>>   "+themeResId);
    }

    protected SelectDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.copyFrom(getWindow().getAttributes());
        //设置dialog的界面宽度
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置dialog高度为包裹内容
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //设置dialog的重心
        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        //dialog.getWindow().setLayout(width-(width/6), LayoutParams.WRAP_CONTENT);
        //用这个方法设置dialog大小也可以，但是这个方法不能设置重心之类的参数，推荐用Attributes设置
        //最后把这个参数对象设置进去，即与dialog绑定
        getWindow().setAttributes(params);
        window.setWindowAnimations(R.style.dialog_down);
        iniViews();
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        setContentView(R.layout.select_dialog);
    }

    @Override
    protected void iniViews() {
        super.iniViews();
        recyclerView = (RecyclerView) findViewById(R.id.rc);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.VERTICAL, R.drawable.cut_line));
        adapter = new DialogAdapter(getContext(), datas, itemID);
        adapter.setListener(new OnEntryClickListener<String>() {
            @Override
            public void onClick(Dialog dialog, int which, String data) {
                if (entryListener != null) {
                    entryListener.onClick(SelectDialog.this.dialog, which, data);
                }
            }
        });

        recyclerView.setAdapter(adapter);
    }


    @Override
    public void setDatas(List<String> datas) {

        if (this.datas == null) {
            this.datas = new ArrayList<>();
        } else {
            this.datas.clear();
        }
        this.datas.addAll(datas);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void show(List<String> datas) {
        setDatas(datas);
        super.show(datas);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    protected void changeLayoutByStyle(int style) {
        JLog.i(style+">>>>>>>>>>");
        switch (style) {
            case SELECT_WIDTH_FULL:
                itemID = R.layout.item_text_full;
                break;
            default:
                itemID = R.layout.item_text;
                break;
        }
    }
}
