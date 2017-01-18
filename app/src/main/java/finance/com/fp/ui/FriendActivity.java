package finance.com.fp.ui;

import android.os.Bundle;

import finance.com.fp.BasisActivity;
import finance.com.fp.R;

public class FriendActivity extends BasisActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        initToolBar("朋友圈");
        setColor(this,getResources().getColor(R.color.text_home_more_text));
    }
}
