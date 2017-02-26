package finance.com.fp.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import finance.com.fp.BasisActivity;
import finance.com.fp.R;
import finance.com.fp.mode.http.Config;
import finance.com.fp.mode.bean.TranInfor;

public class ImportDetailActivity extends BasisActivity implements View.OnClickListener {

    private TranInfor infor;
    private TextView tv_call, tv_call_warn, tv_msg, tv_msg_warn, tv_wei;
    private Button bt_phone, bt_msg, bt_online;
    private LinearLayout ll_onLine, ll_phone, ll_msg, ll_wei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_detail);
        setColor(this, getResources().getColor(R.color.statucolor));
        initView();
        initData();
    }


    @Override
    public void initView() {
        super.initView();
        infor = getIntent().getParcelableExtra(Config.infors);
        initToolBar(infor.title);
        tv_call = (TextView) findViewById(R.id.tv_call);
        tv_call_warn = (TextView) findViewById(R.id.tv_call_warn);
        tv_msg = (TextView) findViewById(R.id.tv_msg);
        tv_msg_warn = (TextView) findViewById(R.id.tv_msg_warn);
        tv_wei = (TextView) findViewById(R.id.tv_wei);
        bt_msg = (Button) findViewById(R.id.bt_msg);
        bt_online = (Button) findViewById(R.id.bt_online);
        bt_phone = (Button) findViewById(R.id.bt_call);
        ll_msg = (LinearLayout) findViewById(R.id.msg_import);
        ll_onLine = (LinearLayout) findViewById(R.id.line_import);
        ll_phone = (LinearLayout) findViewById(R.id.phone_import);
        ll_wei = (LinearLayout) findViewById(R.id.wei_import);

    }


    @Override
    public void initData() {
        super.initData();
        int a = 0, b = 0, c = 0, d = 0, e = 0, f = 0, g = 0;
        switch (infor.title) {
            case "招商银行":
                a = R.string.zhao_call;
                b = R.string.zhao_call_warn;
                f = R.string.zhao_phone;
                g= R.string.zhao_online;

                break;
            case "广发银行":
                a = R.string.guang_call;
                b = R.string.guang_call_warn;
                f = R.string.guang_phone;
                e = R.string.guang_wei;
                g= R.string.guang_online;

                break;
            case "交通银行":
                a = R.string.jiao_call;
                b = R.string.jiao_call_warn;
                f = R.string.jiao_phone;

                break;
            case "浦发银行":
                a = R.string.pu_call;
                f = R.string.pu_phone;
                g=R.string.pu_online;
                break;
            case "建设银行":
                a = R.string.jian_call;
                b = R.string.jian_call_warn;
                f = R.string.jian_phone;
                c = R.string.jian_msg;
                d = R.string.jian_msg_warn;
                g=R.string.jian_online;
                msg_bt = getString(R.string.jian_msg_phone);
                break;
            case "中信银行":
                a = R.string.xin_call;
                b = R.string.xin_call_warn;
                f = R.string.xin_phone;
                c = R.string.xin_msg;
                d = R.string.xin_msg_worn;
                e = R.string.xin_wei;
                g=R.string.xin_online;
                msg_bt = getString(R.string.xin_msg_phone);
                break;
            case "农业银行":
                g=R.string.nong_online;
                a = R.string.nong_call;
                f = R.string.nong_phone;
                break;
            case "平安银行":
                g=R.string.ping_online;
                a = R.string.ping_call;
                f = R.string.ping_phone;
                break;
            case "兴业银行":
                g=R.string.xing_online;
                a = R.string.xing_call;
                b = R.string.xing_call_warn;
                f = R.string.xing_phone;
                break;
            case "工商银行":
                g=R.string.gong_online;
                a = R.string.gong_call;
                b = R.string.gong_call_warn;
                f = R.string.gong_phone;
                break;
            case "民生银行":
                g=R.string.min_online;
                a = R.string.min_call;

                f = R.string.min_phone;
                break;
            case "中国银行":
                g=R.string.zhong_online;
                a = R.string.zhong_call;
                b = R.string.zhong_call_warn;
                f = R.string.zhong_phone;
                break;
            case "光大银行":
                g=R.string.guangd_online;
                a = R.string.guangd_call;
                f = R.string.guangd_phone;
                break;
            case "华夏银行":
                g=R.string.hua_online;
                a = R.string.hua_call;
                f = R.string.hua_phone;
                break;
            default:

                break;

        }

        getString(a, b, c, d, e, f, g);

        bt_phone.setOnClickListener(this);
        bt_msg.setOnClickListener(this);
        bt_online.setOnClickListener(this);

    }

    private String phone_bt, msg_bt,online_bt;

    private void getString(int a, int b, int c, int d, int e, int f, int g) {
        if (a != 0) {
            ll_phone.setVisibility(View.VISIBLE);

            phone_bt = getString(f);
            tv_call.setText(getString(a));
            if (b != 0) {
                tv_call_warn.setVisibility(View.VISIBLE);
                tv_call_warn.setText(getString(b));
            } else {
                tv_call_warn.setVisibility(View.GONE);
            }
            bt_phone.setText("拨打(" + phone_bt + ")");
        } else {
            ll_phone.setVisibility(View.GONE);
        }

        if (c != 0) {
            ll_msg.setVisibility(View.VISIBLE);
            tv_msg.setText(getString(c));
            if (d != 0) {
                tv_msg_warn.setVisibility(View.VISIBLE);
                tv_msg_warn.setText(getString(d));
            } else {
                tv_msg_warn.setVisibility(View.GONE);
            }

        } else {
            ll_msg.setVisibility(View.GONE);
        }

        if (e != 0) {
            ll_wei.setVisibility(View.VISIBLE);
            tv_wei.setText(getString(e));
        } else {
            ll_wei.setVisibility(View.GONE);
        }

        if (g!=0){
            ll_onLine.setVisibility(View.VISIBLE);
            online_bt=getString(g);
        }else {
            ll_onLine.setVisibility(View.GONE);

        }

    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.bt_call:
                intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phone_bt));
                startActivity(intent);
                break;
            case R.id.bt_msg:
                intent = new Intent("android.intent.action.SENDTO",
                        Uri.parse("smsto:" + msg_bt));
                intent.putExtra("sms_body", ""); //默认短信文字
                startActivity(intent);
                break;
            case R.id.bt_online:
                  intent = new Intent(this, ShowDetailActivity.class);
                TranInfor infor = new TranInfor();
                infor.title = this.infor.title ;
                infor.type=1;
                infor.content = online_bt;
                intent.putExtra(Config.infors, infor);
                startActivity(intent);
                break;
        }
    }
}

