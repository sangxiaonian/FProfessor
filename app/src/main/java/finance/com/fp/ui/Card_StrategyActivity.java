package finance.com.fp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import em.sang.com.allrecycleview.adapter.DefaultAdapter;
import em.sang.com.allrecycleview.holder.CustomHolder;
import em.sang.com.allrecycleview.inter.DefaultAdapterViewLisenter;
import finance.com.fp.BasisActivity;
import finance.com.fp.R;
import finance.com.fp.mode.bean.Config;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.utlis.RecycleViewDivider;

/**
 * 办卡攻略
 */
public class Card_StrategyActivity extends BasisActivity implements RadioGroup.OnCheckedChangeListener {


    private RecyclerView rc;
    private RadioGroup rg;
    private List<Set_Item> set_items;
    DefaultAdapter<Set_Item> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card__strategy);
        setColor(this, getResources().getColor(R.color.statucolor));
        tranInfor=getIntent().getParcelableExtra(Config.infors);
        initToolBar(tranInfor.title);
        initView();
    }

    @Override
    public void initView() {
        super.initView();
        rc = (RecyclerView) findViewById(R.id.rc);
        rg = (RadioGroup) findViewById(R.id.rg_card);
        rg.setOnCheckedChangeListener(this);
        set_items=new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rc.setLayoutManager(manager);
        rc.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.VERTICAL));
        rc.setAdapter(getAdapter());
        ((RadioButton)rg.getChildAt(0)).setChecked(true);

    }

    private DefaultAdapter<Set_Item> getAdapter() {

        adapter = new DefaultAdapter<>(this,set_items, R.layout.item_card_strategy,new DefaultAdapterViewLisenter<Set_Item>(){
            @Override
            public CustomHolder getBodyHolder(Context context, List<Set_Item> lists, int itemID) {
                return new CustomHolder<Set_Item>(context,lists,itemID){
                    @Override
                    public void initView(int position, List<Set_Item> datas, Context context) {
                        super.initView(position, datas, context);
                        TextView tv = (TextView) itemView.findViewById(R.id.tv);
                        tv.setText(datas.get(position).title);
                    }
                };
            }
        });
        return adapter;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        set_items.clear();
        String title = null;
        switch (checkedId) {
            case R.id.rb_primer:
                title="新手入门";
                break;
            case R.id.rb_how:
                title="如何选卡";
                break;
            case R.id.rb_god:
                title="卡神之路";
                break;
        }

        for (int i = 0; i < 15; i++) {
            Set_Item item = new Set_Item();
            item.title=title+i;
            set_items.add(item);
        }
        adapter.notifyDataSetChanged();


    }
}
