package finance.com.fp;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.Logger;

import finance.com.fp.utlis.ToastUtil;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/12/27 17:31
 */
public class CusApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtil.init(this);
        Logger.init("PING");
        context=this;

    }

    public static Context getContext() {
        return context;
    }
}
