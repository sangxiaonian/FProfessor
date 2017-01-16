package em.sang.com.allrecycleview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import em.sang.com.allrecycleview.inter.RefrushListener;
import em.sang.com.allrecycleview.utils.Apputils;
import em.sang.com.allrecycleview.utils.JLog;
import em.sang.com.allrecycleview.view.RefrushLinearLayout;
import em.sang.com.allrecycleview.view.ShapeView;

import static em.sang.com.allrecycleview.PullRecycleView.LOADING_DOWN;
import static em.sang.com.allrecycleview.PullRecycleView.LOAD_BEFOR_DOWN;
import static em.sang.com.allrecycleview.PullRecycleView.LOAD_OVER_DOWN;

/**
 * Description：
 * <p>
 * Author：桑小年
 * Data：2016/12/1 14:42
 */
public class PullUPRecycleView extends BasicRecycleView {


    private boolean hasTop,hasBoom;
    private float downY;

    private RefrushLinearLayout topView, boomView;
    private int mearchTop, mearchBoom;
    private int refrush_state = 0;
    private RefrushListener listener;


    /**
     * 正在加载
     */
    public static final int LOADING = 0;
    /**
     * 加载成功
     */
    public static final int LOAD_SUCCESS = 1;//加载成功

    /**
     * 加载失败
     */
    public static final int LOAD_FAIL = 2;//加载失败
    /**
     * 正常状态
     */
    public static final int LOAD_OVER = 4;//正常状态
    /**
     * 即将开始加载
     */
    public static final int LOAD_BEFOR = 5;//即将开始加载


    /**
     * 滑动距离倍数
     */
    private final int muli = 3;

    private boolean isMove;
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
        boomView = new RefrushLinearLayout(context);
        boomView.setGravity(Gravity.CENTER);

