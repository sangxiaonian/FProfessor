package finance.com.fp.ui.fragment;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import finance.com.fp.BasisFragment;
import finance.com.fp.R;
import finance.com.fp.presenter.RegisterPreComl;
import finance.com.fp.presenter.inter.RegisterInter;
import finance.com.fp.ui.inter.FragmentListener;
import finance.com.fp.ui.inter.RegisterView;
import finance.com.fp.utlis.Utils;
import sang.com.xdialog.DialogFactory;
import sang.com.xdialog.XDialog;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/2/15 11:01
 */
public class RegisterPasswordFragment extends BasisFragment implements View.OnClickListener, RegisterView<String> {
    private EditText et_user, et_password;
    private Button bt_next;

    private RegisterInter pre;
    private FragmentListener listener;
    private XDialog dialog,inforDialog;


    @Override
    public View initViews(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_password, null);
        et_user = (EditText) view.findViewById(R.id.et_user);
        et_password = (EditText) view.findViewById(R.id.et_pasword);
        bt_next = (Button) view.findViewById(R.id.bt_login);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        pre = new RegisterPreComl(this);
        bt_next.setOnClickListener(this);
        if (listener!=null&&listener.isRegister()){
            initToolBar(null);
        }else {
            initToolBar(getString(R.string.psd_change));
        }
        dialog= DialogFactory.getInstance().creatDiaolg(getContext(),DialogFactory.LOAD_DIALOG);
        inforDialog= DialogFactory.getInstance().creatDiaolg(getContext(),DialogFactory.ALEART_DIALOG);

    }

    @Override
    public void onClick(View v) {
        pre.jumpToNext(getContext(), et_user, et_password, null);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (FragmentListener) context;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            et_user.requestFocus();

        }
    }

    @Override
    public void onNextClick() {

        if (listener != null) {
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
    public void upDynamic(String time, boolean b) {

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
        return R.string.input_password;
    }

    @Override
    public int getPasswordNotic() {
        return R.string.input_password_entry;
    }

    @Override
    public String getPhone() {
        return listener.getPhone();
    }

    @Override
    public void setPhone(String phone) {

    }


    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        dialog.dismiss();
        inforDialog.setTitle(Utils.getResStr(R.string.attention));
        inforDialog.setDatas(Utils.getResStr(R.string.net_error));
        inforDialog.showStyle(XDialog.ALEART_ONLY_ENTRY);
    }

    @Override
    public void onNext(String s) {
        dialog.dismiss();

        switch (s){
            case "0":
                inforDialog.setTitle(Utils.getResStr(R.string.psd_fail));
                inforDialog.setDatas(Utils.getResStr(R.string.psd_fail_reason));
                inforDialog.showStyle(XDialog.ALEART_ONLY_ENTRY);
                break;
            case "1":
                onNextClick();
                pre.setSp(getActivity(),false);
                break;
        }
    }
    public void initToolBar(String title) {
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onBackClikc();
                }
            });

        }

        TextView tv_title = (TextView) rootView.findViewById(R.id.title);
        if (title!=null){
            tv_title.setText(title);
        }
    }

}
