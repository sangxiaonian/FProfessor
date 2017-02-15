package finance.com.fp.presenter;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import em.sang.com.allrecycleview.adapter.DefaultAdapter;
import em.sang.com.allrecycleview.holder.CustomHolder;
import em.sang.com.allrecycleview.inter.DefaultAdapterViewLisenter;
import finance.com.fp.R;
import finance.com.fp.ui.holder.IDHolder;
import finance.com.fp.mode.IDSonDataCom;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.bean.TranInfor;
import finance.com.fp.mode.inter.IDSonDataInter;
import finance.com.fp.presenter.inter.IDSonPreInter;
import finance.com.fp.ui.inter.IDSonView;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/2/9 14:56
 */
public class IDSonPreComl implements IDSonPreInter {

    private final IDSonView view;
    private IDSonDataInter data;
    private TranInfor infor;
    private List<Set_Item> lists = new ArrayList<>();
    private DefaultAdapter<Set_Item> adapter;

    public IDSonPreComl(IDSonView view) {
        this.view = view;
        data = new IDSonDataCom();
    }


    @Override
    public void getTrans(Activity activity, String infors) {
        infor = activity.getIntent().getParcelableExtra(infors);
        if (infor != null) {
            view.initTitle(infor.title);
            lists.clear();
            lists.addAll(data.getDataById(activity, infor.item_id));
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public DefaultAdapter<Set_Item> getAdapter(Context context, int item_card_more) {
        return adapter = new DefaultAdapter<Set_Item>(context, lists, R.layout.item_card_more, new DefaultAdapterViewLisenter<Set_Item>() {
            @Override
            public CustomHolder getBodyHolder(Context context, List<Set_Item> lists, int itemID) {
                IDHolder<Set_Item> holder = new IDHolder<>(context, lists, itemID);
                holder.setOnClickListener(view);
                holder.setCup(0);
                return holder;
            }
        });
    }

    @Override
    public void click(int position, Set_Item item) {
        this.item = item;
        switch (infor.item_id) {
            case 5://个人资信
                personCreditClick(position);
                break;
            case 6://房产
                houseClick(position);
                break;
            case 7://车产
                carClick(position);
                break;
            case 8://保单
                chitClick(position);
                break;
        }
    }

    private void chitClick(int position) {
        switch (position) {
            case 0:
                view.showPickerDialog(data.getStringResous(R.array.chit));
                break;
            case 1:
                view.showPickerDialog(data.getStringResous(R.array.chit_time));
                break;
        }
    }

    private void carClick(int position) {
        switch (position) {
            case 0:
                view.showPickerDialog(data.getStringResous(R.array.car));
                break;
            case 1:
                view.showEditDialog(item.title);
            case 2:
                view.showPickerDialog(data.getStringResous(R.array.car_tiem));
                break;
            case 3:
            case 4:
                view.showPickerDialog(data.getStringResous(R.array.isHas));
                break;
        }

    }

    private void houseClick(int position) {
        switch (position) {
            case 0:
                view.showPickerDialog(data.getStringResous(R.array.house));
                break;
            case 2:
                view.showPickerDialog(data.getStringResous(R.array.house_style));
                break;
            case 1:
            case 3:
                view.showEditDialog(item.title);
                break;
            case 4:
            case 5:
                view.showSelectDialog(data.getStringResous(R.array.isHas));
                break;
        }
    }

    private Set_Item item;

    @Override
    public void changeItem(String o) {
        item.describe = o;
        adapter.notifyDataSetChanged();
    }

    private void personCreditClick(int position) {

        switch (position) {
            case 1://信用卡
            case 3://成功贷款记录
            case 4://淘宝
            case 5://公积金
            case 6://社保
                view.showSelectDialog(data.getStringResous(R.array.isHas));
                break;
            case 0://文化程度
                view.showPickerDialog(data.getStringResous(R.array.education));
                break;
            case 2://两年内信用记录
                view.showPickerDialog(data.getStringResous(R.array.credit_record));
                break;
        }


    }
}
