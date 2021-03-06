package finance.com.fp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;


import com.sang.viewfractory.utils.BarUtils;

import java.util.concurrent.TimeUnit;

import finance.com.fp.BasisActivity;
import finance.com.fp.CusApplication;
import finance.com.fp.R;
import finance.com.fp.mode.http.Config;
import finance.com.fp.utlis.Utils;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class SplishActivity extends BasisActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarUtils.hideStatusBar(this);
        setContentView(R.layout.activity_splish);

        Observable.just(0).subscribeOn(Schedulers.io()).delay(2, TimeUnit.SECONDS)
                .map(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        ((CusApplication)getApplication()).initUmen();
                        String sp = Utils.getSp(SplishActivity.this, Config.isFirst);
                        return TextUtils.isEmpty(sp);
                    }
                })

                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        startActivity(new Intent(SplishActivity.this, MainActivity.class));
                        finish();
                        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
                    }

                    @Override
                    public void onNext(Boolean integer) {
                        if (integer) {
                            startActivity(new Intent(SplishActivity.this, GuideActivity.class));
                            finish();
                        } else {
                            startActivity(new Intent(SplishActivity.this, MainActivity.class));
                            finish();
                        }
                        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);

                    }
                });
    }
}
