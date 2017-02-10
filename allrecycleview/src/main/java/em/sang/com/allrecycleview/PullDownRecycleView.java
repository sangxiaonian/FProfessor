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


    public void upRefrush_state(int refrush_state) {
        if (this.refrush_state == refrush_state) {
            return;
        }
        this.refrush_state = refrush_state;
        moveToChildHight(refrush_state);
        switch (refrush_state) {
            case LOAD_DOWN_OVER:
                boomView.upState(ShapeView.LOAD_BEFOR);
                boomView.setTvMsg("上拉加载数据");
                break;
            case LOAD_DOWN_BEFOR:
                boomView.setTvMsg("松手刷新数据");
                boomView.upState(ShapeView.LOAD_OVER);
                break;
            case LOADING_DOWN:
                boomView.upState(ShapeView.LOADING);
                boomView.setTvMsg("正在加载数据");
                if (listener != null) {
                    listener.onLoading();
                }

                break;
            case LOAD_DOWN_FAIL:
                boomView.upState(ShapeView.LOAD_FAIL);
                boomView.setTvMsg("加载失败");
                break;
            case LOAD_DOWN_SUCCESS:
                boomView.upState(ShapeView.LOAD_SUCCESS);
                boomView.setTvMsg("加载成功!");
                break;
            default:
                boomView.setTvMsg("加载异常");
                boomView.setTvMsg("加载异常");
                break;
        }


    }


    @Override
    public boolean isChangStateByHeight() {
        return refrush_state == LOAD_DOWN_OVER || refrush_state == LOAD_DOWN_BEFOR;
    }


    @Override
    public void moveToChildHight(final int refrush_state) {
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
                    case LOAD_DOWN_BEFOR:
                        load = LOADING_DOWN;
                        break;
                    default:
                        if (stand == min) {
                            load = LOAD_DOWN_OVER;
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
            if (isChangStateByHeight() || refrush_state == LOADING_DOWN) {
                animator.start();
            } else {
                animator.setStartDelay(200);
                animator.start();
            }
        }

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
