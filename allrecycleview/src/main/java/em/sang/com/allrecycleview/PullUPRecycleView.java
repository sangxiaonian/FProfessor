package em.sang.com.allrecycleview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import em.sang.com.allrecycleview.adapter.BasicAdapter;
import em.sang.com.allrecycleview.holder.SimpleHolder;
import em.sang.com.allrecycleview.utils.Apputils;
import em.sang.com.allrecycleview.view.RefrushLinearLayout;
import em.sang.com.allrecycleview.view.ShapeView;


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
        upRefrush_state(LOAD_OVER);
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
        upRefrush_state(LOAD_SUCCESS);
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
        int stand = (int) min;
        stand = mearchTop;
        return stand;
    }

    //获取刷新的高度
    @Override
    public LinearLayout getEndView() {
        LinearLayout view = topView;

        return view;
    }


    @Override
    public void upRefrush_state(int refrush_state) {
        if (this.refrush_state == refrush_state) {
            return;
        }
        this.refrush_state = refrush_state;
        moveToChildHight(refrush_state);
        switch (refrush_state) {
            case LOAD_OVER:
                topView.setTvMsg("下拉刷新数据");
                topView.upState(ShapeView.LOAD_OVER);
                break;
            case LOAD_BEFOR:
                topView.upState(ShapeView.LOAD_BEFOR);
                topView.setTvMsg("松手刷新数据");

                break;
            case LOADING:
                topView.upState(ShapeView.LOADING);
                topView.setTvMsg("正在加载数据");
                if (listener != null) {
                    listener.onLoading();
                }

                break;
            case LOAD_FAIL:
                topView.upState(ShapeView.LOAD_FAIL);
                topView.setTvMsg("加载失败");


                break;
            case LOAD_SUCCESS:
                topView.upState(ShapeView.LOAD_SUCCESS);
                topView.setTvMsg("加载成功!");
                break;
            default:
                topView.setTvMsg("加载异常");

                break;
        }


    }

    @Override
    public void setLoading() {
        super.setLoading();
        setHightVisiable(mearchTop+1);
        upRefrush_state(LOADING);

    }



    @Override
    public boolean isChangStateByHeight() {
        return refrush_state == LOAD_OVER || refrush_state == LOAD_BEFOR;
    }


    protected void moveToChildHight(final int refrush_state) {
        final View view = getEndView();
        int height = getHeightVisiable(view);
        final float stand = getStandHeightByStated(refrush_state);
        if (animator != null && animator.isRunning()) {
            animator.removeAllListeners();
            animator.cancel();
        }
        if (height == stand) {
            return;
        }

        animator = ValueAnimator.ofFloat(height, stand);


        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                setViewHeight(view, value);
            }
        });


        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                int load;
                switch (refrush_state) {
                    case LOAD_BEFOR:
                        load = LOADING;
                        break;

                    default:
                        if (stand == min) {
                            load = LOAD_OVER;
                        } else {
                            load = refrush_state;
                        }
                        break;
                }
                upRefrush_state(load);
            }
        });

        animator.setDuration(200);
        if (isNoTouch) {
            if (isChangStateByHeight() || refrush_state == LOADING) {
                animator.start();
            } else {
                animator.setStartDelay(200);
                animator.start();
            }
        }

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
