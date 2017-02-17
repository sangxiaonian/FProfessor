package finance.com.fp.presenter.inter;

import android.content.Context;
import android.widget.EditText;
import android.widget.RadioGroup;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/2/14 17:23
 */
public interface RegisterInter {


    void unSubscriber();

    /**
     * 获取验证码
     */
    void getDynamic();

    void jumpToNext(Context context, EditText et_user, EditText et_password, EditText et_register);

    void onRgCheckChanged(RadioGroup group, int checkedId);
}
