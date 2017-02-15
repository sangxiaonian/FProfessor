package finance.com.fp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import finance.com.fp.BasisActivity;
import finance.com.fp.R;
import finance.com.fp.mode.bean.Config;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.bean.TranInfor;
import finance.com.fp.mode.datafractory.HomeDataFractory;
import finance.com.fp.mode.datafractory.ImprotFactory;
import finance.com.fp.presenter.ImportProComl;
import finance.com.fp.presenter.inter.ImportInter;
import finance.com.fp.ui.inter.ImportView;
import finance.com.fp.utlis.ToastUtil;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/5 17:39
 */
public class ImportActivity extends BasisActivity implements ImportView {
    private Toolbar toolbar;
    private TextView title;
    private RecyclerView recyclerView;

    private ImportInter pre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        setColor(this, getResources().getColor(R.color.statucolor));
        initView();

    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initListener();
    }

    public void initData() {
        pre = new ImportProComl(this);
        title.setText(getResources().getString(R.string.import_title));

        recyclerView.setAdapter(pre.getAdapter(this));
    }

    private void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
    public void onItemClick(Set_Item item, int position) {
        TranInfor tranInfor = new TranInfor();
        Intent intent = new Intent();
        tranInfor.activity_id = 2;

        tranInfor.title = item.title;
        Class c = null;
        switch (position) {
            case 0:
                tranInfor.item_id = ImprotFactory.LOAN_STRAGE;
                c = HomeSonActivity.class;
                break;
            case 1:
                c=TradeAnalysisActivity.class;
                tranInfor.item_id = HomeDataFractory.BALANCE_CALL;
                break;
            case 2:
//                c=HomeSonActivity.class;
                tranInfor.item_id = HomeDataFractory.CREDIT;
                break;


        }
        if (c == null) {
            ToastUtil.showTextToast("该功能尚未开放");
        } else {
            intent.putExtra(Config.infors, tranInfor);
            intent.setClass(this, c);
            startActivity(intent);
        }
    }

    @Override
    public void oneKeyImport() {
        TranInfor tranInfor = new TranInfor();
        Intent intent = new Intent(this,HomeSonActivity.class);
        tranInfor.activity_id = 2;
        tranInfor.title = getString(R.string.import_key);
        tranInfor.item_id = ImprotFactory.LOAN_ONE_KEY_IPMORT;
        intent.putExtra(Config.infors, tranInfor);
        startActivity(intent);
    }
}
