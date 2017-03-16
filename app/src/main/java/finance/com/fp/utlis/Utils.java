package finance.com.fp.utlis;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.umeng.message.IUmengCallback;
import com.umeng.message.PushAgent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import finance.com.fp.CusApplication;
import finance.com.fp.R;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.http.Config;
import finance.com.fp.ui.RegisterActivity;
import sang.com.xdialog.DialogFactory;
import sang.com.xdialog.XDialog;
import sang.com.xdialog.inter.OnEntryClickListener;

import static android.R.attr.path;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/2/14 10:38
 */
public class Utils {

    /**
     * 解析时间
     *
     * @param time 毫秒时间
     * @return
     */
    public static String fromatTime(String time) {
        Long l = Long.parseLong(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = new Date(l);
        return format.format(d1);
    }

    /**
     * 获取当前时间
     *
     * @param mode 格式 例如："yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static String getCurrentTimeo(String mode) {

        SimpleDateFormat format = new SimpleDateFormat(mode);
        Date d1 = new Date();
        return format.format(d1);
    }

    /**
     * 获取两位小数
     *
     * @param data
     * @return
     */
    public static float get2Double(double data) {
        BigDecimal b = new BigDecimal(data);
//        return b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        //   b.setScale(2,   BigDecimal.ROUND_HALF_UP)   表明四舍五入，保留两位小数
        float v = (float) Double.parseDouble(String.format("%.2f", data));

        return v;
    }

    /**
     * MD5加密类
     *
     * @param str 要加密的字符串
     * @return 加密后的字符串
     */
    public static String MD5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] byteDigest = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < byteDigest.length; offset++) {
                i = byteDigest[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString().toUpperCase();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 获取随机数
     *
     * @return
     */
    public static String getRound(int count) {
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            buffer.append(random.nextInt(10));
        }

        return buffer.toString();
    }

    public static String getResStr(int id) {
        return CusApplication.getContext().getString(id);
    }

    /**
     *   * 设置注册
     * @param context
     * @param isTemp  是否是临时卡
     * @param register 注册码/密码
     * @param phone 手机号
     */
    public static void setSp(Context context, boolean isTemp,String register,String phone) {
        SharedPreferences preferences = context.getSharedPreferences(Config.sp_name, 0);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putBoolean(Config.sp_try, isTemp);
        if (isTemp) {
            edit.putString(Config.sp_try_code, register);
            edit.putString(Config.login_name, phone);
            edit.putLong(Config.sp_try_time, System.currentTimeMillis());
        }else {
            edit.putString(Config.login_name, phone);
            edit.putString(Config.login_password, register);
        }

        edit.commit();
    }

    /**
     *
     * @param context
     */
    public static String getSp(Context context, String phone) {
        SharedPreferences preferences = context.getSharedPreferences(Config.sp_name, 0);
       return preferences.getString(phone,"");
    }

    /**
     * 设置注册
     * @param context
     */
    public static boolean getBooleanSp(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(Config.sp_name, 0);
        return preferences.getBoolean(name,true);
    }

    /**
     *  设置配置文件
     * @param context

     */
    public static void setSp(Context context,String name,String value) {
        SharedPreferences preferences = context.getSharedPreferences(Config.sp_name, 0);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(name, value);
        edit.commit();
    }


    /**
     *  设置配置文件
     * @param context

     */
    public static void setSp(Context context,String name,boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(Config.sp_name, 0);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putBoolean(name, value);
        edit.commit();
    }


    public static boolean isLogion( Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Config.sp_name, 0);
        boolean isTry = preferences.getBoolean(Config.sp_try, false);
        if (isTry){
            long l = System.currentTimeMillis() - preferences.getLong(Config.sp_try_time, System.currentTimeMillis());
            if (l <1000*60*60*2&&l>0){
                return true;
            }else {
                return false;
            }
        }else {
            String name = preferences.getString(Config.login_name, "");
            String pas = preferences.getString(Config.login_password, "");
            if (!TextUtils.isEmpty(name)){
                return true;
            }else {
                return false;
            }
        }

    }

    public static void login_out(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Config.sp_name, 0);
        SharedPreferences.Editor edit = preferences.edit();
        edit.clear().commit();
    }

    public static void showLoginDialog(final Context context){
        XDialog dialog  = DialogFactory.getInstance().creatDiaolg(context,DialogFactory.ALEART_DIALOG);
        dialog.setTitle("提示");
        dialog.setDatas("登录后才能进行该操作");
        dialog.setOnClickListener(new OnEntryClickListener() {
            @Override
            public void onClick(Dialog dialog, int which, Object data) {
                dialog.dismiss();
                context.startActivity(new Intent(context, RegisterActivity.class));

            }
        });
        dialog.show();
    }

    public static List<Set_Item> getRandomData(String msg,String msg2) {
        List<Set_Item> list = new ArrayList<>();
        Random random = new Random();
        random.nextInt();
        String[] balances = CusApplication.getContext().getResources().getStringArray(R.array.all_balances);
        String[] phones = CusApplication.getContext().getResources().getStringArray(R.array.phones);

        for (int i = 0; i < 100; i++) {
            String phone = phones[random.nextInt(phones.length)];
            StringBuffer buffer = new StringBuffer();
            buffer
                    .append(phone)
                    .append("****")
                    .append(getRound(4))
                    .append(msg)
                    .append(balances[random.nextInt(balances.length)])
                    .append(msg2)

            ;
            Set_Item item = new Set_Item(0,buffer.toString(),random.nextInt(60)+1+"分钟前");
            list.add(item);
        }
        return list;
    }
    public static List<Set_Item> getRandomLoanData() {
        List<Set_Item> list = new ArrayList<>();
        Random random = new Random();
        random.nextInt();
        String[] balances = CusApplication.getContext().getResources().getStringArray(R.array.all_balances);
        String[] phones = CusApplication.getContext().getResources().getStringArray(R.array.phones);

        for (int i = 0; i < 100; i++) {
            String phone = phones[random.nextInt(phones.length)];
            StringBuffer buffer = new StringBuffer();
            buffer.append(phone)
                    .append("****")
                    .append(getRound(4))
                    .append("成功申请贷款")
                    .append((random.nextInt(100)+1)/10f)
                    .append("万元")

            ;
            Set_Item item = new Set_Item(0,buffer.toString(),random.nextInt(60)+1+"分钟前");
            list.add(item);
        }
        return list;
    }

    public static Context getContext() {
        return CusApplication.getContext().getApplicationContext();
    }

    /**
     * 保存图片到图库
     * @param context
     * @param bmp
     */
    public static void saveImageToGallery(Context context, Bitmap bmp) {
       // 首先保存图片
        boolean has = PermissionUtils.getInstance()
                .showMsg(Utils.getResStr(R.string.no_permission), Utils.getResStr(R.string.reason_permission))
                .has((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (has) {
            boolean isSuccess=true;
            File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
            if (!appDir.exists()) {
                appDir.mkdir();
            }
            String fileName = System.currentTimeMillis() + ".jpg";
            File file = new File(appDir, fileName);
            try {
                FileOutputStream fos = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                isSuccess=false;
            } catch (IOException e) {
                isSuccess=false;
                e.printStackTrace();
            }

            // 其次把文件插入到系统图库
            try {
                MediaStore.Images.Media.insertImage(context.getContentResolver(),
                        file.getAbsolutePath(), fileName, null);
            } catch (FileNotFoundException e) {
                isSuccess=false;
                e.printStackTrace();
            }
            if (isSuccess){
                ToastUtil.showTextToast(context.getString(R.string.friend_save_succeed));
            }else {
                ToastUtil.showTextToast(context.getString(R.string.friend_save_fail));
            }
            // 最后通知图库更新
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
        }
    }

    public static void openPush(PushAgent mPushAgent,Context context) {
        Utils.setSp(context,Config.isopenPush,true);
        mPushAgent.enable(new IUmengCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });
    }

    public static void close(PushAgent mPushAgent,Context context){
        Utils.setSp(context,Config.isopenPush,false);
        mPushAgent.disable(new IUmengCallback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onFailure(String s, String s1) {
            }
        });
    }
}
