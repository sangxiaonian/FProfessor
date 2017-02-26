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
public class PullUPRecycleView extends BasicPullRecycleView {



    public PullUPRecycleView(Context context) {
        super(context);
        initView(context, null, 0);

    }

    public PullUPRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0);

    }

    public PullUPRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs, defStyle);

    }

    protected void initView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        downY = -1;
        topView = new RefrushLinearLayout(context);
        min = 1f;
        mearchTop = Apputils.getWidthAndHeight(topView)[1];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.height = (int) min;
        topView.setLayoutParams(params);
        hasTop = true;


    }


    /**
     * 刷新成功后调用
     */
    @Override
    public void loadSuccess() {
        upRefrush_state(LOAD_SUCCESS);
    }

    /**
     * 刷新失败后调用
     */
    @Override
    public void loadFail() {
        upRefrush_state(LOAD_FAIL);
    }




    public int changeStateByHeight(int height) {
        int result;
        float stand = getEndHeight();
        if (height < stand) {
            result = LOAD_OVER;
        } else {

            result = LOAD_BEFOR;
        }
        return result;
    }


    @Override
    public float getEndHeight() {
        return mearchTop;
    }

    //获取刷新的高度
    @Override
    public LinearLayout getEndView() {
        return topView;
    }


    /**
     * 根据状态获取最终高度
     *
     * @param refrush_state
     * @return
     */
    public float getStandHeightByStated(int refrush_state) {
        float stand;
        switch (refrush_state) {
            case LOAD_BEFOR:
            case LOADING:
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
            if (getHasTop()) {
                basicAdapter.addTop(new SimpleHolder(topView));
            }

        }
    }

    @Override
    public boolean canDrag() {
        return isFirst();
    }
}
