package finance.com.fp.utlis;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * PermissionUtil.
 * <ul>
 * <li>date: 16/7/18</li>
 * </ul>
 *
 * @author tongjin
 */
public class PermissionUtil {
    /**
     * 是否拥有权限.
     *
     * @param context    context
     * @param permission permission
     * @return 判断结果
     */
    public static boolean has(@NonNull Context context, @NonNull String permission) {
        return ContextCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean hasReason(@NonNull Activity activity,
                                    @NonNull String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity,
                permission);
    }

    /**
     * 申请权限.
     *
     * @param activity    activity
     * @param permissions permissions
     * @param requestCode requestCode
     */
    public static void request(final @NonNull Activity activity,
                               final @NonNull String[] permissions,
                               final int requestCode) {
        ActivityCompat.requestPermissions(activity,
                permissions,
                requestCode);
    }

    /**
     * 申请权限.
     *
     * @param activity    activity
     * @param permission  permission
     * @param requestCode requestCode
     */
    public static void request(final @NonNull Activity activity,
                               final @NonNull String permission,
                               final int requestCode) {
        String[] permissions = {permission};
        request(activity,
                permissions,
                requestCode);
    }

    /**
     * 是否拥有打电话权限.
     *
     * @param context context
     * @return 判断结果
     */
    public static boolean hasCallPhonePerm(@NonNull Context context) {
        return has(context, Manifest.permission.CALL_PHONE);
    }

    /**
     * 是否有申请打电话权限的原因.
     *
     * @param activity activity
     * @return 判断结果
     */
    public static boolean hasCallPhonePermReason(@NonNull Activity activity) {
        return hasReason(activity,
                Manifest.permission.CALL_PHONE);
    }

    /**
     * 请求打电话权限.
     *
     * @param activity    activity
     * @param requestCode requestCode
     */
    public static void requestCallPhonePerm(final @NonNull Activity activity,
                                            final int requestCode) {
        request(activity,
                Manifest.permission.CALL_PHONE,
                requestCode);
    }
}