        mearchTop = Apputils.getWidthAndHeight(topView)[1];
        mearchBoom = Apputils.getWidthAndHeight(boomView)[1];

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.height = 0;
        topView.setLayoutParams(params);
        boomView.setLayoutParams(params);
        refrush_state = LOAD_OVER;
        hasTop=true;
        hasBoom=true;


    }

    /**
     * 设置刷新监听
     *
     * @param listener
     */
    public void setRefrushListener(RefrushListener listener) {
        this.listener = listener;
    }

    /**
     * 刷新成功后调用
     */
    public void loadSuccess() {
        upRefrush_state(LOAD_SUCCESS);
    }

    /**
     * 刷新失败后调用
     */
    public void loadFail() {
        upRefrush_state(LOAD_SUCCESS);
    }


    public View getTopView() {
        return topView;
    }

    public View getBoomView() {
        return boomView;
    }

    public void setHasTop(boolean hasTop){
        this.hasTop=hasTop;
    }
    public void setHasBoom(boolean hasBoom){
        this.hasBoom=hasBoom;
    }

    public boolean getHasTop(){
        return hasTop;
    }

    public boolean getHasBoom() {
        return hasBoom;
    }

    public void setTopView(View view) {

        topView.addView(view);
        mearchTop = Apputils.getWidthAndHeight(topView)[1];
    }

    public void setBoomView(View view) {

        boomView.addView(view);
        mearchBoom = Apputils.getWidthAndHeight(boomView)[1];
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        if (downY == -1) {
            downY = e.getRawY();
        }

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float gap = (e.getRawY() - downY) / muli;
                downY = e.getRawY();
                isMove = false;

                if (isShowUp() || getHeightVisiable(topView) >= 0) {
                    setHightVisiable(topView, gap);
                } else if (isShowDown() || getHeightVisiable(boomView) >= 0) {
                    setHightVisiable(boomView, -gap);
                }

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                downY = -1;
                isMove = true;
                if (isFirst()) {
                    moveToChildHight(refrush_state);
                } else if (isLast()) {
                    moveToChildHight(refrush_state);
                }
                break;
            default:

                break;
        }


        return super.onTouchEvent(e);
    }


    /**
     * 获取当前控件高度
     *
     * @param view
     * @return
     */
    private int getHeightVisiable(LinearLayout view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        return layoutParams.height;
    }

    /**
     * 设置当前控件高度
     *
     * @param view
     * @param gap
     */
    private void setHightVisiable(LinearLayout view, float gap) {
        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }
        int height = (int) (getHeightVisiable(view) + gap);
        height = height >= 0 ? height : 0;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);
        if (isCanMove() && height != 0 && isFirst()) {
            scrollToPosition(0);
        } else if (height != 0 && isLast() && gap < 0) {
            scrollToPosition(getAdapter().getItemCount() - 1);
        }
        JLog.i("-----------------"+height+"---------------------");
        //根据高度更新状态
        if (isCanMove()) {

            upRefrush_state(changeStateByHeight(height));
        }

    }

    private int changeStateByHeight(int height) {
        int result;
        float stand = getEndHeight();

        if ((isFirst()&&refrush_state==LOADING) ){
            return refrush_state;
        }
        if (isFirst()) {
            if (height < stand) {
                result = LOAD_OVER;
            } else {

                result = LOAD_BEFOR;
            }
        }   else {
            result = refrush_state;
        }

        return result;
    }

    //获取刷新的高度
    private float getEndHeight() {
        int stand = 0;
        if (isFirst()) {
            stand = mearchTop;
        } else if (isLast()) {
            stand = mearchBoom;
        }
        return stand;
    }

    //获取刷新的高度
    private LinearLayout getEndView() {
        LinearLayout view = topView;
        if (isFirst()) {
            view = topView;
        } else if (isLast()) {
            view = boomView;
        }
        return view;
    }


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
            case LOADING_DOWN:
                boomView.upState(ShapeView.LOADING);
                boomView.setTvMsg("正在加载数据");
                break;
            case LOAD_OVER_DOWN:
                boomView.upState(ShapeView.LOAD_BEFOR);
                boomView.setTvMsg("上拉加载数据");

                break;
            case LOAD_BEFOR_DOWN:
                boomView.upState(ShapeView.LOAD_OVER);
                boomView.setTvMsg("松手刷新数据");

                break;


            default:
                topView.setTvMsg("加载异常");
                boomView.setTvMsg("加载异常");

                break;
        }


    }


    /**
     * 是否正在下拉
     *
     * @return
     */
    private boolean isShowUp() {
        return topView.getChildCount() > 0 && isFirst() && state == RecyclerView.SCROLL_STATE_DRAGGING;
    }

    /**
     * 是否正在上拉
     *
     * @return
     */
    private boolean isShowDown() {
        return boomView.getChildCount() > 0 && isLast() && state == RecyclerView.SCROLL_STATE_DRAGGING;
    }

    private boolean isCanMove() {
        return refrush_state == LOAD_OVER || refrush_state == LOAD_BEFOR || refrush_state == LOAD_OVER_DOWN
                || refrush_state == LOAD_BEFOR_DOWN||(isFirst()&&refrush_state>5)||(isLast()&&refrush_state<6);
    }


    private ValueAnimator animator;

    private void moveToChildHight(final int refrush_state) {
        View view = getEndView();
        float stand;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int height = layoutParams.height;
        switch (refrush_state) {
            case LOAD_BEFOR:
            case LOADING:
            case LOADING_DOWN:
            case LOAD_BEFOR_DOWN:
                stand = getEndHeight();
                break;
            default:
                stand = 0;
                break;
        }

        JLog.i(refrush_state + "===");

        if (animator != null && animator.isRunning()) {
            animator.removeAllListeners();
            animator.cancel();
        }
        if (height == stand) {
            return;
        }
        animator = ValueAnimator.ofFloat(height, stand);
        final View finalView = view;
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = finalView.getLayoutParams();
                layoutParams.height = (int) value;
                finalView.setLayoutParams(layoutParams);

            }
        });

        if (refrush_state == LOAD_BEFOR || refrush_state == LOAD_BEFOR_DOWN) {
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);


                    if (isFirst()) {
                        upRefrush_state(LOADING);
                    } else if (isLast()) {
                        upRefrush_state(LOADING_DOWN);
                    }

                }
            });

        } else if (stand == 0) {
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if (getHeightVisiable(getEndView()) == 0) {
                        if (isFirst()) {
                            upRefrush_state(LOAD_OVER);
                        } else if (isLast()) {
                            upRefrush_state(LOAD_OVER_DOWN);

                        }
                    }
//
                }
            });
        }
        animator.setDuration(200);
        if (isMove) {
            if (isCanMove() || refrush_state == LOADING || refrush_state == LOADING_DOWN) {
                animator.start();
            } else {
//                if ()
                animator.setStartDelay(200);
                animator.start();
            }
        }
    }


}
