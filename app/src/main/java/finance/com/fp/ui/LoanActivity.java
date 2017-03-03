package finance.com.fp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.List;

import finance.com.fp.BasisActivity;
import finance.com.fp.R;
import finance.com.fp.mode.http.Config;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.bean.TranInfor;
import finance.com.fp.presenter.LoanProComl;
import finance.com.fp.presenter.inter.LoanInter;
import finance.com.fp.ui.holder.CardNotifiHolder;
import finance.com.fp.ui.holder.HomeCarouselHolder;
import finance.com.fp.ui.inter.LoanView;
import finance.com.fp.utlis.ToastUtil;

/**
 * 网贷
 */
public class LoanActivity extends BasisActivity implements LoanView {

    private Toolbar toolbar;
    private TextView title;
    private RecyclerView recyclerView;
    private HomeCarouselHolder carouselHolder;
    private CardNotifiHolder notifiHolder;

    private List<Set_Item> lists;


    private LoanInter pre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        setColor(this, getResources().getColor(R.color.statucolor));
        initView();
        initToolBar(getString(R.string.loan_title));

    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void initData() {
        pre = new LoanProComl(this);
        recyclerView.setAdapter(pre.getAdapter(this));

    }


    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) findViewById(R.id.title);
        recyclerView = (RecyclerView) findViewById(R.id.rc);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearThread();
    }

    private void clearThread() {
        pre.stopCarousel();

    }


    @Override
    public void onItemClick(int position, Object item) {
        Class c = null;
        if (position == 0) {
            c = Loan_Strategy_Activity.class;
        } else if (position == 1) {
            c = Loan_Search_Activity.class;
        }
        if (c!=null) {
            startActivity(new Intent(LoanActivity.this, c));
        }else {
            ToastUtil.showTextToast("该功能尚未开放");
        }
    }

    @Override
    public void onListItemClick(int position, Set_Item item) {
        TranInfor tranInfor = new TranInfor();
        Intent intent = new Intent();
        tranInfor.activity_id = 2;

        tranInfor.title = item.title;
        Class c = null;
        switch (position) {
            case 0://提额攻略
//                tranInfor.item_id = LoanDataFractory.LOAN_STRAGE;

                break;
            case 1://交易分析
//               c=HomeSonActivity.class;
//                tranInfor.item_id = LoanDataFractory.BALANCE_CALL;
                break;
            case 2://精养卡分析
//                c=HomeSonActivity.class;
//                tranInfor.item_id = HomeDataFractory.CREDIT;
                break;


        }
        if (c==null){
            ToastUtil.showTextToast("该功能尚未开放");
        }else {
            intent.putExtra(Config.infors, tranInfor);
            intent.setClass(this, c);
            startActivity(intent);
        }
    }

    @Override
    public void grideLoanClick(int position, Set_Item item) {
        Intent intent = new Intent(this,Loan_Search_Activity.class);
        if (position==9){
            position=0;
        }else {
            position++;
        }
        intent.putExtra(Config.infors,position);
        startActivity(intent);

    }


}
