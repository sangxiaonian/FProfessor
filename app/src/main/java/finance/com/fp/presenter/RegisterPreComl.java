package finance.com.fp.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import finance.com.fp.R;
import finance.com.fp.mode.datafractory.HttpFactory;
import finance.com.fp.presenter.inter.RegisterInter;
import finance.com.fp.ui.inter.RegisterView;
import rx.Subscriber;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/2/14 17:24
 */
public class RegisterPreComl implements RegisterInter {

    private RegisterView view;

    private Subscriber<String> subscriber;



    public RegisterPreComl(RegisterView view){
        this.view=view;
    }



    @Override
    public void unSubscriber() {
        if (subscriber!=null&&!subscriber.isUnsubscribed()){
            subscriber.unsubscribe();
        }
    }

    @Override
    public void getDynamic() {
        HttpFactory.getDynamic().subscribe(getSubscriber());
    }

    @Override
    public void jumpToNext(Context context, EditText et_user, EditText et_password, EditText et_register) {
        if (TextUtils.isEmpty(et_user.getText().toString().trim())){
            view.showEtError(et_user, R.string.input_phone);
            return;
        }

        if (TextUtils.isEmpty(et_password.getText().toString().trim())){
            view.showEtError(et_password, R.string.input_dynamic);
            return;
        }
        if (TextUtils.isEmpty(et_register.getText().toString().trim())){
            view.showEtError(et_register, R.string.input_register);
            return;
        }
        view.onNextClick();

    }




    private Subscriber<String> getSubscriber() {
        unSubscriber();
        return subscriber =new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(String o) {

            }
        };
    }
}
