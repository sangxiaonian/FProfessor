package finance.com.fp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import finance.com.fp.BasisActivity;
import finance.com.fp.R;
import finance.com.fp.mode.http.Config;
import finance.com.fp.utlis.Utils;

public class Set_Activity extends BasisActivity implements View.OnClickListener {

    private LinearLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        setColor(this,getResources().getColor(R.color.statucolor));
        initView();
    }

    public void initView() {
        initToolBar(getString(R.string.set_title));
        ll= (LinearLayout) findViewById(R.id.ll_set_changeps);
        ll.setOnClickListener(this);
    }

    public void login_out(View view){
        Utils.login_out(this);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_set_changeps:
                finish();
                Intent intent = new Intent(this, RegisterActivity.class);
                intent.putExtra(Config.login_name,Utils.getSp(this,Config.login_name));
                startActivity(intent);
                break;
        }
    }
}
