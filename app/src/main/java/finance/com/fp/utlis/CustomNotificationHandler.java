package finance.com.fp.utlis;

import android.content.Context;

import com.orhanobut.logger.Logger;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import java.util.HashMap;
import java.util.Map;

public class CustomNotificationHandler extends UmengNotificationClickHandler {



        @Override
        public void dismissNotification(Context context, UMessage msg) {
                super.dismissNotification(context, msg);
//                MobclickAgent.onEvent(context, "dismiss_notification");
                Logger.e("---dismissNotification---------");
        }
         
        @Override
        public void launchApp(Context context, UMessage msg) {
                super.launchApp(context, msg);
                Map<String, String> map = new HashMap<String, String>();
                map.put("action", "launch_app");
//                MobclickAgent.onEvent(context, "click_notification", map);
                Logger.e("---dismissNotification---------");

        }
         
        @Override
        public void openActivity(Context context, UMessage msg) {
                super.openActivity(context, msg);
                Map<String, String> map = new HashMap<String, String>();
                map.put("action", "open_activity");
//                MobclickAgent.onEvent(context, "click_notification", map);
                Logger.e("---dismissNotification---------");
        }
         
        @Override
        public void openUrl(Context context, UMessage msg) {
                super.openUrl(context, msg);
                Map<String, String> map = new HashMap<String, String>();
                map.put("action", "open_url");

                Logger.e("---dismissNotification---------");
//                MobclickAgent.onEvent(context, "click_notification", map);
        }
         
        @Override
        public void dealWithCustomAction(Context context, UMessage msg) {
                super.dealWithCustomAction(context, msg);
                Map<String, String> map = new HashMap<String, String>();
                map.put("action", "custom_action");
                Logger.e("---dismissNotification---------");
//                MobclickAgent.onEvent(context, "click_notification", map);
        }
         
        @Override
        public void autoUpdate(Context context, UMessage msg) {
                super.autoUpdate(context, msg);
                Map<String, String> map = new HashMap<String, String>();
                map.put("action", "auto_update");
                Logger.e("---dismissNotification---------");
//                MobclickAgent.onEvent(context, "click_notification", map);
        }
 
}