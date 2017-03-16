package finance.com.fp;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import finance.com.fp.mode.http.Config;
import finance.com.fp.utlis.ToastUtil;
import finance.com.fp.utlis.Utils;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/12/27 17:31
 */
public class CusApplication extends Application {

    private static Context context;
    private boolean hasNewNative, hasFriendNative;

    public boolean isHasNewNative() {
        return hasNewNative;
    }

    public boolean isHasFriendNative() {
        return hasFriendNative;
    }

    public void setHasNewNative(boolean hasNewNative) {
        this.hasNewNative = hasNewNative;
    }

    public void setHasFriendNative(boolean hasFriendNative) {
        this.hasFriendNative = hasFriendNative;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtil.init(this);
        Logger.init("PING");
        context = this;
        Logger.i("友盟注册");
        CrashReport.initCrashReport(getApplicationContext(), "b038c087da", false);
        PushAgent mPushAgent = PushAgent.getInstance(this);
//注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Logger.i("友盟注册成功:" + deviceToken);

            }

            @Override
            public void onFailure(String s, String s1) {
                Logger.i("友盟注册失败" + s + ">>>" + s1);
            }
        });

        boolean booleanSp = Utils.getBooleanSp(this, Config.isopenPush);
        if (booleanSp){
            Utils.close(mPushAgent,this);
        }else {
            Utils.openPush(mPushAgent,this);
        }
    }

    public static Context getContext() {
        return context;
    }


}
