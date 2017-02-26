package em.sang.com.allrecycleview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import em.sang.com.allrecycleview.adapter.BasicAdapter;
import em.sang.com.allrecycleview.holder.SimpleHolder;
import em.sang.com.allrecycleview.utils.Apputils;
import em.sang.com.allrecycleview.view.RefrushLinearLayout;


/**
 * Description：
 * <p>
 * Author：桑小年
 * Data：2016/12/1 14:42
 */
public class PullDownRecycleView extends BasicPullRecycleView {


    public PullDownRecycleView(Context context) {
        super(context);
        initView(context, null, 0);

    }

    public PullDownRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0);

    }

    public PullDownRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs, defStyle);

    }

    protected void initView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        downY = -1;
        min = 1f;
        boomView = new RefrushLinearLayout(context);
        mearchBoom = Apputils.getWidthAndHeight(boomView)[1];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mearchBoom);
        params.height = (int) min;
        boomView.setLayoutParams(params);
        upRefrush_state(LOAD_DOWN_BEFOR);
        hasBoom = true;
    }

    /**
     * 刷新成功后调用
     */
    public void loadSuccess() {
        upRefrush_state(LOAD_DOWN_SUCCESS);
    }

    /**
     * 刷新失败后调用
     */
    public void loadFail() {
        upRefrush_state(LOAD_DOWN_SUCCESS);
    }



    /**
     * 向上拖动，改变数据
     *
     * @return
     */
    public boolean canDrag() {
        return isLast();
    }


    @Override
    public int changeStateByHeight(int height) {
        int result;
        float stand = getEndHeight();
        if (height < stand) {
            result = LOAD_DOWN_OVER;
        } else {
            result = LOAD_DOWN_BEFOR;
        }
        return result;
    }

    //获取刷新的高度
    @Override
    public float getEndHeight() {
        return mearchBoom;
    }


    @Override
    public LinearLayout getEndView() {
        LinearLayout view = boomView;
        return view;
    }





    /**
     * 根据状态获取动画执行最终高度
     *
     * @param refrush_state
     * @return
     */
    public float getStandHeightByStated(int refrush_state) {
        float stand;
        switch (refrush_state) {
            case LOAD_DOWN_BEFOR:
            case LOADING_DOWN:
                stand = getEndHeight();
                break;
            default:
                stand = min;
                break;
        }
        return stand;
    }


    @Override
    public void addUpDataItem(Adapter adapter) {
        if (adapter instanceof BasicAdapter) {
            BasicAdapter basicAdapter = (BasicAdapter) adapter;
            if (getHasBoom()) {
                basicAdapter.addBoom(new SimpleHolder(boomView));
            }
        }
    }
}
