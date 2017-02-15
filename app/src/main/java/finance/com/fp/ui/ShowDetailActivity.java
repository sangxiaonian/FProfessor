package finance.com.fp.ui;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.orhanobut.logger.Logger;

import finance.com.fp.BasisActivity;
import finance.com.fp.R;
import finance.com.fp.mode.bean.Config;
import finance.com.fp.mode.bean.TranInfor;

public class ShowDetailActivity extends BasisActivity {

    public WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        TranInfor infor = getIntent().getParcelableExtra(Config.infors);
        setColor(this, getResources().getColor(R.color.statucolor));
        initToolBar(infor.title);
        webView = (WebView) findViewById(R.id.web);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setDefaultFontSize(18);
        if (infor.type == 0) {
            webView.loadDataWithBaseURL("", infor.content, "text/html", "utf-8", "");
        }else {
            webView.loadUrl(infor.content);
        }
        Logger.i(infor.toString());
    }
}
