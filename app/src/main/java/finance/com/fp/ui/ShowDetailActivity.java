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
import finance.com.fp.mode.http.Config;
import finance.com.fp.mode.bean.TranInfor;
import finance.com.fp.utlis.HorizontalProgress;

public class ShowDetailActivity extends BasisActivity {

    public WebView webView;
    HorizontalProgress progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        progress = (HorizontalProgress) findViewById(R.id.pb);
        progress.setProgress(0);

        TranInfor infor = getIntent().getParcelableExtra(Config.infors);
        setColor(this, getResources().getColor(R.color.statucolor));
        initToolBar(infor.title);
        webView = (WebView) findViewById(R.id.web);
        WebSettings mWebSettings = webView.getSettings();

        mWebSettings.setLoadWithOverviewMode(true);


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


        Logger.i(infor.content);
        if (infor.type == 0) {
            webView.loadDataWithBaseURL("", infor.content, "text/html", "utf-8", "");
        } else {
            mWebSettings.setDisplayZoomControls(false);
            mWebSettings.setUseWideViewPort(true);
            mWebSettings.setLoadsImagesAutomatically(true);
            mWebSettings.setDomStorageEnabled(true);
            mWebSettings.setJavaScriptEnabled(true);
            mWebSettings.setBuiltInZoomControls(true);
            mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            webView.loadUrl(infor.content);


        }


    }


    @Override
    public void finish() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.finish();
        }


    }
}
