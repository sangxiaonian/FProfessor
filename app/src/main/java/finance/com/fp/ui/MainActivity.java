package finance.com.fp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import finance.com.fp.BasisActivity;
import finance.com.fp.BasisFragment;
import finance.com.fp.R;
import finance.com.fp.ui.fragment.FindFragment;
import finance.com.fp.ui.fragment.HomeFragment;
import finance.com.fp.ui.fragment.SetFragment;

public class MainActivity extends BasisActivity implements View.OnClickListener {


    /**
     * The {@link ViewPager} that will host the section contents.
     */

    private LinearLayout ll_home, ll_find, ll_set;
    private TextView tv_home, tv_find, tv_set;
    private ImageView img_home, img_find, img_set;
    private List<View> views;
    private FrameLayout vp;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setColor(this, getResources().getColor(R.color.colorPrimary));
        initView();
        initDatas();
        initListener();

    }

    private void initDatas() {
        views = new ArrayList<>();
        fragments = new ArrayList<>();
        views.add(tv_find);
        views.add(tv_home);
        views.add(tv_set);
        views.add(img_set);
        views.add(img_home);
        views.add(img_find);


    }

    private void initListener() {
        ll_find.setOnClickListener(this);
        ll_set.setOnClickListener(this);
        ll_home.setOnClickListener(this);
        ll_home.performClick();
    }

    public void initView() {
        tv_find = (TextView) findViewById(R.id.tv_find);
        tv_set = (TextView) findViewById(R.id.tv_set);
        tv_home = (TextView) findViewById(R.id.tv_home);

        img_find = (ImageView) findViewById(R.id.img_find);
        img_set = (ImageView) findViewById(R.id.img_set);
        img_home = (ImageView) findViewById(R.id.img_home);

        ll_home = (LinearLayout) findViewById(R.id.ll_home);
        ll_find = (LinearLayout) findViewById(R.id.ll_find);
        ll_set = (LinearLayout) findViewById(R.id.ll_set);
        vp = (FrameLayout) findViewById(R.id.container);

    }

    HomeFragment homeFragment;
    FindFragment findFragment;
    SetFragment setFragment;
    BasisFragment currentFragment;

    @Override
    public void onClick(View v) {
        FragmentManager fm = getSupportFragmentManager();
        // 开启Fragment事务

        FragmentTransaction transaction = fm.beginTransaction();
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }
        switch (v.getId()) {
            case R.id.ll_home:

                if (homeFragment == null) {

                    homeFragment = new HomeFragment();
                    transaction.add(R.id.container, homeFragment);
                } else {
                    transaction.show(homeFragment);
                }
                currentFragment = homeFragment;
                changeState(0);
                break;
            case R.id.ll_find:
                if (findFragment == null) {
                    findFragment = new FindFragment();
                    transaction.add(R.id.container, findFragment);
                } else {
                    transaction.show(findFragment);
                }
                currentFragment = findFragment;
                changeState(1);
                break;
            case R.id.ll_set:


                if (setFragment == null) {
                    setFragment = new SetFragment();
                    transaction.add(R.id.container, setFragment);

                } else {
                    transaction.show(setFragment);
                }
                currentFragment = setFragment;
                changeState(2);
                break;
        }
        transaction.commit();
    }

    private void changeState(int position) {
        switch (position) {
            case 0:
                changeState(img_home, tv_home);
                break;
            case 1:
                changeState(img_find, tv_find);
                break;
            case 2:
                changeState(img_set, tv_set);
                break;
        }
    }

    private void changeState(View img, View textView) {
        for (View v : views) {
            if (v == img || v == textView) {
                v.setEnabled(true);
            } else {
                v.setEnabled(false);
            }
        }
    }
}
