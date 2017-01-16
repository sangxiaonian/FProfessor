package finance.com.fp.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import finance.com.fp.BasisActivity;
import finance.com.fp.R;

/**
 * 意见反馈
 */
public class FeedbackActivity extends BasisActivity {

    private EditText et;
    private TextView acount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        setColor(this,getResources().getColor(R.color.white));
        initView();

    }
    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onResume() {
        super.onResume();
        initListener();
    }
    private void initView() {
        initToolBar(getString(R.string.feedback));
        et= (EditText) findViewById(R.id.et_feed);
        acount= (TextView) findViewById(R.id.tv_feed_acount);
    }

    private void initListener(){
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                acount.setText(et.getText().toString().trim().length()+"/100");
            }
        });
    }
    
}
