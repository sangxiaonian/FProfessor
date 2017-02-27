package finance.com.fp.utlis;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import static anetwork.channel.http.NetworkSdkSetting.context;

/**
 * PermissionUtil.
 * <ul>
 * <li>date: 16/7/18</li>
 * </ul>
 *
 * @author tongjin
 */
public class PermissionUtils {
    private Activity activity;
    private static PermissionUtils instance;
    private final static int  REQUEST_QUDE=1;

    private PermissionUtils() {

    }

    public static PermissionUtils getInstance() {
        if (instance == null) {
            synchronized (PermissionUtils.class) {
                if (instance == null) {
                    instance = new PermissionUtils();
                }
            }
        }
        return instance;
    }


    /**
     * 是否拥有权限.
     *
     * @param activity   activity
     * @param permission permission
     * @return 判断结果
     */
    public boolean has(@NonNull Activity activity, @NonNull String permission) {
        String[] permissions = {permission};
        return has(activity,permissions);
    }

    /**
     * 是否拥有权限.
     *
     * @param activity activity
     * @return 判断结果
     */
    public boolean has(final @NonNull Activity activity,
                       final @NonNull String[] permissions) {

        boolean hasPermission = true;
        boolean isTry = true;
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(context, permissions[i]) != PackageManager.PERMISSION_GRANTED){
                hasPermission=false;
                isTry=hasReason(activity,permissions[i]);
            }
        }

        if (!hasPermission) {
            if (isTry){

                request(activity,permissions,REQUEST_QUDE);
            }else {
                request(activity,permissions,REQUEST_QUDE);
            }
        } else {

        }

        return hasPermission;
    }


    /**
     * 判断是否再次申请权限
     *
     * @param activity
     * @param permission
     * @return
     */
    public  boolean hasReason(@NonNull Activity activity,
                                    @NonNull String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity,
                permission);
    }


    /**
     * 申请多个权限.
     *
     * @param activity    activity
     * @param permissions permissions
     * @param requestCode requestCode
     */
    public   void request(final @NonNull Activity activity,
                               final @NonNull String[] permissions,
                               final int requestCode) {
        ActivityCompat.requestPermissions(activity,
                permissions,
                requestCode);
    }

    /**
     * 申请单个权限.
     *
     * @param activity    activity
     * @param permission  permission
     * @param requestCode requestCode
     */
    public void request(final @NonNull Activity activity,
                               final @NonNull String permission,
                               final int requestCode) {
        String[] permissions = {permission};
        request(activity,
                permissions,
                requestCode);
    }


}