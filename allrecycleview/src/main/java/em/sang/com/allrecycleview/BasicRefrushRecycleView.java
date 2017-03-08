package em.sang.com.allrecycleview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import em.sang.com.allrecycleview.inter.RefrushListener;
import em.sang.com.allrecycleview.view.RefrushLinearLayout;
import em.sang.com.allrecycleview.view.ShapeView;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/12/1 14:42
 */
public abstract class BasicRefrushRecycleView extends RecyclerView {
    /**
     * 正在加载
     */
    public static final int LOADING = 0;//正在加载
    /**
     * 加载成功
     */
    public static final int LOAD_SUCCESS = 1;//加载成功

    /**
     * 加载失败
     */
    public static final int LOAD_FAIL = 2;//加载失败
    /**
     * 下拉加载
     */
    public static final int LOAD_OVER = 4;//下拉加载
    /**
     * 松手刷新
     */
    public static final int LOAD_BEFOR = 5;//松手刷新


    /**
     * 正在加载
     */
    public static final int LOADING_DOWN = 6;//正在加载
    /**
     * 加载成功
     */
    public static final int LOAD_DOWN_SUCCESS = 7;//加载成功

    /**
     * 加载失败
     */
    public static final int LOAD_DOWN_FAIL = 8;//加载失败
    /**
     * 上拉加载
     */
    public static final int LOAD_DOWN_OVER = 9;//下拉加载
    /**
     * 松手刷新
     */
    public static final int LOAD_DOWN_BEFOR = 10;//松手刷新


    public boolean hasTop, hasBoom;

    public RefrushLinearLayout topView, boomView;
    public int mearchTop, mearchBoom;


    public float downY;


    public boolean isNoTouch = true;
    protected boolean isLoadMore;
    protected ValueAnimator upAnimator, downAnimator;


    /**
     * 滑动距离倍数
     */
    public final int muli = 3;
    public RefrushListener listener;
    public float min = 0;
    public int upstate = -1;
    public int downstate = -1;

    protected int nextUpState = -1;
    protected int nextDownState = -1;

    public BasicRefrushRecycleView(Context context) {
        super(context);
        initView(context, null, 0);

    }

