package finance.com.fp.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.orhanobut.logger.Logger;

import finance.com.fp.BasisFragment;
import finance.com.fp.R;
import finance.com.fp.presenter.RegisterPreComl;
import finance.com.fp.presenter.inter.RegisterInter;
import finance.com.fp.ui.LoginActivity;
import finance.com.fp.ui.inter.FragmentListener;
import finance.com.fp.ui.inter.RegisterView;
import finance.com.fp.utlis.ToastUtil;
import finance.com.fp.utlis.Utils;
import sang.com.xdialog.DialogFactory;
import sang.com.xdialog.XDialog;
import sang.com.xdialog.inter.OnEntryClickListener;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/2/15 11:01
 */
public class RegisterPhoneFragment extends BasisFragment implements View.OnClickListener, RegisterView<String> {
    private EditText et_user, et_password, et_register;
    private Button bt_next, bt_dynamic, bt_vip;

    private RegisterInter pre;
    private FragmentListener listener;
    private XDialog dialog,inforDialog;

    @Override
    public View initViews(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_register, null);
        et_user = (EditText) view.findViewById(R.id.et_user);
        et_password = (EditText) view.findViewById(R.id.et_pasword);
        et_register = (EditText) view.findViewById(R.id.et_register);
        bt_next = (Button) view.findViewById(R.id.bt_login);
        bt_dynamic = (Button) view.findViewById(R.id.bt_dynamic);
        bt_vip = (Button) view.findViewById(R.id.bt_forget);

        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        bt_next.setOnClickListener(this);
        bt_dynamic.setOnClickListener(this);
        bt_vip.setOnClickListener(this);
        pre=new RegisterPreComl(this);
        initToolBar();
        dialog= DialogFactory.getInstance().creatDiaolg(getContext(),DialogFactory.LOAD_DIALOG);
        inforDialog= DialogFactory.getInstance().creatDiaolg(getContext(),DialogFactory.ALEART_DIALOG);
        inforDialog.setCancelable(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_forget:

                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
                break;
            case R.id.bt_dynamic:
                pre.getDynamic(getString(R.string.input_phone),et_user);
                break;
            case R.id.bt_login:
                pre.jumpToNext(getContext(),et_user,et_password,et_register);
                break;

        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener= (FragmentListener) context;
    }

    @Override
    public void onNextClick() {
        if (listener!=null){
            listener.onNextClick();
        }
    }

    @Override
    public void showEtError(EditText et, int input_dynamic) {
        et.setError(getString(input_dynamic));
        et.setText("");
        et.requestFocus();
    }

    @Override
    public void onStop() {
        super.onStop();
        pre.unSubscriber();
    }

    @Override
    public void upDynamic(String time, boolean b) {
        bt_dynamic.setText(time);
        if (bt_dynamic.isEnabled()!=b){
            bt_dynamic.setEnabled(b);
        }
    }

    @Override
    public void showDialog() {
        dialog.show();

    }

    @Override
    public void dissMissDialog() {
        dialog.dismiss();
    }

    @Override
    public void showNormal() {

    }

    @Override
    public void showDynamic() {

    }

    @Override
    public int getPhoneNotic() {
        return R.string.input_phone;
    }

    @Override
    public int getPasswordNotic() {
        return R.string.input_dynamic;
    }

    @Override
    public String getPhone() {
        return listener.getPhone();
    }

    @Override
    public void setPhone(String phone) {
        listener.setPhone(phone);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            et_user.requestFocus();
        }
    }

    public void initToolBar() {
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onBackClikc();
                }
            });

        }
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        dissMissDialog();
        ToastUtil.showTextToast(Utils.getResStr(R.string.net_error));
    }

    @Override
    public void onNext( String o) {
        dissMissDialog();

        final String a =o;
        inforDialog.setOnClickListener(new OnEntryClickListener() {
            @Override
            public void onClick(Dialog dialog, int which, Object data) {
                dialog.dismiss();
                if (!TextUtils.equals("1",a)) {
                    return;
                }
                //保存并跳转
                pre.setSp(getContext(),true);
                getActivity().finish();
            }
        });
        inforDialog.setStyle(XDialog.ALEART_ONLY_ENTRY);

        Logger.i(o+"");
       switch (o){
           case "0":
               inforDialog.setTitle(Utils.getResStr(R.string.register_fail));
               inforDialog.setDatas(Utils.getResStr(R.string.no_register));
               inforDialog.show();
               break;
           case "1":
               inforDialog.setTitle(Utils.getResStr(R.string.attention));
               inforDialog.setDatas(Utils.getResStr(R.string.temp_register));
               inforDialog.setCancelable(false);
               inforDialog.show();
               break;
           case "2":
               onNextClick();
               break;
           case "3":
               inforDialog.setTitle(Utils.getResStr(R.string.register_fail));
               inforDialog.setDatas(Utils.getResStr(R.string.register_has));
               inforDialog.show();
               break;
           default:
               inforDialog.setTitle(Utils.getResStr(R.string.register_fail));
               inforDialog.setDatas(Utils.getResStr(R.string.net_error));
               inforDialog.show();
               break;
       }

    }
}
