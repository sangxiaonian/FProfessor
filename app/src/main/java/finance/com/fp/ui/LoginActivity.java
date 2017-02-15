package finance.com.fp.ui;

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
import finance.com.fp.presenter.LoginPreComl;
import finance.com.fp.presenter.inter.LoginInter;
import finance.com.fp.ui.inter.LoginView;

public class LoginActivity extends BasisActivity implements LoginView,View.OnClickListener{

    private RadioGroup rg;
    private LoginInter pre;
    private EditText et_user,et_password;
    private Button bt_login,bt_dynamic,bt_forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setColor(this,getResources().getColor(R.color.statucolor));
        initToolBar(getString(R.string.login));
        initView();
        initData();
    }

    @Override
    public void initView() {
        super.initView();
        rg= (RadioGroup) findViewById(R.id.rg);
        et_user= (EditText) findViewById(R.id.et_user);
        et_password= (EditText) findViewById(R.id.et_pasword);
        bt_login= (Button) findViewById(R.id.bt_login);
        bt_dynamic= (Button) findViewById(R.id.bt_dynamic);
        bt_forget= (Button) findViewById(R.id.bt_forget);

    }

    @Override
    public void initData() {
        super.initData();
        pre=  new LoginPreComl(this);

        bt_dynamic.setOnClickListener(this);
        bt_login.setOnClickListener(this);
        bt_forget.setOnClickListener(this);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                pre.onRgCheckChanged(group,checkedId);
            }
        });
        ((RadioButton)rg.getChildAt(0)).setChecked(true);
    }

    @Override
    public void showDynamic() {
        bt_dynamic.setVisibility(View.VISIBLE);
        et_password.setText("");
        et_password.setHint(getString(R.string.input_dynamic));
        et_password.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
    }

    @Override
    public void showNormal() {
        bt_dynamic.setVisibility(View.GONE);
        et_password.setText("");
        et_password.setHint(getString(R.string.input_password));
        et_password.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    @Override
    public void onClick(View v) {

    }
}
