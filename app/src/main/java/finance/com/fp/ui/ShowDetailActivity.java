package finance.com.fp.ui;

import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.sang.viewfractory.view.HorizontalProgress;

import finance.com.fp.BasisActivity;
import finance.com.fp.R;
import finance.com.fp.mode.bean.TranInfor;
import finance.com.fp.mode.http.Config;

public class ShowDetailActivity extends BasisActivity {

    public WebView webView;
    HorizontalProgress progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        progress = (HorizontalProgress) findViewById(R.id.pb);
        progress.setProgress(0);

        final TranInfor infor = getIntent().getParcelableExtra(Config.infors);
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

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);

                try {
                    Logger.i(failingUrl);
                    Uri uri = Uri.parse(infor.content);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        });

        // 设置网页加载进度
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progress.setProgress(newProgress);
            }

        });


        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);

            }
        });


        Logger.i(infor.content);
        if (infor.type == 0)

        {
            String a = "   <head>\n" +
                    "        <title> new document </title>\n" +
                    "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                    "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                    "        <style>\n" +
                    "                img { max-width: 100%;}\n" +
                    "        </style>\n" +
                    "        </head>";
            webView.loadDataWithBaseURL("", a + infor.content, "text/html", "utf-8", "");
        } else

        {
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


    public void initToolBar(String title) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                }
            });
            TextView tvtitle = (TextView) findViewById(R.id.title);
            if (tvtitle != null) {
                tvtitle.setText(title);
            }
        }
    }

}
