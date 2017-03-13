package finance.com.fp;

import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import org.json.JSONException;
import org.json.JSONObject;

import finance.com.fp.utlis.ToastUtil;

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




    }

    public static Context getContext() {
        return context;
    }

    public Notification getCusNotification(Context context, UMessage msg) {
        Logger.i(msg.toString() + msg.display_type + "body:" + "title:" + msg.title + "ticker:" + msg.ticker + "text:" + msg.text + "builder_id:" + msg.builder_id);

        Notification.Builder builder = new Notification.Builder(context);
        RemoteViews myNotificationView = new RemoteViews(context.getPackageName(), R.layout.notification_view);
        myNotificationView.setTextViewText(R.id.notification_title, msg.title);
        myNotificationView.setTextViewText(R.id.notification_text, msg.text);
        myNotificationView.setImageViewResource(R.id.notification_large_icon, R.mipmap.ic_launcher);
        myNotificationView.setImageViewResource(R.id.notification_small_icon, R.mipmap.ic_launcher);
        builder.setContent(myNotificationView)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setTicker(msg.ticker)
                .setAutoCancel(true);
        return builder.getNotification();


    }
}
