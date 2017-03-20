package finance.com.fp;

import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
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

import java.util.Map;

import finance.com.fp.mode.http.Config;
import finance.com.fp.ui.inter.MainView;
import finance.com.fp.utlis.ToastUtil;
import finance.com.fp.utlis.Utils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/12/27 17:31
 */
public class CusApplication extends Application {

    private static Context context;
    private MainView view;
    public void setView(MainView view){
        this.view=view;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtil.init(this);
        Logger.init("PING");
        context = this;
        Logger.i("友盟注册");
        initUmen();
    }

    private void initUmen() {
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

        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            /**
             * 自定义通知栏样式的回调方法
             * */
            @Override
            public Notification getNotification(Context context, final UMessage msg) {
                Logger.i("自定义通知栏来了");
                Observable.just(msg)
                        .filter(new Func1<UMessage, Boolean>() {
                            @Override
                            public Boolean call(UMessage uMessage) {
                                Map<String, String> map = msg.extra;
                                Logger.i("自定义通知栏来了");
                                return  map!=null&&!TextUtils.isEmpty(map.get("typeId"))&&TextUtils.isDigitsOnly(map.get("typeId"));
                            }
                        })
                         .map(new Func1<UMessage,Integer>() {
                             @Override
                             public Integer call(UMessage uMessage) {
                                 Map<String, String> map = msg.extra;
                                 String typeId = map.get("typeId");
                                 Logger.i("自定义通知栏来了"+typeId);
                                 return Integer.parseInt(typeId);
                             }
                         })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Integer>() {
                            @Override
                            public void onCompleted() {
                            }
                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }
                            @Override
                            public void onNext(Integer integer) {
                                switch (integer) {
                                    case 1:
                                        Utils.setSp(getContext(),Config.showMsg,true);
                                        if (view!=null) {
                                            view.showMsg();
                                        }
                                        break;
                                    case 2:
                                        Utils.setSp(getContext(),Config.showFriend,true);
                                        if (view!=null) {
                                            view.showFriend();
                                        }
                                        break;
                                    default:
                                        Logger.i("显示数据");
                                        break;
                                }
                                UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
                            }
                        });

                return getCusNotification(context, msg);
            }
        };
        mPushAgent.setMessageHandler(messageHandler);

        /**
         * 自定义行为的回调处理
         * UmengNotificationClickHandler是在BroadcastReceiver中被调用，故
         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
         * */
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
                Logger.i("-----------------自定义消息被点击了---------------------");
            }
        };
        //使用自定义的NotificationHandler，来结合友盟统计处理消息通知
        //参考http://bbs.umeng.com/thread-11112-1-1.html
//        CustomNotificationHandler notificationClickHandler1 = new CustomNotificationHandler();
//        mPushAgent.setNotificationClickHandler(notificationClickHandler1);
        mPushAgent.setNotificationClickHandler(notificationClickHandler);

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
    public Notification getCusNotification(Context context, UMessage msg) {

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
        Notification notification = builder.getNotification();
        return notification;


    }

}
