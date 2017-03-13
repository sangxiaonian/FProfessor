package finance.com.fp.ui;

import android.app.Notification;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import finance.com.fp.BasisActivity;
import finance.com.fp.BasisFragment;
import finance.com.fp.R;
import finance.com.fp.ui.fragment.FindFragment;
import finance.com.fp.ui.fragment.HomeFragment;
import finance.com.fp.ui.fragment.SetFragment;
import finance.com.fp.ui.inter.MainView;

import static anet.channel.util.Utils.context;

public class MainActivity extends BasisActivity implements View.OnClickListener,MainView{


    /**
     * The {@link ViewPager} that will host the section contents.
     */

    private LinearLayout ll_home, ll_set;
    private LinearLayout ll_find;
    private TextView tv_home, tv_find, tv_set;
    private ImageView img_home, img_find, img_set,img_red;
    private List<View> views;
    private FrameLayout vp;
    private List<Fragment> fragments;
    private boolean showMsg,showFriend;
    private String home="home";
    private String find="find";
    private String set="set";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setColor(this, getResources().getColor(R.color.colorPrimary));
        initView();
        initDatas();
        initListener();


    }


    private void initDatas() {
        views = new ArrayList<>();
        fragments = new ArrayList<>();
        views.add(tv_find);
        views.add(tv_home);
        views.add(tv_set);
        views.add(img_set);
        views.add(img_home);
        views.add(img_find);


    }



    private void initListener() {
        ll_find.setOnClickListener(this);
        ll_set.setOnClickListener(this);
        ll_home.setOnClickListener(this);
        ll_home.performClick();
        PushAgent mPushAgent = PushAgent.getInstance(this);
        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            /**
             * 自定义消息的回调方法
             * */
            @Override
            public void dealWithCustomMessage(final Context context, final UMessage msg) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Map<String, String> map = msg.extra;
                                String typeId = map.get("typeId");
                                Logger.e(msg.builder_id + ">>>>>>" + typeId);
                                switch (typeId) {
                                    case "1":
                                        if (getSupportFragmentManager().findFragmentByTag(home) != null) {
                                            homeFragment.showRed();
                                        }
                                        showMsg = true;
                                        break;
                                    case "2":
                                        if (getSupportFragmentManager().findFragmentByTag(find) != null) {
                                            findFragment.showRed();
                                        }
                                        Logger.i("显示数据");
                                        img_red.setVisibility(View.VISIBLE);
                                        showFriend = true;
                                        break;
                                    default:
                                        Logger.i("显示数据");
                                        break;
                                }
                                UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);

                            }
                        });
                        // TODO Auto-generated method stub
                        // 对自定义消息的处理方式，点击或者忽略




//                        if (isClickOrDismissed) {
//                            //自定义消息的点击统计
//                            UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
//                        } else {
//                            //自定义消息的忽略统计
//                            UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
//                        }

                }


            /**
             * 自定义通知栏样式的回调方法
             * */
            @Override
            public Notification getNotification(Context context, UMessage msg) {

                return getCusNotification(context, msg);
//                }
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
        //CustomNotificationHandler notificationClickHandler = new CustomNotificationHandler();
        mPushAgent.setNotificationClickHandler(notificationClickHandler);


    }

    public void initView() {
        tv_find = (TextView) findViewById(R.id.tv_find);
        tv_set = (TextView) findViewById(R.id.tv_set);
        tv_home = (TextView) findViewById(R.id.tv_home);

        img_find = (ImageView) findViewById(R.id.img_find);
        img_set = (ImageView) findViewById(R.id.img_set);
        img_home = (ImageView) findViewById(R.id.img_home);

        ll_home = (LinearLayout) findViewById(R.id.ll_home);
        ll_find = (LinearLayout) findViewById(R.id.ll_find);
        ll_set = (LinearLayout) findViewById(R.id.ll_set);
        vp = (FrameLayout) findViewById(R.id.container);
        img_red= (ImageView) findViewById(R.id.img_red);

    }

    HomeFragment homeFragment;
    FindFragment findFragment;
    SetFragment setFragment;
    BasisFragment currentFragment;

    @Override
    public void onClick(View v) {



        FragmentManager fm = getSupportFragmentManager();
        // 开启Fragment事务

        FragmentTransaction transaction = fm.beginTransaction();
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }
        switch (v.getId()) {
            case R.id.ll_home:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.container, homeFragment,home);

                } else {
                    transaction.show(homeFragment);
                }
                currentFragment = homeFragment;
                changeState(0);
                break;
            case R.id.ll_find:
                if (findFragment == null) {
                    findFragment = new FindFragment();
                    transaction.add(R.id.container, findFragment,find);
                } else {
                    transaction.show(findFragment);
                }
                currentFragment = findFragment;

                changeState(1);
                break;
            case R.id.ll_set:


                if (setFragment == null) {
                    setFragment = new SetFragment();
                    transaction.add(R.id.container, setFragment,set);

                } else {
                    transaction.show(setFragment);
                }
                currentFragment = setFragment;
                changeState(2);
                break;
        }
        transaction.commit();
    }

    private void changeState(int position) {
        switch (position) {
            case 0:
                changeState(img_home, tv_home);
                break;
            case 1:
                changeState(img_find, tv_find);
                break;
            case 2:
                changeState(img_set, tv_set);
                break;
        }
    }

    private void changeState(View img, View textView) {
        for (View v : views) {
            if (v == img || v == textView) {
                v.setEnabled(true);
            } else {
                v.setEnabled(false);
            }
        }
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
        return builder.getNotification();


    }

    @Override
    public boolean hasNewMsg() {
        return showMsg;
    }

    @Override
    public boolean hasNewFriend() {
        return showFriend;
    }

    @Override
    public void removeFriend() {
        img_red.setVisibility(View.GONE);
        showFriend=false;
    }

    @Override
    public void removeMsg() {
        showMsg=false;
    }
}
