package em.sang.com.allrecycleview.holder;

import android.content.Context;
import android.view.View;

import java.util.List;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/11/8 11:58
 */
public class HeardHolder<T> extends CustomPeakHolder<T> {



    public HeardHolder(View itemView) {
        super(itemView);
    }

    public HeardHolder(Context context, int itemID, T data) {
        super(context, itemID, data);
    }

    public HeardHolder(Context context, List lists, int itemID) {
        super(context, lists, itemID);
    }




}
