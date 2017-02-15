package finance.com.fp.ui;

import android.os.Bundle;
import android.widget.FrameLayout;

import finance.com.fp.BasisActivity;
import finance.com.fp.R;
import finance.com.fp.ui.inter.FragmentListener;

public class RegisterActivity extends BasisActivity implements FragmentListener{

    private FrameLayout fl;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setColor(this,getResources().getColor(R.color.statucolor));
        initView();
    }

    @Override
    public void initView() {
        super.initView();
        fl= (FrameLayout) findViewById(R.id.fl);

    }
    @Override
    public void onBackClikc() {

    }

    @Override
    public void onNextClick() {

    }
}
