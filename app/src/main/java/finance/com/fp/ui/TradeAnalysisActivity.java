package finance.com.fp.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import finance.com.fp.BasisActivity;
import finance.com.fp.R;
import finance.com.fp.utlis.ToastUtil;

/**
 * 交易分析
 */
public class TradeAnalysisActivity extends BasisActivity implements View.OnClickListener {

    private RelativeLayout mcc,query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_analysis);
        setColor(this,getResources().getColor(R.color.statucolor));
        initToolBar("交易分析");
        mcc= (RelativeLayout) findViewById(R.id.mcc);
        query= (RelativeLayout) findViewById(R.id.query);
        mcc.setOnClickListener(this);
        query.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mcc:
                ToastUtil.showTextToast("MCC");
                break;
            case R.id.query:
                ToastUtil.showTextToast("银联查询");

                break;
        }
    }
}
