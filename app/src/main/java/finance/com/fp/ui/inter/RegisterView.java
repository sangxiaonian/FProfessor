package finance.com.fp.ui.inter;


import android.widget.EditText;

import rx.Observer;


public interface RegisterView<T> extends Observer<T> {



    void onNextClick();

    void showEtError(EditText et, int input_dynamic);

    /**
     * 更新验证码上面是数
     * @param time
     * @param b
     */
    void upDynamic(String time, boolean b);

    void showDialog();

    void dissMissDialog();

    void showNormal();

    void showDynamic();

    int getPhoneNotic();

    int getPasswordNotic();

    String getPhone();

    void setPhone(String phone);

    boolean showView(EditText et_register);

    boolean re_psd();
}
