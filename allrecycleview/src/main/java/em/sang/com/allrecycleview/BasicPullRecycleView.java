package em.sang.com.allrecycleview;

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
import android.widget.LinearLayout;

import em.sang.com.allrecycleview.inter.RefrushListener;
import em.sang.com.allrecycleview.view.RefrushLinearLayout;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/12/1 14:42
 */
public abstract class BasicPullRecycleView extends RecyclerView {
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


    public boolean isNoTouch=true;

    /**
     * 滑动状态
     */
    public int state;
    /**
     * 滑动距离倍数
     */
    public final int muli = 3;
    public RefrushListener listener;
    public float min = 0;
    public int refrush_state = -1;
    public ValueAnimator animator;

    public BasicPullRecycleView(Context context) {
        super(context);

    }

    public BasicPullRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public BasicPullRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }



    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (downY == -1) {
            downY = e.getRawY();
        }
        clearViewAnimotion();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float gap = (e.getRawY() - downY) / muli;
                downY = e.getRawY();
                isNoTouch = false;

                if (canDrag()) {
                    if (isFirst()) {
                        setHightVisiable(gap);
                    } else if (!isFirst()&&isLast()) {
                        setHightVisiable(-gap);
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                downY = -1;
                isNoTouch = true;
                if (canDrag()) {
                    moveToChildHight(refrush_state);
                }
                break;
            default:

                break;
        }

        return super.onTouchEvent(e);
    }

    protected abstract void moveToChildHight(int refrush_state);

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


    protected void clearViewAnimotion(){
        if (animator != null && animator.isRunning()) {
            animator.cancel();
            animator=null;
        }
    }

    /**
     * 设置当前控件高度
     *
     * @param gap
     */
    public void setHightVisiable(float gap) {
        LinearLayout view = getEndView();
        int height = (int) (getHeightVisiable(view) + gap);
        float minheight;

        if (isChangStateByHeight()) {
            minheight = min;
        } else {
            minheight = getEndHeight();
        }
        if (height > minheight) {
            if (isFirst()) {
                scrollToPosition(0);
            } else if (isLast()) {
                scrollToPosition(getAdapter().getItemCount() - 1);

            }
        }
        height = height >= minheight ? height : (int) minheight;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);
        //根据高度更新状态
        if (isChangStateByHeight()) {
            upRefrush_state(changeStateByHeight(height));
        }

    }

    /**
     * 根据状态获取动画执行最终高度
     *
     * @param refrush_state
     * @return
     */
    public abstract float getStandHeightByStated(int refrush_state);

    /**
     * 根据高度来更状态
     *
     * @param height 加载条目的高度
     * @return 返回当前加载状态
     */
    public abstract int changeStateByHeight(int height);

    /**
     * 获取刷新条目的高度
     *
     * @return
     */
    public abstract float getEndHeight();

    /**
     * 获取刷新条目
     *
     * @return
     */
    public abstract LinearLayout getEndView();


    /**
     * 条目是否会根据高度变化
     *
     * @return
     */
    public abstract boolean isChangStateByHeight();

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
            int position = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
              position = linearLayoutManager.findFirstVisibleItemPosition();
            return 0 == position;
        } else if (manager instanceof StaggeredGridLayoutManager) {
            return 0 == ((StaggeredGridLayoutManager) manager).findFirstVisibleItemPositions(new int[2])[0];
        } else {
            return false;
        }
    }

    public boolean isLast() {
        int itemCount = getAdapter().getItemCount() - 1;
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
     * 更新状态
     *
     * @param refrush_state
     */
    public abstract void upRefrush_state(int refrush_state);

    /**
     * 刷新失败后调用
     */
    public abstract void loadFail();


    @Override
    public void setAdapter(Adapter adapter) {
        addUpDataItem(adapter);
        super.setAdapter(adapter);
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
     * 正在加载
     */
    public void setLoading(){

    }




}