    public BasicRefrushRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0);
    }

    public BasicRefrushRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs, defStyle);

    }

    protected void initView(Context context, @Nullable AttributeSet attrs, int defStyle) {}

    protected int getNextState(int refrush_state) {
        int load = refrush_state;
        switch (refrush_state) {
            case LOAD_DOWN_BEFOR:
                if (isNoTouch) {
                    load = LOADING_DOWN;
                }
                break;
            case LOAD_BEFOR:
                if (isNoTouch) {
                    load = LOADING;
                }
                break;
            case LOADING:
            case LOADING_DOWN:
            case LOAD_OVER:
            case LOAD_DOWN_OVER:
                break;
            case LOAD_SUCCESS:
            case LOAD_FAIL:
                load = LOAD_OVER;
                break;
            case LOAD_DOWN_SUCCESS:
            case LOAD_DOWN_FAIL:
                load = LOAD_DOWN_OVER;
                break;
        }
        return load;
    }

    /**
     * 是否是上拉加载更多
     *
     * @return
     */
    public boolean isLoadMore() {
        return isLoadMore;
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
                isNoTouch = false;
                    if (isFirst()) {
                        clearUpAnimotion();
                        setUpHeightVisible(gap);
                    } else if (isLast()) {
                        clearDownAnimotion();
                        setDownHeightVisible(-gap);
                    }

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                downY = -1;
                isNoTouch = true;
                    if (isFirst()) {
                        startUpAnimotion(upstate);
                    } else if (isLast()) {
                        startDownAnimotion(downstate);
                    }
                break;
            default:
                downY = -1;
                isNoTouch = true;
                    if (isFirst()) {
                        startUpAnimotion(upstate);
                    } else if (isLast()) {
                        startDownAnimotion(downstate);
                    }
                break;
        }

        return super.onTouchEvent(e);
    }

    /**
     * 设置下拉刷新布局高度改变
     *
     * @param gap 增加的高度
     */
    protected void setUpHeightVisible(float gap) {
        LinearLayout view = topView;
        int height = (int) (getHeightVisiable(view) + gap);
        float minheight;

        if (isUpChangeStateByHeight()) {
            minheight = min;
        } else {
            minheight = mearchTop;
        }
        if (height > minheight) {
            scrollToPosition(0);

        }
        height = height >= minheight ? height : (int) minheight;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);
        //根据高度更新状态
        if (isUpChangeStateByHeight()) {
            upRefushState(upChangeStateByHeight(height));
        }
    }

    /**
     * 下拉刷新时候,根据条目高度来改变状态,主要用于下拉刷新和松手刷新时候调用
     *
     * @param height 当前条目高度
     * @return 返回更改的状态
     */
    protected abstract int upChangeStateByHeight(int height);

    /**
     * 下拉刷新动画
     *
     * @param upState 当前状态
     */
    protected void startUpAnimotion(int upState) {
        final View view = topView;
        float stand = getUpHeightByState(upState);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int height = layoutParams.height;
        if (height == stand) {
            return;
        }
        final int load = getNextState(upState);

        upAnimator = ValueAnimator.ofFloat(height, stand);
        upAnimator.setInterpolator(new DecelerateInterpolator(1));
        upAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                setViewHeight(topView, value);
            }
        });
        upAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (load != upstate) {
                    upRefushState(load);
                }

            }
        });

        if (isNoTouch) {
            if (upState == LOAD_SUCCESS || upState == LOAD_FAIL) {
                upAnimator.setDuration(1000);
                upAnimator.start();
            } else {
                upAnimator.setDuration(200);
                upAnimator.start();
            }
        }

    }

    protected float getUpHeightByState(int upState) {
        float stand;
        switch (upState) {
            case LOAD_BEFOR:
            case LOADING:
                stand = mearchTop;
                break;
            default:
                stand = min;
                break;
        }
        return stand;
    }

    /**
     * 上拉加载动画
     *
     * @param downState 当前状态
     */
    protected void startDownAnimotion(final int downState) {
        final View view = boomView;
        float stand = getDownHeightByState(downState);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int height = layoutParams.height;
        if (height == stand) {
            return;
        }
        final int load = getNextState(downState);
        downAnimator = ValueAnimator.ofFloat(height, stand);
        downAnimator.setInterpolator(new DecelerateInterpolator(1));
        downAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                setViewHeight(boomView, value);
            }
        });
        downAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (load != downstate) {
                    downRefrushState(load);
                }

            }
        });

        if (isNoTouch) {
            if (downstate == LOAD_SUCCESS || downstate == LOAD_FAIL) {
                downAnimator.setDuration(1000);
                downAnimator.start();
            } else {
                downAnimator.setDuration(200);
                downAnimator.start();
            }
        }
    }

    protected float getDownHeightByState(int upState) {
        float stand;
        switch (upState) {
            case LOADING_DOWN:
            case LOAD_DOWN_BEFOR:
                stand = mearchBoom;
                break;
            default:
                stand = min;
                break;
        }
        return stand;
    }

    /**
     * 设置上拉加载布局高度改变
     *
     * @param gap 增加的高度
     */
    protected void setDownHeightVisible(float gap) {
        LinearLayout view = boomView;
        int height = (int) (getHeightVisiable(view) + gap);
        float minheight;

        if (isDownChangeStateByHeight()) {
            minheight = min;
        } else {
            minheight = mearchBoom;
        }

        if (height > minheight) {
            scrollToPosition(getAdapter().getItemCount() - 1);
        }
        height = height >= minheight ? height : (int) minheight;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);
        //根据高度更新状态
        if (isDownChangeStateByHeight()) {
            downRefrushState(downChangeStateByHeight(height));
        }
    }

    /**
     * 上拉加载时候,用来根据高度判断是处于上拉加载还是松手刷新
     *
     * @param height 条目高度
     * @return 状态
     */
    protected abstract int downChangeStateByHeight(int height);


    /**
     * 设置正在加载
     */
    public void setLoading() {
        clearViewAnimotion();
        setViewHeight(topView, mearchTop + 1);
        upRefushState(LOAD_BEFOR);
    }


    /**
     * 获取当前控件高度
     *
     * @return
     * @pram view
     */
    public int getHeightVisiable(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        return layoutParams.height;
    }


    public void clearViewAnimotion() {
        clearUpAnimotion();
        clearDownAnimotion();
    }

    public void clearUpAnimotion() {
        if (upAnimator != null && upAnimator.isRunning()) {
            upAnimator.removeAllUpdateListeners();
            upAnimator.pause();
            upAnimator.cancel();

        }
    }

    public void clearDownAnimotion() {
        if (downAnimator != null && downAnimator.isRunning()) {
            downAnimator.removeAllUpdateListeners();
            downAnimator.pause();
            downAnimator.cancel();

        }
    }


    /**
     * 更改View的高度
     *
     * @param view  要更改的条目
     * @param value 高度值
     */
    public void setViewHeight(View view, float value) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) value;
        view.setLayoutParams(layoutParams);
    }


    public boolean isFirst() {
        LayoutManager manager = getLayoutManager();
        if (manager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = ((LinearLayoutManager) manager);
            int position = linearLayoutManager.findFirstVisibleItemPosition();
            if (position == -1) {
                position = 0;
            }
            return 0 == position && hasTop;
        } else if (manager instanceof StaggeredGridLayoutManager) {
            int position = ((StaggeredGridLayoutManager) manager).findFirstVisibleItemPositions(new int[2])[0];
            if (position <= 0) {
                position = 0;
            }
            return 0 == position && hasTop;
        } else {
            return false;
        }
    }

    public boolean isLast() {
        int itemCount = getAdapter().getItemCount() - 1;
        LayoutManager manager = getLayoutManager();
        if (manager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = ((LinearLayoutManager) manager);
            return itemCount == linearLayoutManager.findLastVisibleItemPosition() && hasBoom;
        } else if (manager instanceof StaggeredGridLayoutManager) {
            return itemCount == ((StaggeredGridLayoutManager) manager).findLastVisibleItemPositions(new int[2])[0] && hasBoom;

        } else {
            return false;
        }
    }


    @Override
    public void setAdapter(Adapter adapter) {
        addUpDataItem(adapter);
        super.setAdapter(adapter);
    }


    /**
     * 上拉加载更多
     *
     * @param refrush_state
     */
    protected void downRefrushState(int refrush_state) {
        if (this.downstate != refrush_state) {

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
                        isLoadMore = true;
                        listener.onLoadDowning();
                    }
                    upRefushState(LOAD_OVER);
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
                    topView.setTvMsg("加载异常");
                    boomView.setTvMsg("加载异常");
                    break;
            }
            this.downstate = refrush_state;
            startDownAnimotion(downstate);
        }
    }

    /**
     * 下拉状态刷新
     *
     * @param refrush_state
     */
    protected void upRefushState(int refrush_state) {
        if (this.upstate != refrush_state) {
            switch (refrush_state) {
                case LOAD_OVER://4
                    topView.setTvMsg("下拉刷新数据");
                    topView.upState(ShapeView.LOAD_OVER);
                    break;
                case LOAD_BEFOR://5
                    topView.upState(ShapeView.LOAD_BEFOR);
                    topView.setTvMsg("松手刷新数据");
                    break;
                case LOADING://0

                    topView.upState(ShapeView.LOADING);
                    topView.setTvMsg("正在加载数据");
                    if (listener != null) {
                        isLoadMore = false;
                        listener.onLoading();
                    }
                    downRefrushState(LOAD_DOWN_OVER);
                    break;
                case LOAD_FAIL://2
                    topView.upState(ShapeView.LOAD_FAIL);
                    topView.setTvMsg("加载失败");
                    break;
                case LOAD_SUCCESS://1
                    topView.upState(ShapeView.LOAD_SUCCESS);
                    topView.setTvMsg("加载成功!");
                    break;
                default:
                    topView.setTvMsg("加载异常");
                    boomView.setTvMsg("加载异常");
                    break;
            }
            this.upstate = refrush_state;
            startUpAnimotion(upstate);
        }
    }


    public void setFlag(String flag) {
        if (topView != null) {
            topView.setFlag(flag);
        }


    }

    public void setDownFlag(String flag) {
        if (boomView != null) {
            boomView.setFlag(flag);
        }
    }


    /**
     * 将刷新条目添加到adapter上
     *
     * @param adapter
     */
    public abstract void addUpDataItem(Adapter adapter);

    /**
     * 向上拖动，改变数据
     *
     * @return
     */
    public abstract boolean canDrag();

    public View getTopView() {
        return topView;
    }

    public View getBoomView() {
        return boomView;
    }

    public void setHasTop(boolean hasTop) {
        this.hasTop = hasTop;
    }

    public void setHasBoom(boolean hasBoom) {
        this.hasBoom = hasBoom;
    }

    public boolean getHasTop() {
        return hasTop;
    }

    public boolean getHasBoom() {
        return hasBoom;
    }


    /**
     * 下拉刷新时候,是否能够随着高度改变状态
     *
     * @return true 可以
     */
    protected boolean isUpChangeStateByHeight() {
        return upstate == LOAD_OVER || upstate == LOAD_BEFOR || upstate == -1;
    }

    /**
     * 上拉加载时候,是否能够随着高度改变状态
     *
     * @return true 可以
     */
    protected boolean isDownChangeStateByHeight() {
        return downstate == LOAD_DOWN_OVER || downstate == LOAD_DOWN_BEFOR || downstate == -1;
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
    public abstract void loadSuccess();


    /**
     * 刷新失败后调用
     */
    public abstract void loadFail();


    public View getEndView() {
        if (isFirst()){
            return topView;
        }else if (isLast()){
            return boomView;
        }
        return null;
    }
}
