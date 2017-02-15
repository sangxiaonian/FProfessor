package finance.com.fp.ui.inter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import finance.com.fp.BasisFragment;
import finance.com.fp.R;
import finance.com.fp.presenter.RegisterPreComl;
import finance.com.fp.presenter.inter.RegisterInter;
import finance.com.fp.ui.LoginActivity;

import static finance.com.fp.R.id.cb_agree;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/2/15 11:01
 */
public class RegisterPhoneFragment extends BasisFragment implements View.OnClickListener, RegisterView {
    private EditText et_user, et_password, et_register;
    private Button bt_next, bt_dynamic, bt_vip;
    private CheckBox checkBox;
    private RegisterInter pre;
    private FragmentListener listener;

    @Override
    public View initViews(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_register, null);
        et_user = (EditText) view.findViewById(R.id.et_user);
        et_password = (EditText) view.findViewById(R.id.et_pasword);
        et_register = (EditText) view.findViewById(R.id.et_register);
        bt_next = (Button) view.findViewById(R.id.bt_login);
        bt_dynamic = (Button) view.findViewById(R.id.bt_dynamic);
        bt_vip = (Button) view.findViewById(R.id.bt_forget);
        checkBox = (CheckBox) view.findViewById(cb_agree);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        bt_next.setOnClickListener(this);
        bt_dynamic.setOnClickListener(this);
        bt_vip.setOnClickListener(this);
        pre=new RegisterPreComl(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_forget:
                startActivity(new Intent(getContext(), LoginActivity.class));
                break;
            case R.id.bt_dynamic:
                pre.getDynamic();
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
}
