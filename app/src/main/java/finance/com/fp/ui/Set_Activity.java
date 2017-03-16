package finance.com.fp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.umeng.message.PushAgent;

import finance.com.fp.BasisActivity;
import finance.com.fp.R;
import finance.com.fp.mode.http.Config;
import finance.com.fp.utlis.Utils;

public class Set_Activity extends BasisActivity implements View.OnClickListener {

    private LinearLayout ll;
    private PushAgent mPushAgent;
    private ImageView img;

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
       findViewById(R.id.ll_set_msg).setOnClickListener(this);
        ll.setOnClickListener(this);
        img = (ImageView) findViewById(R.id.img_set_msg);
          mPushAgent = PushAgent.getInstance(this);
        boolean booleanSp = Utils.getBooleanSp(this, Config.isopenPush);
        if (booleanSp){
            img.setImageResource(R.mipmap.common_nav_btn_message_s);
        }else {
            img.setImageResource(R.mipmap.common_nav_btn_message_n);
        }
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
            case R.id.ll_set_msg:
                boolean booleanSp = Utils.getBooleanSp(this, Config.isopenPush);
                if (booleanSp){
                    img.setImageResource(R.mipmap.common_nav_btn_message_n);
                    Utils.close(mPushAgent,this);
                }else {
                    img.setImageResource(R.mipmap.common_nav_btn_message_s);
                    Utils.openPush(mPushAgent,this);
                }
        }
    }


    
}
