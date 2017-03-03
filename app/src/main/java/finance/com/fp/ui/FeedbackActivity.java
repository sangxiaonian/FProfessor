package finance.com.fp.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import finance.com.fp.BasisActivity;
import finance.com.fp.R;
import finance.com.fp.mode.http.Config;
import finance.com.fp.mode.http.HttpClient;
import finance.com.fp.utlis.Utils;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sang.com.xdialog.DialogFactory;
import sang.com.xdialog.XDialog;
import sang.com.xdialog.inter.OnEntryClickListener;

/**
 * 意见反馈
 */
public class FeedbackActivity extends BasisActivity implements rx.Observer<String> {

    private EditText et;
    private TextView acount,tv_phone;
    private XDialog dialog,infDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        setColor(this,getResources().getColor(R.color.statucolor));
        initView();
        dialog= DialogFactory.getInstance().creatDiaolg(this,DialogFactory.LOAD_DIALOG);
        infDialog= DialogFactory.getInstance().creatDiaolg(this,DialogFactory.ALEART_DIALOG);

    }


    @Override
    protected void onResume() {
        super.onResume();
        initListener();
    }
    public void initView() {
        initToolBar(getString(R.string.feedback));
        et= (EditText) findViewById(R.id.et_feed);
        acount= (TextView) findViewById(R.id.tv_feed_acount);
        tv_phone= (TextView) findViewById(R.id.tv_phone);
    }

    private void initListener(){
        if (Utils.isLogion(this)){
            tv_phone.setText(Utils.getSp(this,Config.login_name));
        }

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                acount.setText(et.getText().toString().trim().length()+"/100");
            }
        });
    }

    public void submit(View view){
        if (Utils.isLogion(this)){
            String trim = et.getText().toString().trim();
            if (!TextUtils.isEmpty(trim)) {
                dialog.show();
                HttpClient.getClient(Config.base_url).submit( trim,Utils.getSp(this, Config.login_name)).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(this);
            }else {
                infDialog.setTitle(getString(R.string.attention));
                infDialog.setDatas("内容不能为空");
                infDialog.showStyle(XDialog.ALEART_ONLY_ENTRY);
            }

        }else {
            infDialog.setTitle("提交失败");
            infDialog.setDatas("目前尚未登陆,登陆后方可操作");
            infDialog.showStyle(XDialog.ALEART_ONLY_ENTRY);
        }
    }



    @Override
    public void onCompleted() {
        dialog.dismiss();
        infDialog.setTitle("反馈成功");
        infDialog.setDatas("融教授感谢您的宝贵意见,我们将认真修正");
        infDialog.setOnClickListener(new OnEntryClickListener() {
            @Override
            public void onClick(Dialog dialog, int which, Object data) {
                dialog.dismiss();
                finish();
            }
        });
        infDialog.showStyle(XDialog.ALEART_ONLY_ENTRY);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onNext(String s) {

    }


}
