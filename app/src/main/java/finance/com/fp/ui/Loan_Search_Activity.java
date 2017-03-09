package finance.com.fp.ui;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import em.sang.com.allrecycleview.RefrushRecycleView;
import em.sang.com.allrecycleview.adapter.DefaultAdapter;
import em.sang.com.allrecycleview.adapter.RefrushAdapter;
import em.sang.com.allrecycleview.cutline.DividerGridItemDecoration;
import em.sang.com.allrecycleview.cutline.RecycleViewDivider;
import em.sang.com.allrecycleview.holder.CustomHolder;
import em.sang.com.allrecycleview.inter.DefaultAdapterViewLisenter;
import em.sang.com.allrecycleview.inter.RefrushListener;
import finance.com.fp.BasisActivity;
import finance.com.fp.R;
import finance.com.fp.mode.bean.LoanSearchBean;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.datafractory.DataLoadLisetner;
import finance.com.fp.mode.datafractory.HttpFactory;
import finance.com.fp.mode.datafractory.LoanDataFractory;
import finance.com.fp.mode.http.Config;
import finance.com.fp.ui.holder.HomeBodyHolder;
import rx.Observer;
import rx.Subscription;

import static finance.com.fp.mode.datafractory.HttpFactory.getLoanSearch;

public class Loan_Search_Activity extends BasisActivity implements Observer<LoanSearchBean> {

    private CheckBox cb;
    private ImageView img;
    private LoanDataFractory fractory;
    private List<LoanSearchBean> lists,tempLists;
    private int position;
    RecyclerView view;
    RefrushRecycleView rc;
    private List<Set_Item> searchs;
    DefaultAdapter adapter,reAdapter;
    private int page=0;
    private int id=0;
    private Subscription subscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan__search_);
        setColor(this, getResources().getColor(R.color.statucolor));
        position=getIntent().getIntExtra(Config.infors,0);
        id=position;
        initToolBar("网贷搜索");
        initView();
        initData();
    }

    @Override
    public void onTitlClick(View v) {
        super.onTitlClick(v);
        rc.smoothScrollToPosition(0);
    }

    @Override
    public void initView() {
        super.initView();
        lists=new ArrayList<>();
        tempLists=new ArrayList<>();
        cb = (CheckBox) findViewById(R.id.cb_search);
        img = (ImageView) findViewById(R.id.img_icon);
        rc= (RefrushRecycleView) findViewById(R.id.rc);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rc.setLayoutManager(manager);
        rc.addItemDecoration(new RecycleViewDivider(this, LinearLayout.VERTICAL));
        reAdapter = new RefrushAdapter<>(this, lists, R.layout.item_loan_item, new DefaultAdapterViewLisenter<LoanSearchBean>() {
            @Override
            public CustomHolder getBodyHolder(Context context, List<LoanSearchBean> lists, int itemID) {
                return new HomeBodyHolder(context, lists, itemID);
            }
        });
        rc.setHasBoom(true);
        rc.setAdapter(reAdapter);

        rc.setRefrushListener(new RefrushListener() {
            @Override
            public void onLoading() {

                 subscribe = getLoanSearch(id, page).subscribe(Loan_Search_Activity.this);
            }

            @Override
            public void onLoadDowning() {
                page++;

               subscribe= HttpFactory.getLoanSearch(id,page).subscribe(Loan_Search_Activity.this);
            }
        });
    }



    @Override
    public void initData() {
        super.initData();
        searchs = new ArrayList<>();
        fractory = LoanDataFractory.getInstance();
        view = (RecyclerView) View.inflate(this, R.layout.view_recycleview, null);
        int dimension = (int) getResources().getDimension(R.dimen.app_margin_ends);
        view.setPadding(dimension,0,dimension, (int) (dimension*2.0/3));

        view.setBackground(new ColorDrawable(getResources().getColor(R.color.white)));
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        view.addItemDecoration(new DividerGridItemDecoration(this,R.drawable.divider_line_rectangle));
        view.setLayoutManager(manager);
        adapter = new DefaultAdapter<Set_Item>(Loan_Search_Activity.this, searchs, R.layout.view_radiobutton, new DefaultAdapterViewLisenter<Set_Item>() {
            @Override
            public CustomHolder getBodyHolder(Context context, List<Set_Item> lists, int itemID) {
                return new CustomHolder<Set_Item>(context, lists, itemID) {
                    @Override
                    public void initView(final int position, final List<Set_Item> datas, Context context) {
                        super.initView(position, datas, context);
                        final Set_Item item = datas.get(position);
                        final RadioButton button = (RadioButton) this.itemView;
                        (button).setText(item.title);
                        button.setChecked(item.isCheck);

                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                button.setChecked(true);
                                for (int i = 0; i < datas.size(); i++) {
                                    if (position == i) {
                                        datas.get(i).isCheck = true;
                                    } else {
                                        datas.get(i).isCheck = false;
                                    }
                                }
                                updata(item);
                                page=0;
                                id=position;
                                rc.setLoading();

                            }
                        });

                    }
                };
            }
        });
        view.setAdapter(adapter);
        fractory.getAllSearch(new DataLoadLisetner<Set_Item>() {
            @Override
            public void loadOver(List<Set_Item> lists) {
                searchs.clear();
                searchs.addAll(lists);
                adapter.notifyDataSetChanged();
                cb.setText(lists.get(position).title);
            }
        },position);
        img.setEnabled(false);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    img.setEnabled(true);
                    showPopuWindow();
                } else {
                    img.setEnabled(false);
                    if (pop != null && pop.isShowing()) {
                        pop.dismiss();
                    }
                }
            }
        });

        rc.setLoading();

    }

    private void updata(Set_Item item) {
        adapter.notifyDataSetChanged();
        pop.dismiss();
        cb.setChecked(false);
        cb.setText(item.title);
    }

    private PopupWindow pop;

    private void showPopuWindow() {
        if (!isDestroyed()) {
            pop = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, false);
            pop.showAsDropDown(cb, 0, (int) getResources().getDimension(R.dimen.home_item_time_margin));
        }
    }

    @Override
    public void onCompleted() {
        Logger.i(tempLists.size()+"");
        if (tempLists.size()==0){
            page--;
            rc.LoadNoMore();
        }else {
            rc.loadSuccess();
            if (!rc.isLoadMore()){
                lists.clear();
            }
            lists.addAll(tempLists);
            reAdapter.notifyDataSetChanged();
            tempLists.clear();
            Logger.i( "加载成功");
        }



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
    public void onNext(LoanSearchBean loanSearchBean) {
        tempLists.add(loanSearchBean);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscribe.unsubscribe();
    }

    @Override
    public void finish() {
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
        }
        super.finish();
    }
}
