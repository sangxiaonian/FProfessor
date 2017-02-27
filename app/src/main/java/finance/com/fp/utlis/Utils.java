package finance.com.fp.utlis;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import finance.com.fp.CusApplication;
import finance.com.fp.mode.http.Config;

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
     * 设置注册
     * @param context
     */
    public static String getSp(Context context, String phone) {
        SharedPreferences preferences = context.getSharedPreferences(Config.sp_name, 0);
       return preferences.getString(phone,"");
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
            if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(pas)){
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
}
