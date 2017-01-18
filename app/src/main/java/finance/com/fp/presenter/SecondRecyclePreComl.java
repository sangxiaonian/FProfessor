package finance.com.fp.presenter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import java.util.List;

import em.sang.com.allrecycleview.adapter.DefaultAdapter;
import em.sang.com.allrecycleview.holder.CustomHolder;
import em.sang.com.allrecycleview.inter.DefaultAdapterViewLisenter;
import finance.com.fp.R;
import finance.com.fp.mode.SecondRecycleDataComl;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.bean.TranInfor;
import finance.com.fp.mode.inter.SecondRecycleData;
import finance.com.fp.presenter.inter.SecondRecyclePre;
import finance.com.fp.ui.inter.SecondRecycleInter;
import finance.com.fp.utlis.DividerGridItemDecoration;
import finance.com.fp.utlis.RecycleViewDivider;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/11 11:39
 */
public class SecondRecyclePreComl implements SecondRecyclePre {
    private SecondRecycleData data;
    private SecondRecycleInter view;
    private TranInfor tranInfor;

    public SecondRecyclePreComl(SecondRecycleInter view) {
        this.view = view;
        data = new SecondRecycleDataComl();

    }

    @Override
    public TranInfor getTranData(Activity context, String tranInfo) {
        tranInfor = context.getIntent().getParcelableExtra(tranInfo);
        view.setTitle(tranInfor.title);
        return tranInfor;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager(Context context) {
        RecyclerView.LayoutManager manager = null;
        switch (tranInfor.manager_type) {
            case 0:
                manager = new LinearLayoutManager(context);
                ((LinearLayoutManager) manager).setOrientation(tranInfor.manager_orientation);
                break;
            case 1:
                manager = new GridLayoutManager(context, tranInfor.manager_row_num);
                ((GridLayoutManager) manager).setOrientation(tranInfor.manager_orientation);
                break;
            case 2:
                manager = new StaggeredGridLayoutManager(tranInfor.manager_row_num, tranInfor.manager_orientation);
                break;
            default:
                manager = new LinearLayoutManager(context);
                break;
        }

        return manager;
    }

    @Override
    public RecyclerView.Adapter getAdapter(Context context) {

        return new DefaultAdapter<>(context, data.getItemDatas(context, tranInfor), tranInfor.layoutId, new DefaultAdapterViewLisenter<Set_Item>() {
            @Override
            public CustomHolder getBodyHolder(Context context, List<Set_Item> lists, int itemID) {
                return new CustomHolder<Set_Item>(context, lists, itemID) {
                    @Override
                    public void initView(int position, List<Set_Item> datas, Context context) {
                        view.initBodyHolder(position, datas.get(position), itemView);
                    }
                };
            }
        });
    }

    @Override
    public RecyclerView.ItemDecoration getDivider(Context context) {
        RecyclerView.ItemDecoration itemDecoration = null;
        switch (tranInfor.manager_type) {
            case 0:
                itemDecoration = new RecycleViewDivider(context,tranInfor.manager_orientation);
                break;
            case 1:
            case 2:
                itemDecoration= new DividerGridItemDecoration(context, R.drawable.divider_line);
                break;
            default:
                itemDecoration = new RecycleViewDivider(context,tranInfor.manager_orientation);
                break;
        }
        return itemDecoration;
    }

    @Override
    public void clickItem(Set_Item data) {
        switch (tranInfor.activity_id){
            case 0://0:homeFragment
                onHomeFragment(data);
                break;


        }
    }

    @Override
    public void initCustomView(int position, Set_Item data, View itemView) {
        switch (tranInfor.activity_id){
            case 1://1:card
                if (tranInfor.item_id==1)
                view.showHotApply(position,  data,   itemView);
                break;

        }

    }

    private void onHomeFragment(Set_Item data) {
        switch (tranInfor.item_id){
            case 0:
                break;
            case 1:
                view.showPhoneDialog(data);
                break;
            case 2:
                break;
            case 3:
                break;

        }
    }
}
