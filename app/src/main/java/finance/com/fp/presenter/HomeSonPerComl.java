package finance.com.fp.presenter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import em.sang.com.allrecycleview.adapter.DefaultAdapter;
import em.sang.com.allrecycleview.holder.CustomHolder;
import em.sang.com.allrecycleview.inter.DefaultAdapterViewLisenter;
import finance.com.fp.mode.HomeSonDataCom;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.bean.TranInfor;
import finance.com.fp.mode.datafractory.CardDataFractory;
import finance.com.fp.mode.datafractory.HomeDataFractory;
import finance.com.fp.mode.datafractory.ImprotFactory;
import finance.com.fp.mode.inter.HomeSonDataInter;
import finance.com.fp.presenter.inter.HomeSonPreInter;
import finance.com.fp.ui.inter.HomeSonView;
import rx.Subscriber;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/20 16:35
 */
public class HomeSonPerComl   implements HomeSonPreInter {

    private HomeSonView view;
    private HomeSonDataInter data;
    private TranInfor infor;
    private ManagerFractory fractory;
    private RecyclerView.Adapter adapter;

    public HomeSonPerComl(HomeSonView view) {
        this.view = view;
        fractory = ManagerFractory.getFractory();
        data = new HomeSonDataCom();
    }


    @Override
    public void getTranInfor(Activity activity, String name) {
        infor = activity.getIntent().getParcelableExtra(name);
        if (infor != null) {
            view.initTitle(infor.title);
        }

    }

    @Override
    public RecyclerView.LayoutManager getManager(Context context) {

        return fractory.getLayoutManager(context, infor.activity_id, infor.item_id);
    }


    @Override
    public RecyclerView.ItemDecoration getDivider(Context context) {
        return fractory.getDivider(context, infor.activity_id, infor.item_id);
    }

    @Override
    public RecyclerView.Adapter getAdapter(Context context) {
        return getHomeAdapter(context, infor.activity_id, infor.item_id);
    }

    @Override
    public void onItemClick(final View itemView, final Set_Item item) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickById(itemView, item);
            }
        });
    }

    @Override
    public void setDatas(Context context) {
        data.getData(infor.activity_id, infor.item_id, view).subscribe(getRefrushSubscriber());
    }


    private void clickById(View itemView, Set_Item item) {
        switch (infor.activity_id) {
            case 0://home
                homeClick(itemView, item, infor.item_id);
                break;
            case 1://cardActivity
                cardClick(itemView, item, infor.item_id);
                break;
            case 2:
                importClick(itemView, item, infor.item_id);
                break;

        }


    }

    private void importClick(View itemView, Set_Item item, int item_id) {
        switch (item_id) {
            case ImprotFactory.LOAN_STRAGE://提额攻略
                view.loan_strage_item(itemView, item);
                break;
            case ImprotFactory.LOAN_ONE_KEY_IPMORT://一键提额
                view.loan_one_key_item(itemView, item);
                break;


        }
    }

    private void cardClick(View itemView, Set_Item item, int item_id) {
        switch (item_id) {
            case CardDataFractory.ALL_BALANCE:
                view.allBalanceItemClick(item);
                break;
            case CardDataFractory.APPLY_PROGRESS:
                view.applyQueryItemClick(itemView, item);
                break;
            case CardDataFractory.HOT_APPLY:
                view.hotapplyItemClick(itemView, item);
                break;

        }


    }

    /**
     * hoem二级页面的条目被点击
     *
     * @param itemView
     * @param item
     * @param item_id
     */
    private void homeClick(View itemView, Set_Item item, int item_id) {

        switch (item_id) {
            case HomeDataFractory.BALANCE_CALL:
                view.showPhoneDialog(item);
                break;
            case HomeDataFractory.CREDIT:
                view.creditItemClick(itemView, item);
                break;
            case HomeDataFractory.MSG_CENTER:
                view.msgItemClick(itemView, item);
                break;
            case HomeDataFractory.UTILITY_TOLL:
                view.utilityItemClick(itemView, item);
                break;
        }
    }


    private List<Set_Item> list = new ArrayList<>();

    private RecyclerView.Adapter getHomeAdapter(Context context, final int activityID, final int item_id) {
        List<Set_Item> items = data.getDataByID(activityID, item_id);
        if (items != null) {
            list.addAll(items);
        }
        int itemId = data.getItemID(activityID, item_id);
        return adapter = new DefaultAdapter<Set_Item>(context, list, itemId, new DefaultAdapterViewLisenter<Set_Item>() {
            @Override
            public CustomHolder getBodyHolder(Context context, List<Set_Item> lists, int itemID) {
                return new CustomHolder<Set_Item>(context, lists, itemID) {
                    @Override
                    public void initView(int position, List<Set_Item> datas, Context context) {
                        super.initView(position, datas, context);
                        Set_Item item = datas.get(position);
                        view.initItemView(itemView, item, position);
                    }
                };
            }
        });

    }

    private Subscriber<Set_Item> refrushSubscriber;


    private Subscriber<Set_Item> getRefrushSubscriber() {
        refrushSubscriber = new Subscriber<Set_Item>() {
            @Override
            public void onStart() {
                super.onStart();
                list.clear();
            }

            @Override
            public void onCompleted() {
                view.loadSuccess();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                view.loadFail();
            }

            @Override
            public void onNext(Set_Item o) {
                list.add(o);

            }
        };
        return refrushSubscriber;
    }


}
