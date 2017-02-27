package finance.com.fp.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import finance.com.fp.CusApplication;
import finance.com.fp.R;
import finance.com.fp.mode.bean.AliMsgBean;
import finance.com.fp.mode.bean.DynamicBean;
import finance.com.fp.mode.datafractory.HttpFactory;
import finance.com.fp.presenter.inter.RegisterInter;
import finance.com.fp.ui.fragment.RegisterPasswordFragment;
import finance.com.fp.ui.fragment.RegisterPhoneFragment;
import finance.com.fp.ui.inter.RegisterView;
import finance.com.fp.utlis.ToastUtil;
import finance.com.fp.utlis.Utils;
import rx.Subscriber;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/2/14 17:24
 */
public class RegisterPreComl implements RegisterInter {

    private RegisterView view;
    private String register,register_code,phone;


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
    public void getDynamic(String string, EditText et_user) {
        String phone = et_user.getText().toString().trim();
        if (TextUtils.isEmpty(phone)){
            et_user.setError(string);
            return;
        }else {
            DynamicBean bean = new DynamicBean();
            bean.setCode(Utils.getRound(6));
            bean.setProduct(CusApplication.getContext().getString(R.string.app_name));
            register=bean.getCode();
            Logger.i(register);
//            HttpFactory.getDynamic(phone,bean).subscribe(getDynamic_subscriber());
        }

    }
    String trim;
    @Override
    public void jumpToNext(Context context, EditText et_user, EditText et_password, EditText et_register) {

        if (et_user!=null){
            phone = et_user.getText().toString().trim();
            if (TextUtils.isEmpty(phone)) {
                view.showEtError(et_user, view.getPhoneNotic());
                return;
            }
        }



        if (et_register!=null){
            register_code = et_register.getText().toString().trim();
            if (TextUtils.isEmpty(register_code)) {
                view.showEtError(et_register, R.string.input_register);
                return;
            }
            if (TextUtils.isEmpty(this.register)||!TextUtils.equals(this.register, trim)){
                ToastUtil.showTextToast("证码错误");
                return;
            }
        }

        trim = et_password.getText().toString().trim();
        if (et_password!=null&&TextUtils.isEmpty(trim)){
            view.showEtError(et_password, view.getPasswordNotic());
            return;
        }

        if (view instanceof RegisterPasswordFragment){

            if (!TextUtils.equals(phone, trim)){
                et_password.setError(Utils.getResStr(R.string.psd_different));
                return;
            }
            view.showDialog();
            String phoneNumber= view.getPhone();
            HttpFactory.setPassword(phoneNumber,trim).subscribe(view);
        }else if (view instanceof RegisterPhoneFragment){
            view.setPhone(phone);
            view.showDialog();
            HttpFactory.register(phone,register_code).subscribe(view);
        }else {

            HttpFactory.login(phone,trim,view.getPasswordNotic()==R.string.input_dynamic).subscribe(view);
        }

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

    @Override
    public void setSp(Context context, boolean b) {
        if (b) {
            Utils.setSp(context, b, register_code, phone);
        }else {
            Utils.setSp(context, b, trim, view.getPhone());
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
                time=60;
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                time=0;
                view.upDynamic(CusApplication.getContext().getString(R.string.get_dynamic), true);
                ToastUtil.showTextToast(CusApplication.getContext().getString(R.string.send_dynamic_fail));
            }

            @Override
            public void onNext(String o) {
                Logger.i(o);
                try{
                    AliMsgBean msgBean = new Gson().fromJson(o, AliMsgBean.class);
                    if (msgBean.getAlibaba_aliqin_fc_sms_num_send_response().getResult().isSuccess()){

                        ToastUtil.showTextToast(CusApplication.getContext().getString(R.string.send_dynamic_success));
                    }else {
                        time=0;
                        view.upDynamic(CusApplication.getContext().getString(R.string.get_dynamic), true);
                        ToastUtil.showTextToast(CusApplication.getContext().getString(R.string.send_dynamic_fail));
                    }
                }catch (Exception e){
                    time=0;
                    ToastUtil.showTextToast(CusApplication.getContext().getString(R.string.send_dynamic_fail));
                    e.printStackTrace();
                }


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
