package finance.com.fp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import em.sang.com.allrecycleview.RefrushRecycleView;
import em.sang.com.allrecycleview.adapter.DefaultAdapter;
import em.sang.com.allrecycleview.cutline.RecycleViewDivider;
import em.sang.com.allrecycleview.holder.CustomHolder;
import em.sang.com.allrecycleview.inter.DefaultAdapterViewLisenter;
import em.sang.com.allrecycleview.inter.RefrushListener;
import finance.com.fp.BasisActivity;
import finance.com.fp.R;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.bean.TranInfor;
import finance.com.fp.mode.datafractory.HttpFactory;
import finance.com.fp.mode.http.Config;
import finance.com.fp.utlis.ToastUtil;
import rx.Observer;
import rx.Subscription;

/**
 * 办卡攻略
 */
public class Card_StrategyActivity extends BasisActivity implements RadioGroup.OnCheckedChangeListener {


    private RefrushRecycleView rc;
    private RadioGroup rg;
    private List<Set_Item> set_items, newLists, howLists, godLists;
    DefaultAdapter<Set_Item> adapter;
    private TranInfor tranInfor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card__strategy);
        setColor(this, getResources().getColor(R.color.statucolor));
        tranInfor = getIntent().getParcelableExtra(Config.infors);
        initToolBar(tranInfor.title);
        initView();

    }

    @Override
    public void initView() {
        super.initView();
        rc = (RefrushRecycleView) findViewById(R.id.rc);
        rc.setHasBoom(true);
        rg = (RadioGroup) findViewById(R.id.rg_card);
        rg.setOnCheckedChangeListener(this);
        set_items = new ArrayList<>();
        newLists = new ArrayList<>();
        howLists = new ArrayList<>();
        godLists = new ArrayList<>();

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rc.setLayoutManager(manager);
        rc.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
        rc.setHasBoom(true);
        rc.setRefrushListener(new RefrushListener() {
            @Override
            public void onLoading() {
                page=0;
                getData();
            }

            @Override
            public void onLoadDowning() {
                page++;
                getData();

            }
        });
        rc.setAdapter(getAdapter());
        ((RadioButton) rg.getChildAt(0)).setChecked(true);


    }



    private DefaultAdapter<Set_Item> getAdapter() {

        adapter = new DefaultAdapter<>(this, set_items, R.layout.item_card_strategy, new DefaultAdapterViewLisenter<Set_Item>() {
            @Override
            public CustomHolder getBodyHolder(Context context, List<Set_Item> lists, int itemID) {
                return new CustomHolder<Set_Item>(context, lists, itemID) {
                    @Override
                    public void initView(int position, List<Set_Item> datas, final Context context) {
                        super.initView(position, datas, context);
                        TextView tv = (TextView) itemView.findViewById(R.id.tv);
                        final Set_Item item = datas.get(position);
                        tv.setText(item.title);
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Card_StrategyActivity.this, ShowDetailActivity.class);
                                TranInfor infor = new TranInfor();
                                infor.title = item.title;
                                infor.content = item.content;
                                infor.describe = item.describe;
                                intent.putExtra(Config.infors, infor);
                                startActivity(intent);
                            }
                        });
                    }
                };
            }
        });
        return adapter;
    }

    int id = 1;
    int page;

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        set_items.clear();
        switch (checkedId) {
            case R.id.rb_primer:
                id = 1;
                break;
            case R.id.rb_how:
                id = 2;
                break;
            case R.id.rb_god:
                id = 3;
                break;
        }
        rc.setLoading();


    }
    Subscription subscribe;
    private void getData() {
        if (subscribe!=null&&!subscribe.isUnsubscribed()){
            subscribe.unsubscribe();
        }
        subscribe = HttpFactory.getCardStrategy(id, page)
                .subscribe(observer);
    }

    private Observer observer = new Observer<Set_Item>() {
        @Override
        public void onCompleted() {
            rc.loadSuccess();
            List<Set_Item> tempList;
            tempList=getLsit();
            if (tempList.size()==0){
                page--;
                ToastUtil.showTextToast(getString(R.string.no_more));
            }
            if (!rc.isLoadMore()){
                set_items.clear();
            }
            set_items.addAll(tempList);
            tempList.clear();
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            rc.loadFail();
            if (rc.isLoadMore()){
                page--;
            }
        }

        @Override
        public void onNext(Set_Item set_item) {
            getLsit().add(set_item);
        }



    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscribe.unsubscribe();
    }

    private List<Set_Item> getLsit() {
        List<Set_Item> list;
        switch (id) {
            case 1:
                list=newLists;
                break;
            case 2:
                list=howLists;

                break;
            case 3:
                list=godLists;
                break;
            default:
                list=set_items;

        }

        return list;
    }
}
