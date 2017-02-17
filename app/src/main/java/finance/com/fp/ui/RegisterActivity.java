package finance.com.fp.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import finance.com.fp.BasisActivity;
import finance.com.fp.BasisFragment;
import finance.com.fp.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setColor(this, getResources().getColor(R.color.statucolor));
        initView();
    }

    @Override
    public void initView() {
        super.initView();
        fl = (FrameLayout) findViewById(R.id.container);
        phoneFragment = new RegisterPhoneFragment();
        currentFragment=phoneFragment;
        FragmentManager fm = getSupportFragmentManager();
        // 开启Fragment事务

        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.container, phoneFragment);
        transaction.commit();
    }

    @Override
    public void onBackClikc() {


        if (currentFragment instanceof RegisterPhoneFragment){
            finish();
        }else if (currentFragment instanceof RegisterPasswordFragment){
            changeFragment(0,false);
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
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }
        switch (position) {
            case 0:
                if (phoneFragment == null) {
                    phoneFragment = new RegisterPhoneFragment();
                    transaction.add(R.id.container, phoneFragment);
                } else {
                    transaction.show(phoneFragment);
                }
                currentFragment = phoneFragment;
                break;
            case 1:
                if (passwordFragment == null) {
                    passwordFragment = new RegisterPasswordFragment();
                    transaction.add(R.id.container, passwordFragment);
                } else {
                    transaction.show(passwordFragment);
                }
                currentFragment = passwordFragment;
                break;
            case 2:
                if (finishFragment == null) {
                    finishFragment = new RegisterFinishFragment();
                    transaction.add(R.id.container, finishFragment);
                } else {
                    transaction.show(finishFragment);
                }
                currentFragment = finishFragment;

                break;
        }

        transaction.commit();
    }
}
