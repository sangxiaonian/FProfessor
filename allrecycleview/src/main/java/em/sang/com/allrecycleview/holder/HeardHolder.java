package em.sang.com.allrecycleview.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/11/8 11:58
 */
public class HeardHolder<T> extends CustomPeakHolder {



    public HeardHolder(View itemView) {
        super(itemView);
    }

    public HeardHolder(Context context, int itemID, Object data) {
        super(context, itemID, data);
    }

    public HeardHolder(Context context, List lists, int itemID) {
        super(context, lists, itemID);
    }


    /**
     * 设置根目录的margin值
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setMagrin(float left,float top,float right,float bottom){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins((int) left,(int)top,(int)right,(int)bottom);
        itemView.setLayoutParams(params);
    }

}
