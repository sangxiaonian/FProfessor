package finance.com.fp.ui.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import em.sang.com.allrecycleview.holder.HeardHolder;
import em.sang.com.allrecycleview.listener.OnToolsItemClickListener;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/16 17:24
 */
public class BasicHolder<T> extends HeardHolder<T> {

    public OnToolsItemClickListener listener;

    public BasicHolder(View itemView) {
        super(itemView);
    }

    public BasicHolder(Context context, int itemID, T data) {
        super(context, itemID, data);
    }

    public BasicHolder(Context context, List lists, int itemID) {
        super(context, lists, itemID);
    }

    public void setOnToolsItemClickListener(OnToolsItemClickListener listener){
        this.listener=listener;
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
