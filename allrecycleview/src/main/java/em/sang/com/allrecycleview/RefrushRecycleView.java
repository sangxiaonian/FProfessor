package em.sang.com.allrecycleview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sang.viewfractory.utils.Apputils;
import com.sang.viewfractory.view.RefrushLinearLayout;

import em.sang.com.allrecycleview.adapter.BasicAdapter;
import em.sang.com.allrecycleview.holder.SimpleHolder;


/**
 * Description：上拉和下拉都需要拖动才能显示
 * <p>
 * Author：桑小年
 * Data：2016/12/1 14:42
 */
public class RefrushRecycleView extends BasicRefrushRecycleView {


    public RefrushRecycleView(Context context) {
        super(context);

    }

    public RefrushRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public RefrushRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    @Override
    protected void initView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super.initView(context, attrs, defStyle);
        downY = -1;
        topView = new RefrushLinearLayout(context);
        boomView = new RefrushLinearLayout(context);

        mearchTop = Apputils.getWidthAndHeight(topView)[1];
        mearchBoom = Apputils.getWidthAndHeight(boomView)[1];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.height = (int) min;
        topView.setLayoutParams(params);

        LinearLayout.LayoutParams boom = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        boom.height = (int) min;
        boomView.setLayoutParams(boom);
        hasTop = true;
        hasBoom = false;


    }


    @Override
    protected int upChangeStateByHeight(int height) {
        int state;
        if (height > mearchTop) {
            state = LOAD_BEFOR;
        } else {
            state = LOAD_OVER;

        }
        return state;
    }

    @Override
    protected int downChangeStateByHeight(int height) {
        int state;
        if (height > mearchBoom) {
            state = LOAD_DOWN_BEFOR;
        } else {
            state = LOAD_DOWN_OVER;

        }
        return state;
    }




    @Override
    public void addUpDataItem(Adapter adapter) {
        if (adapter instanceof BasicAdapter) {
            BasicAdapter basicAdapter = (BasicAdapter) adapter;
            if (getHasBoom()) {
                basicAdapter.addBoom(new SimpleHolder(boomView));
            }
            if (getHasTop()) {
                basicAdapter.addTop(new SimpleHolder(topView));
            }
        }
    }

    @Override
    public boolean canDrag() {
        return isLast() || isFirst();
    }


}
