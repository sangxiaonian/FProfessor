package em.sang.com.allrecycleview.holder;

import android.view.View;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/11/8 11:58
 */
public class FootHolder<T> extends CustomPeakHolder<T> {

    public View itemView;
    public T data;

    public FootHolder(T data, View itemView) {
        super(itemView);
        this.data = data;
        this.itemView=itemView;
    }


}
