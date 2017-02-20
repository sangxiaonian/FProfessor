package finance.com.fp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import em.sang.com.allrecycleview.adapter.DefaultAdapter;
import finance.com.fp.BasisActivity;
import finance.com.fp.R;
import finance.com.fp.mode.bean.Config;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.bean.TranInfor;
import finance.com.fp.mode.datafractory.CardDataFractory;
import finance.com.fp.presenter.CardActivityComl;
import finance.com.fp.presenter.inter.CardActivityPre;
import finance.com.fp.ui.inter.CardView;
import finance.com.fp.utlis.ToastUtil;

public class CardActivity extends BasisActivity implements CardView {


    private RecyclerView recyclerView;

    private DefaultAdapter<Set_Item> adapter;
    private CardActivityPre pre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        setColor(this, getResources().getColor(R.color.statucolor));
        initToolBar(getString(R.string.card_title));
        initView();
        initData();

    }

    public void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.rc);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
    }

    public void initData() {
        pre = new CardActivityComl(this);


        adapter = pre.getAdapter(this);
        recyclerView.setAdapter(adapter);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        pre.clearThread();
    }


    @Override
    public void onItemClick(int position, Set_Item item) {
        TranInfor tranInfor = new TranInfor();
        Intent intent = new Intent();
        tranInfor.activity_id = 1;
        tranInfor.item_id = position;
        tranInfor.title = item.title;
        Class c = null;
        switch (position) {
            case 0:
                c = Card_StrategyActivity.class;
                break;
            case 1:
                c = HomeSonActivity.class;
                break;
            case 2:
                c = PlannerActivity.class;
                break;
        }
        if (c == null) {
            ToastUtil.showTextToast(tranInfor.title);
        } else {
            intent.putExtra(Config.infors, tranInfor);
            intent.setClass(this, c);
            startActivity(intent);
        }
    }

    @Override
    public void onCardMoreClick(Set_Item item) {
        TranInfor tranInfor = new TranInfor();
        Intent intent = new Intent();
        tranInfor.activity_id = 1;
        tranInfor.item_id = CardDataFractory.ALL_BALANCE;
        tranInfor.title = item.title;
        intent.putExtra(Config.infors, tranInfor);
        intent.setClass(this, HomeSonActivity.class);
        startActivity(intent);
    }

    @Override
    public void onQueryProClick(Set_Item item) {
        TranInfor tranInfor = new TranInfor();
        Intent intent = new Intent();
        tranInfor.activity_id = 1;
        tranInfor.item_id = CardDataFractory.APPLY_PROGRESS;
        tranInfor.title = item.title;
        intent.putExtra(Config.infors, tranInfor);
        intent.setClass(this, HomeSonActivity.class);
        startActivity(intent);
    }
}
