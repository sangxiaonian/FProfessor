package finance.com.fp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.List;

import em.sang.com.allrecycleview.adapter.DefaultAdapter;
import em.sang.com.allrecycleview.holder.CustomHolder;
import em.sang.com.allrecycleview.inter.DefaultAdapterViewLisenter;
import finance.com.fp.BasisActivity;
import finance.com.fp.R;
import finance.com.fp.ui.holder.Loan_Strategy_Holder;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.datafractory.DataLoadLisetner;
import finance.com.fp.mode.datafractory.LoanDataFractory;
import finance.com.fp.utlis.RecycleViewDivider;

/**
 * 网贷攻略
 */
public class Loan_Strategy_Activity extends BasisActivity {

    private RecyclerView rc;
    private DefaultAdapter adapter;
    private List<Set_Item> lists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        setColor(this,getResources().getColor(R.color.statucolor));
        initToolBar(getString(R.string.loan_strategy));
        rc = (RecyclerView) findViewById(R.id.rc);

        initData();
    }

    @Override
    public void initData() {
        super.initData();
        lists= LoanDataFractory.getInstance().getLoanStragety();
        adapter = new DefaultAdapter<Set_Item>(this,lists,R.layout.item_loan_strategy,new DefaultAdapterViewLisenter<Set_Item>(){
            @Override
            public CustomHolder getBodyHolder(Context context, List<Set_Item> lists, int itemID) {
                return new CustomHolder<Set_Item>(context,lists,itemID){
                    @Override
                    public void initView(int position, List<Set_Item> datas, Context context) {
                        super.initView(position, datas, context);
                        TextView tv = (TextView) itemView.findViewById(R.id.tv_item_loan);
                        tv.setText(datas.get(position).title);
                    }
                };
            }
        }
        );

        adapter.addHead(new Loan_Strategy_Holder(this,R.layout.item_heard_loan_strategy,new Set_Item()));

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rc.setLayoutManager(manager);

        rc.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.HORIZONTAL,R.drawable.divider_line));
        rc.setAdapter(adapter);
        LoanDataFractory.getInstance().getLoanStrategyData(new DataLoadLisetner<Set_Item>() {
            @Override
            public void loadOver(List<Set_Item> lists) {
                Loan_Strategy_Activity.this.lists.addAll(lists);
                adapter.notifyDataSetChanged();
            }
        });

    }
}
