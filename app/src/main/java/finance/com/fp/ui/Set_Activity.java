package finance.com.fp.ui;

import android.os.Bundle;

import finance.com.fp.BasisActivity;
import finance.com.fp.R;

public class Set_Activity extends BasisActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        setColor(this,getResources().getColor(R.color.statucolor));
        initView();
    }

    public void initView() {
        initToolBar(getString(R.string.set_title));
    }
}
