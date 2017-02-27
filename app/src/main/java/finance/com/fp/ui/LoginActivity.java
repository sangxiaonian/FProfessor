package finance.com.fp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import finance.com.fp.BasisActivity;
import finance.com.fp.R;
import finance.com.fp.presenter.RegisterPreComl;
import finance.com.fp.presenter.inter.RegisterInter;
import finance.com.fp.ui.inter.RegisterView;
import sang.com.xdialog.DialogFactory;
import sang.com.xdialog.XDialog;

public class LoginActivity extends BasisActivity implements RegisterView<String>, View.OnClickListener {

    private RadioGroup rg;
    private RegisterInter pre;
    private EditText et_user, et_password;
    private Button bt_login, bt_dynamic, bt_forget;
    private XDialog dialog, inforDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setColor(this, getResources().getColor(R.color.statucolor));
        initToolBar(getString(R.string.login));
        initView();
        initData();
    }

    @Override
    public void initView() {
        super.initView();
        rg = (RadioGroup) findViewById(R.id.rg);
        et_user = (EditText) findViewById(R.id.et_user);
        et_password = (EditText) findViewById(R.id.et_pasword);

        bt_login = (Button) findViewById(R.id.bt_login);
        bt_dynamic = (Button) findViewById(R.id.bt_dynamic);
        bt_forget = (Button) findViewById(R.id.bt_forget);

    }

    @Override
    public void initData() {
        super.initData();
        pre = new RegisterPreComl(this);

        bt_dynamic.setOnClickListener(this);
        bt_login.setOnClickListener(this);
        bt_forget.setOnClickListener(this);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                pre.onRgCheckChanged(group, checkedId);
            }
        });

        ((RadioButton) rg.getChildAt(0)).setChecked(true);
        dialog = DialogFactory.getInstance().creatDiaolg(this, DialogFactory.LOAD_DIALOG);
        inforDialog = DialogFactory.getInstance().creatDiaolg(this, DialogFactory.ALEART_DIALOG);
    }

    @Override
    public void showDynamic() {
        isNormal = true;
        bt_dynamic.setVisibility(View.VISIBLE);
        et_password.setText("");
        et_password.setHint(getString(R.string.input_dynamic));
        et_password.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
    }

    @Override
    public int getPhoneNotic() {
        return R.string.input_phone;
    }

    @Override
    public int getPasswordNotic() {
        if (isNormal) {

            return R.string.input_dynamic;
        } else {
            return R.string.input_password;
        }

    }

    @Override
    public String getPhone() {
        return et_user.getText().toString().trim();
    }

    @Override
    public void setPhone(String phone) {

    }

    private boolean isNormal;

    @Override
    public void showNormal() {
        isNormal = false;
        bt_dynamic.setVisibility(View.GONE);
        et_password.setText("");
        et_password.setHint(getString(R.string.input_password));
        et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    private boolean dynamic;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                dynamic = false;
                pre.jumpToNext(this, et_user, et_password, null);
                break;
            case R.id.bt_dynamic:
                dynamic = true;
                pre.getDynamic(getString(R.string.input_phone), et_user);
                break;
            case R.id.bt_forget:

                break;
        }
    }

    @Override
    public void onNextClick() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void showEtError(EditText et, int input_dynamic) {
        et.setError(getString(input_dynamic));
        et.setText("");
        et.requestFocus();
    }

    @Override
    public void upDynamic(String time, boolean b) {
        if (bt_dynamic.isEnabled() != b) {
            bt_dynamic.setEnabled(b);
        }
        bt_dynamic.setText(time);
    }

    @Override
    public void showDialog() {
        dialog.show();
    }

    @Override
    public void dissMissDialog() {
        dialog.dismiss();
    }

    @Override
    public void onCompleted() {
        dissMissDialog();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        dialog.dismiss();
        inforDialog.setDatas(getString(R.string.attention));
        inforDialog.setTitle(getString(R.string.net_error));
        inforDialog.showStyle(XDialog.ALEART_ONLY_ENTRY);
    }

    @Override
    public void onNext(String o) {
        switch (o) {
            case "0":
                inforDialog.setDatas(getString(R.string.login_error));
                inforDialog.setTitle(getString(R.string.login_fail));
                inforDialog.showStyle(XDialog.ALEART_ONLY_ENTRY);
                break;
            case "1":
                finish();
                pre.setSp(this, false);
                break;
        }
    }
}
