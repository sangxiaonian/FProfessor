package em.sang.com.allrecycleview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import em.sang.com.allrecycleview.adapter.BasicAdapter;
import em.sang.com.allrecycleview.holder.SimpleHolder;
import em.sang.com.allrecycleview.utils.Apputils;
import em.sang.com.allrecycleview.utils.JLog;
import em.sang.com.allrecycleview.view.RefrushLinearLayout;

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
        float stand ;
        switch (refrush_state) {
            case LOAD_BEFOR:
            case LOADING:
                stand=mearchTop;
                break;
            case LOADING_DOWN:
            case LOAD_DOWN_BEFOR:
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

        JLog.i("-----------initView------------");
        mearchTop = Apputils.getWidthAndHeight(topView)[1];
        mearchBoom = Apputils.getWidthAndHeight(boomView)[1];

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.height = (int) min;
        topView.setLayoutParams(params);

        LinearLayout.LayoutParams boom = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        boom.height = (int) min;
        boomView.setLayoutParams(boom);
        hasTop=true;
        hasBoom=false;



    }


    @Override
    public boolean isLast() {

        if (refrush_state==LOADING){

            return false;
        }else {
            return super.isLast()&&hasBoom;
        }

    }

    @Override
    public boolean isFirst() {
            return super.isFirst();
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
        return canDown()||canUp();
    }

    private boolean canDown(){
        return refrush_state == LOAD_DOWN_OVER || refrush_state == LOAD_DOWN_BEFOR||refrush_state==-1||(refrush_state<=5&&isLast());
    }

    private boolean canUp(){
        return (refrush_state == LOAD_OVER || refrush_state == LOAD_BEFOR||refrush_state==-1)||(refrush_state>5&&isFirst());
    }



}
