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
import em.sang.com.allrecycleview.utils.JLog;
import em.sang.com.allrecycleview.view.RefrushLinearLayout;
import em.sang.com.allrecycleview.view.ShapeView;

/**
 * Description：
 * <p>
 * Author：桑小年
 * Data：2016/12/1 14:42
 */
public class PullRecycleView extends BasicPullRecycleView {


    public PullRecycleView(Context context) {
        super(context);
        initView(context, null, 0);

    }

    public PullRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0);

    }

    public PullRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs, defStyle);

    }

    @Override
    public float getStandHeightByStated(int refrush_state) {
        float stand = min;
        switch (refrush_state) {
            case LOAD_BEFOR:
            case LOADING:

                stand=mearchTop;
                break;
            case LOADING_DOWN:
            case LOAD_DOWN_BEFOR:
                if (isLast()){
                    stand = getEndHeight();
                }
                stand=mearchBoom;
                break;
            default:
                stand = min;
                break;
        }
        return stand;
    }

    protected void initView(Context context, @Nullable AttributeSet attrs, int defStyle) {
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
        hasTop=true;
        hasBoom=true;
    }


    @Override
    public boolean isLast() {

        if (refrush_state==LOADING){

            return false;
        }else {
            return super.isLast();
        }

    }

    @Override
    public boolean isFirst() {
        if (refrush_state==LOADING_DOWN){

            return false;
        }else {
            return super.isFirst();
        }

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
        upRefrush_state(LOAD_FAIL);
    }

    @Override
    public void addUpDataItem(Adapter adapter) {
        if (adapter instanceof BasicAdapter) {
            BasicAdapter basicAdapter = (BasicAdapter) adapter;
            if (getHasBoom()) {
                basicAdapter.addBoom(new SimpleHolder(boomView));
            }

            if (getHasTop()){
                basicAdapter.addTop(new SimpleHolder(topView));
            }
        }
    }

    @Override
    public void setLoading() {
        super.setLoading();
        setViewHeight(getEndView(),mearchTop);
        upRefrush_state(LOADING);
    }
    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
//        upRefrush_state(LOAD_OVER);
//        upRefrush_state(LOAD_DOWN_BEFOR);
    }

    @Override
    public boolean canDrag() {
        return isLast()||isFirst();
    }


    public int changeStateByHeight(int height) {
        int result =refrush_state;
        float stand = getEndHeight();

        if (isFirst()) {
            if (height <= stand) {
                result = LOAD_OVER;
            } else {
                result = LOAD_BEFOR;
            }
        }else if (isLast()){
            if (height <= stand) {
                result = LOAD_DOWN_OVER;
            } else {
                result = LOAD_DOWN_BEFOR;
            }
        }
        return result;
    }

    //获取刷新的高度
    public float getEndHeight() {
        float stand = min;
        if (isFirst()) {
            stand = mearchTop;
        } else if (isLast()) {
            stand = mearchBoom;
        }
        return stand;
    }

    //获取刷新的高度
    public LinearLayout getEndView() {
        LinearLayout view = topView;
        if (isFirst()) {
            view = topView;
        } else if (isLast()) {
            view = boomView;
        }
        return view;
    }

    @Override
    public boolean isChangStateByHeight() {
        return refrush_state == LOAD_DOWN_OVER || refrush_state == LOAD_DOWN_BEFOR||refrush_state == LOAD_OVER || refrush_state == LOAD_BEFOR;

    }


    public void upRefrush_state(int refrush_state) {
        if (this.refrush_state == refrush_state) {
            return;
        }

//        if ((this.refrush_state==LOAD_BEFOR||this.refrush_state==LOAD_DOWN_BEFOR)&&refrush_state==LOADING){
//            if (listener!=null){
//                listener.onLoading();
//            }
//        }

        JLog.i(this.refrush_state+">>>>>>"+refrush_state);
        if (refrush_state==LOADING){
            JLog.i(this.refrush_state+"执行监听>>>>>>"+refrush_state);
            if (listener!=null){
                listener.onLoading();
            }
        }

        clearViewAnimotion();
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
                break;
            case LOAD_FAIL:
                topView.upState(ShapeView.LOAD_FAIL);
                topView.setTvMsg("加载失败");
                break;
            case LOAD_SUCCESS:
                topView.upState(ShapeView.LOAD_SUCCESS);
                topView.setTvMsg("加载成功!");
                break;

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
        this.refrush_state = refrush_state;

    }


    private int a;

    private int nextState=-1;
    @Override
    public void moveToChildHight(final int refrush_state) {
        View view = getEndView();
         float stand=getStandHeightByStated(refrush_state);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int height = layoutParams.height;
        if (height == stand) {
            return;
        }
        if (animator != null && animator.isRunning()) {
            animator.removeAllListeners();
            animator.cancel();
            animator=null;
        }

        if (refrush_state==LOADING){

            JLog.i("--------------失败了------------------------"+stand+">>>"+height);
        }


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
                load=LOAD_OVER;
                break;
            case LOAD_DOWN_SUCCESS:
            case LOAD_DOWN_FAIL:
                load=LOAD_DOWN_OVER;
                break;
        }

        this.nextState=load;
        JLog.i(load+"---------------动画执行开始-------------------------"+refrush_state);
        animator = ValueAnimator.ofFloat(height, stand);
        final View finalView = view;
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                setViewHeight(finalView,value);
            }
        });
        final int finalLoad = load;
        animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    JLog.i(finalLoad +"---------------动画执行结束-------------------------"+refrush_state);
                    upRefrush_state( PullRecycleView.this.nextState);

                }
            });

        if (refrush_state==LOAD_SUCCESS){

            JLog.i("-------------加载成功----------------");
        }
        animator.setDuration(200);

        if (isNoTouch) {
            if (isChangStateByHeight()) {
                animator.start();
            } else {
                animator.setStartDelay(200);
                animator.start();
            }
        }


    }


}
