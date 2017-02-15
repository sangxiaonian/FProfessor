package finance.com.fp.presenter;

import android.widget.RadioGroup;

import finance.com.fp.R;
import finance.com.fp.presenter.inter.LoginInter;
import finance.com.fp.ui.inter.LoginView;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/2/14 17:24
 */
public class LoginPreComl implements LoginInter {

    private  LoginView view;

    public LoginPreComl(LoginView view){
        this.view=view;
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
}
