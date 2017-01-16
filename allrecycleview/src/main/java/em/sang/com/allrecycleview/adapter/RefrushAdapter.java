package em.sang.com.allrecycleview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import em.sang.com.allrecycleview.PullRecycleView;
import em.sang.com.allrecycleview.holder.SimpleHolder;
import em.sang.com.allrecycleview.inter.DefaultAdapterViewLisenter;


/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/11/7 16:43
 */
public class RefrushAdapter<T> extends DefaultAdapter<T> {


    public RefrushAdapter(Context context, List lists, int itemID, DefaultAdapterViewLisenter lisenter) {
        super(context, lists, itemID, lisenter);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (recyclerView instanceof PullRecycleView){
            PullRecycleView re = (PullRecycleView) recyclerView;
            View topView = re.getTopView();
            if (re.getHasTop()) {
                addTop(new SimpleHolder(topView));
            }
            if (re.getHasBoom()){
                addBoom(new SimpleHolder(re.getBoomView()));
            }

        }
        super.onAttachedToRecyclerView(recyclerView);
    }



}
