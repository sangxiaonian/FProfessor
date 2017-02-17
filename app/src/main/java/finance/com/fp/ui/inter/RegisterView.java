package finance.com.fp.ui.inter;


import android.widget.EditText;

public interface RegisterView {



    void onNextClick();

    void showEtError(EditText et, int input_dynamic);

    /**
     * 更新验证码上面是数据
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
}
