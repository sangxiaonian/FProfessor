package em.sang.com.allrecycleview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

import em.sang.com.allrecycleview.inter.RefrushListener;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/12/1 14:42
 */
public class BasicRecycleView extends RecyclerView {

    /**
     * 是否是向上滑动,即为上拉加载
     */
    public boolean isUpScroll;
    /**
     * 滑动状态
     */
    public int state;


    private RefrushListener listener;


    public BasicRecycleView(Context context) {
        super(context);

    }

    public BasicRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public BasicRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }


    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        if (dy>0){
            isUpScroll=true;
        }else if (dy<0){
            isUpScroll=false;
        }
    }

    public boolean isFirst() {
        LayoutManager manager = getLayoutManager();
        if (manager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = ((LinearLayoutManager) manager);
            return 0 == linearLayoutManager.findFirstVisibleItemPosition();
        } else if (manager instanceof StaggeredGridLayoutManager) {
            return 0 == ((StaggeredGridLayoutManager) manager).findFirstVisibleItemPositions(new int[2])[0];
        } else {
            return false;
        }
    }

    public boolean isLast(){
        int itemCount = getAdapter().getItemCount()-1;
        LayoutManager manager = getLayoutManager();
        if (manager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = ((LinearLayoutManager) manager);
            return itemCount == linearLayoutManager.findLastVisibleItemPosition();
        } else if (manager instanceof StaggeredGridLayoutManager) {
            return itemCount == ((StaggeredGridLayoutManager) manager).findFirstCompletelyVisibleItemPositions(new int[2])[0];

        } else {
            return false;
        }
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        this.state=state;
    }
}
