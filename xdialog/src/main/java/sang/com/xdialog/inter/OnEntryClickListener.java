package sang.com.xdialog.inter;

import android.app.Dialog;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/2/9 13:45
 */
public interface OnEntryClickListener<T> {
    void onClick(Dialog dialog,int which,T data);
}
