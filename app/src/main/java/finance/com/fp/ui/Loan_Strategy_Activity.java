package finance.com.fp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import em.sang.com.allrecycleview.RefrushRecycleView;
import em.sang.com.allrecycleview.adapter.RefrushAdapter;
import em.sang.com.allrecycleview.holder.CustomHolder;
import em.sang.com.allrecycleview.inter.DefaultAdapterViewLisenter;
import em.sang.com.allrecycleview.inter.RefrushListener;
import finance.com.fp.BasisActivity;
import finance.com.fp.R;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.bean.TranInfor;
import finance.com.fp.mode.datafractory.LoanDataFractory;
import finance.com.fp.mode.http.Config;
import finance.com.fp.ui.holder.Loan_Strategy_Holder;
import finance.com.fp.utlis.RecycleViewDivider;
import finance.com.fp.utlis.ToastUtil;
import rx.Observer;
import rx.Subscription;

/**
 * 网贷攻略
 */
public class Loan_Strategy_Activity extends BasisActivity implements Observer<Set_Item>{

    private RefrushRecycleView rc;
    private RefrushAdapter adapter;
    private List<Set_Item> lists;
    private int page;
    Subscription subscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refush);
        setColor(this,getResources().getColor(R.color.statucolor));
        initToolBar(getString(R.string.loan_strategy));
        rc = (RefrushRecycleView) findViewById(R.id.rc);
        rc.setHasBoom(true);
        initData();
    }

    @Override
    public void initData() {
        super.initData();
        lists= new ArrayList<>();
        tempList=new ArrayList<>();
        adapter = new RefrushAdapter(this,lists,R.layout.item_loan_strategy,new DefaultAdapterViewLisenter<Set_Item>(){
            @Override
            public CustomHolder getBodyHolder(Context context, List<Set_Item> lists, int itemID) {
                return new CustomHolder<Set_Item>(context,lists,itemID){
                    @Override
                    public void initView(int position, List<Set_Item> datas, Context context) {
                        super.initView(position, datas, context);
                        TextView tv = (TextView) itemView.findViewById(R.id.tv_item);
                        final Set_Item item = datas.get(position);
                        tv.setText(item.title);
                        itemView.findViewById(R.id.more).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Loan_Strategy_Activity.this, ShowDetailActivity.class);
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
        }
        );

        adapter.addHead(new Loan_Strategy_Holder(this,R.layout.item_heard_loan_strategy,new Set_Item()));

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rc.setLayoutManager(manager);

        rc.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.HORIZONTAL,R.drawable.divider_line));
        adapter.setRefrushPosition(1);

        rc.setAdapter(adapter);
        rc.setRefrushListener(new RefrushListener() {
            @Override
            public void onLoading() {
                page=0;
                  subscribe = LoanDataFractory.getInstance().getLoanStragety(page).subscribe(Loan_Strategy_Activity.this);
            }

            @Override
            public void onLoadDowning() {
                page++;
                subscribe = LoanDataFractory.getInstance().getLoanStragety(page).subscribe(Loan_Strategy_Activity.this);
            }
        });

        rc.setLoading();



    }

    private List<Set_Item> tempList;


    @Override
    public void onCompleted() {
        rc.loadSuccess();
        if (tempList.size()==0){
            page--;
            ToastUtil.showTextToast(getString(R.string.no_more));
        }
        if (!rc.isLoadMore()){
            lists.clear();
        }
        lists.addAll(tempList);
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
        tempList.add(set_item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscribe.unsubscribe();
    }

}
