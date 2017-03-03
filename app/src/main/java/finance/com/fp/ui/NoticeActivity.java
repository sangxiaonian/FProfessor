package finance.com.fp.ui;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import finance.com.fp.BasisActivity;
import finance.com.fp.R;
import finance.com.fp.mode.http.Config;
import finance.com.fp.mode.bean.TranInfor;

public class NoticeActivity extends BasisActivity {

    private TextView title,time,content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        setColor(this,getResources().getColor(R.color.statucolor));
        initToolBar(getString(R.string.msg_detail));

        title= (TextView) findViewById(R.id.tv_title);
        time= (TextView) findViewById(R.id.tv_time);
        content= (TextView) findViewById(R.id.content);

        TranInfor infro = getIntent().getParcelableExtra(Config.infors);

        title.setText(infro.title);
        time.setText(infro.updatetime);
        content.setText(Html.fromHtml(infro.content));
    }
}
