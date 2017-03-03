package finance.com.fp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;

import finance.com.fp.BasisActivity;
import finance.com.fp.R;
import finance.com.fp.mode.bean.LoanSearchBean;
import finance.com.fp.mode.bean.TranInfor;
import finance.com.fp.mode.http.Config;

/**
 * 贷款详情
 */
public class LoanConditionActivity extends BasisActivity implements View.OnClickListener{
    LoanSearchBean bean;
    private LinearLayout ll_con,ll_material,ll_notice;
    private RelativeLayout rl_sqgl;
    private TextView tv_con,tv_material,tv_notice,tv_money,tv_tiem,tv_title,tv_describe;
    private Button bt_apply;
    private ImageButton bt_strategy;
    private ImageView img_item_loan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_condition);
        setColor(this,getResources().getColor(R.color.statucolor));
         bean = getIntent().getParcelableExtra(Config.infors);
        initToolBar(bean.getTitle());
        initView();
        initData();
    }

    @Override
    public void initView() {
        ll_con= (LinearLayout) findViewById(R.id.ll_con);
        ll_material= (LinearLayout) findViewById(R.id.ll_material);
        ll_notice= (LinearLayout) findViewById(R.id.ll_notice);
        tv_con= (TextView) findViewById(R.id.tv_con);
        tv_material= (TextView) findViewById(R.id.tv_material);
        tv_notice= (TextView) findViewById(R.id.tv_notice);
        tv_money= (TextView) findViewById(R.id.tv_money);
        tv_tiem= (TextView) findViewById(R.id.tv_time);
        tv_title= (TextView) findViewById(R.id.tv_title);
        tv_describe= (TextView) findViewById(R.id.tv_describe);
        bt_apply= (Button) findViewById(R.id.bt_apply);
        bt_strategy= (ImageButton) findViewById(R.id.bt_strategy);
        img_item_loan= (ImageView) findViewById(R.id.img_item_loan);
        rl_sqgl= (RelativeLayout) findViewById(R.id.rl_sqgl);
    }

    @Override
    public void initData() {
        setViewText(ll_con,tv_con,bean.getTiaojian());
        setViewText(ll_material,tv_material,bean.getSuoxu());
        setViewText(ll_notice,tv_notice,bean.getZhushi());
        Glide.with(this).load(bean.getThumb())
                .placeholder(R.mipmap.loading)
                .error(R.mipmap.load_fail)
                .crossFade().into(img_item_loan);
        tv_tiem.setText(bean.getFenqi());
        tv_money.setText(bean.getMoney());
        tv_title.setText(bean.getTitle());
        tv_describe.setText(bean.getDescription());
        bt_apply.setOnClickListener(this);
        bt_strategy.setOnClickListener(this);
        rl_sqgl.setOnClickListener(this);


    }

    private void setViewText(LinearLayout ll_con, TextView tv_con, String tiaojian) {
        if (TextUtils.isEmpty(tiaojian)){
            ll_con.setVisibility(View.GONE);
        }else {
            ll_con.setVisibility(View.VISIBLE);
            Logger.i(tiaojian);
            tv_con.setText(Html.fromHtml(tiaojian));
        }
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, ShowDetailActivity.class);
        TranInfor infor = new TranInfor();
        infor.title = bean.getTitle();

        switch (v.getId()){
            case R.id.bt_apply:
                infor.type=1;
                infor.content = bean.getLjsq_url();
                break;
            case R.id.bt_strategy:
            case R.id.rl_sqgl:

                infor.type=0;
                infor.content = bean.getSq_url();
                break;
        }
        intent.putExtra(Config.infors, infor);
        startActivity(intent);
    }
}
