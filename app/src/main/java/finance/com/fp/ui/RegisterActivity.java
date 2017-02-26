package finance.com.fp.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.widget.FrameLayout;

import finance.com.fp.BasisActivity;
import finance.com.fp.BasisFragment;
import finance.com.fp.R;
import finance.com.fp.mode.http.Config;
import finance.com.fp.ui.fragment.RegisterFinishFragment;
import finance.com.fp.ui.fragment.RegisterPasswordFragment;
import finance.com.fp.ui.fragment.RegisterPhoneFragment;
import finance.com.fp.ui.inter.FragmentListener;

public class RegisterActivity extends BasisActivity implements FragmentListener {

    private FrameLayout fl;
    private int position;
    private RegisterPhoneFragment phoneFragment;
    private RegisterPasswordFragment passwordFragment;
    private RegisterFinishFragment finishFragment;
    private BasisFragment currentFragment;
    private String phone;
    private boolean isRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setColor(this, getResources().getColor(R.color.statucolor));
        phone=getIntent().getStringExtra(Config.login_name);
        initView();
    }

    @Override
    public void initView() {
        super.initView();
        fl = (FrameLayout) findViewById(R.id.container);
        phoneFragment = new RegisterPhoneFragment();
        currentFragment=phoneFragment;
        FragmentManager fm = getSupportFragmentManager();

        //此处来判断是否是修改密码,如果从其他页面传递过来的已经有手机号码了,说明此处是修改密码
        if (!TextUtils.isEmpty(phone)){
            if (passwordFragment == null) {
                passwordFragment = new RegisterPasswordFragment();
            }
            isRegister=false;
            currentFragment=passwordFragment;
        }else {
            isRegister=true;
        }
        // 开启Fragment事务

        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container, currentFragment);
        transaction.commit();
    }

    @Override
    public void onBackClikc() {
        if (currentFragment instanceof RegisterPhoneFragment){
            finish();
        }else if (currentFragment instanceof RegisterPasswordFragment){
            if (isRegister){
                changeFragment(0,false);
            }else{
                finish();
            }
        }else if (currentFragment instanceof RegisterFinishFragment){
            finish();
        }
    }

    @Override
    public void onNextClick() {
        if (currentFragment instanceof RegisterPhoneFragment){
            changeFragment(1, true);
        }else if (currentFragment instanceof RegisterPasswordFragment){
            changeFragment(2, true);
        }else if (currentFragment instanceof RegisterFinishFragment){
            finish();
        }

    }



    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public boolean isRegister() {
        return isRegister;
    }

    @Override
    public void setPhone(String phone) {
        this.phone=phone;
    }

    private void changeFragment(int position, boolean b) {
        FragmentManager fm = getSupportFragmentManager();
        // 开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();
        if (b) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out);
        }else {
            transaction.setCustomAnimations(
                    R.anim.slide_left_in,
                    R.anim.slide_right_out);
        }

        switch (position) {
            case 0:
                if (phoneFragment == null) {
                    phoneFragment = new RegisterPhoneFragment();

                }
                currentFragment = phoneFragment;
                break;
            case 1:
                if (passwordFragment == null) {
                    passwordFragment = new RegisterPasswordFragment();

                }
                currentFragment = passwordFragment;
                break;
            case 2:
                if (finishFragment == null) {
                    finishFragment = new RegisterFinishFragment();

                }
                currentFragment = finishFragment;

                break;
        }

        transaction.replace(R.id.container, currentFragment, "LoginFragment")
                .addToBackStack(null).commit();

    }
}
