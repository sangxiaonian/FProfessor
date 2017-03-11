package finance.com.fp.ui;

import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sang.viewfractory.view.HorizontalProgress;

import finance.com.fp.BasisActivity;
import finance.com.fp.R;
import finance.com.fp.mode.bean.FinanceBean;
import finance.com.fp.mode.bean.HttpBean;
import finance.com.fp.mode.http.HttpClient;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sang.com.xdialog.DialogFactory;
import sang.com.xdialog.XDialog;

public class ShowProActivity extends BasisActivity implements Observer<HttpBean<FinanceBean>>{

    public WebView webView;
    HorizontalProgress progress;
    XDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        progress = (HorizontalProgress) findViewById(R.id.pb);
        progress.setProgress(0);
        dialog= DialogFactory.getInstance().creatDiaolg(this,DialogFactory.LOAD_DIALOG);

        setColor(this, getResources().getColor(R.color.statucolor));
        initToolBar("网贷进度查询");
        webView = (WebView) findViewById(R.id.web);
        WebSettings mWebSettings = webView.getSettings();
        mWebSettings.setLoadWithOverviewMode(true);
        dialog.show();
        HttpClient.getClient().getContent("38","0","1").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this);

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





    }


    @Override
    public void finish() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.finish();
        }


    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        dialog.dismiss();
        e.printStackTrace();
    }

    @Override
    public void onNext(HttpBean<FinanceBean> financeBeanHttpBean) {
        dialog.dismiss();
        try {

            String content = financeBeanHttpBean.getTitle().get(0).getContent();
            webView.loadDataWithBaseURL("", content, "text/html", "utf-8", "");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
