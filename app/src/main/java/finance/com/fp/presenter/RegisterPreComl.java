package finance.com.fp.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.RadioGroup;

import finance.com.fp.CusApplication;
import finance.com.fp.R;
import finance.com.fp.mode.datafractory.HttpFactory;
import finance.com.fp.presenter.inter.RegisterInter;
import finance.com.fp.ui.inter.RegisterView;
import finance.com.fp.utlis.ToastUtil;
import rx.Subscriber;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/2/14 17:24
 */
public class RegisterPreComl implements RegisterInter {

    private RegisterView view;

    private Subscriber<String> subscriber,dynamic_subscriber;
    private int time=60;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            time--;
            if (time>=0){
                view.upDynamic("已发送("+time+")",false);
                sendEmptyMessageDelayed(0,1000);
            }else {
                view.upDynamic(CusApplication.getContext().getString(R.string.get_dynamic), true);
            }
        }
    };


    public RegisterPreComl(RegisterView view){
        this.view=view;
    }



    @Override
    public void unSubscriber() {
        if (subscriber!=null&&!subscriber.isUnsubscribed()){
            subscriber.unsubscribe();
        }

        if (dynamic_subscriber!=null&&!dynamic_subscriber.isUnsubscribed()){
            dynamic_subscriber.unsubscribe();
        }

        if (handler!=null&&handler.hasMessages(0)){
            view.upDynamic(CusApplication.getContext().getString(R.string.get_dynamic), false);
            handler.removeMessages(0);
        }
    }


    @Override
    public void getDynamic() {

        HttpFactory.getDynamic().subscribe(getDynamic_subscriber());
    }

    @Override
    public void jumpToNext(Context context, EditText et_user, EditText et_password, EditText et_register) {
        if (et_user!=null&&TextUtils.isEmpty(et_user.getText().toString().trim())){
            view.showEtError(et_user, view.getPhoneNotic());
            return;
        }

        if (et_password!=null&&TextUtils.isEmpty(et_password.getText().toString().trim())){
            view.showEtError(et_password, view.getPasswordNotic());
            return;
        }
        if (et_register!=null&&TextUtils.isEmpty(et_register.getText().toString().trim())){
            view.showEtError(et_register, R.string.input_register);
            return;
        }
        HttpFactory.register().subscribe(getSubscriber());

    }

    @Override
    public void onRgCheckChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rb_normal://普通登陆
                view.showNormal();
                break;
            case R.id.rb_dynamic://动态登陆
                view.showDynamic();
                break;
        }
    }


    private Subscriber<String> getDynamic_subscriber(){
        if (dynamic_subscriber!=null&&!dynamic_subscriber.isUnsubscribed()){
            dynamic_subscriber.unsubscribe();
        }
        return dynamic_subscriber =new Subscriber<String>() {

            @Override
            public void onStart() {
                super.onStart();
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                ToastUtil.showTextToast(CusApplication.getContext().getString(R.string.send_dynamic_fail));
            }

            @Override
            public void onNext(String o) {
                ToastUtil.showTextToast(CusApplication.getContext().getString(R.string.send_dynamic_success));

            }
        };
    }


    private Subscriber<String> getSubscriber() {
        if (subscriber!=null&&!subscriber.isUnsubscribed()){
            subscriber.unsubscribe();
        }
        return subscriber =new Subscriber<String>() {

            @Override
            public void onStart() {
                super.onStart();
                view.showDialog();
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                view.dissMissDialog();
                ToastUtil.showTextToast("请检查网络环境是否正常");

            }

            @Override
            public void onNext(String o) {
                view.dissMissDialog();
                view.onNextClick();
            }
        };
    }
}
