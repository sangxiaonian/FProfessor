package finance.com.fp.ui;

import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.orhanobut.logger.Logger;

import finance.com.fp.BasisActivity;
import finance.com.fp.R;
import finance.com.fp.mode.bean.Config;
import finance.com.fp.mode.bean.TranInfor;
import finance.com.fp.utlis.HorizontalProgress;

public class ShowDetailActivity extends BasisActivity {

    public WebView webView;
    HorizontalProgress progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        progress= (HorizontalProgress) findViewById(R.id.pb);
        progress.setProgress(50);

        TranInfor infor = getIntent().getParcelableExtra(Config.infors);
        setColor(this, getResources().getColor(R.color.statucolor));
        initToolBar(infor.title);
        webView = (WebView) findViewById(R.id.web);
        WebSettings mWebSettings = webView.getSettings();
      
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setDefaultTextEncodingName("GBK");
        mWebSettings.setLoadsImagesAutomatically(true);
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setDisplayZoomControls(false);
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        // 设置网页加载进度
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                 progress.setProgress(newProgress);

            }

        });




        if (infor.type == 0) {
            webView.loadDataWithBaseURL("", infor.content, "text/html", "utf-8", "");
        }else {
            Logger.i(infor.content);
//            webView.loadUrl(infor.content);
            String a = "http://www.icbc.com.cn/ICBC/%e5%b9%bf%e5%91%8a%e9%a1%b5%e9%9d%a2/%e7%bd%91%e7%ab%99%e5%ae%a3%e4%bc%a0%e5%b9%bf%e5%91%8a%e9%a1%b5%e9%9d%a2/%e7%bd%91%e7%ab%99%e5%b9%bf%e5%91%8a%e9%a1%b5%e9%9d%a2/2012/0326_%e6%96%b0%e7%89%88%e4%bf%a1%e7%94%a8%e5%8d%a1%e7%bd%91%e4%b8%8a%e5%8a%9e%e5%8d%a1%e5%ae%a3%e4%bc%a0/%e6%96%b0%e7%89%88%e4%bf%a1%e7%94%a8%e5%8d%a1%e7%bd%91%e4%b8%8a%e5%8a%9e%e5%8d%a1%e5%ae%a3%e4%bc%a0.htm";
            webView.loadUrl(a);

        }




    }


    @Override
    public void finish() {
        if (webView.canGoBack()){
            webView.goBack();
        }else {
            super.finish();
        }



    }
}
