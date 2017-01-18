package finance.com.fp.ui;

import android.os.Bundle;

import finance.com.fp.BasisActivity;
import finance.com.fp.R;

/**
 * 账号信息
 */
public class IDActivity extends BasisActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id);
        setColor(this,getResources().getColor(R.color.white));
        initView();
    }
    public void initView() {
        initToolBar(getString(R.string.id_infor));
    }
}
