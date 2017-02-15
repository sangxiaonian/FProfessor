package finance.com.fp.utlis;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/2/14 10:38
 */
public class Utils {

    /**
     * 解析时间
     * @param time 毫秒时间
     * @return
     */
    public static String fromatTime(String time){
        Long l = Long.parseLong(time);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );
        Date d1=new Date(l);
        return format.format(d1);
    }

    /**
     *获取当前时间
     * @param mode 格式 例如："yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static String getCurrentTimeo(String mode){

        SimpleDateFormat format=new SimpleDateFormat(mode);
        Date d1=new Date();
        return format.format(d1);
    }

}
