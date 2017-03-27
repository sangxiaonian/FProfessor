package com.sang.viewfractory.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import com.sang.viewfractory.utils.DeviceUtils;
import com.sang.viewfractory.utils.JLog;
import com.sang.viewfractory.utils.ToastUtil;
import com.sang.viewfractory.utils.ViewUtils;

public class MoveView1 extends android.support.v7.widget.AppCompatImageView implements GestureDetector.OnGestureListener ,GestureDetector.OnDoubleTapListener{

    private PointF downP, destinationP, ratioP;

    /**
     * view开始和结束时后的位置
     */
    private PointF startLocation;

    /**
     *
     */
    private PointF scaleRectF;
    private float maxScale, minScale;
    private boolean isCanDrag;
    private GestureDetector detector;
    private OnLongClickListener longClickListener;
    private OnClickListener clickListener;
    private MoveViewListener listener;
    private boolean isSacel;
    private float doubliScale;

    /**
     * 设置MoveView监听,设置此监听时候,${setOnClickListener}和${setOnLongClickListener}方法失效
     *
     * @param listener
     */
    public void setListener(MoveViewListener listener) {
        this.listener = listener;
    }

    public MoveView1(Context context) {
        super(context);
        initView(context, null, 0);
    }

    public MoveView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0);
    }

    public MoveView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }



    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        downP = new PointF(0, 0);
        destinationP = new PointF(100, 100);
        ratioP = new PointF();
        maxScale = 1.0f;
        minScale = 0.3f;
        doubliScale=2f;

        startLocation = new PointF(0,0);

        setClickable(true);
        detector = new GestureDetector(context, this);
    }

    private float mHeight, mWidth;


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (viewRect!=null) {
            canvas.save();
            canvas.translate(startLocation.x,startLocation.y-DeviceUtils.getStatuBarHeight(getContext()));
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            canvas.drawRect(viewRect, paint);
            canvas.restore();
        }

    }

    private void startAnimotion(){
//        ValueAnimator.ofFloat()
    }


    /**
     * 原始View的位置
     */
    Rect viewRect;

    /**
     *
     * 设置传入进来的View
     * @param originView
     */
    public void setOriginView(View originView) {
          viewRect = ViewUtils.getViewRect(originView);
        int[] loaction = ViewUtils.getLoaction(originView);
        startLocation.x = loaction[0];
        startLocation.y=loaction[1]-DeviceUtils.getStatuBarHeight(getContext());
        postInvalidate();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isCanDrag && event.getAction() != MotionEvent.ACTION_DOWN && event.getAction() != MotionEvent.ACTION_MOVE) {
            if (getScaleX() < 0.5) {
                moveToPoint();
            } else {
                recoverToPosition();
            }
            isCanDrag = false;
            downP.x = 0;
            downP.y = 0;
        }
        detector.onTouchEvent(event);
        return true;
    }





    /**
     * 缩放view
     *
     * @param moveX 当前X坐标
     * @param moveY 触摸点的Y坐标
     * @param disY  移动距离
     * @param disX  x方向已定距离
     */
    private void scaleView(float moveX, float moveY, float disY, float disX) {
        float abs = Math.abs((mHeight - disY) / mHeight);
        abs = abs > maxScale ? maxScale : (abs < minScale ? minScale : abs);
        setScaleX(abs);
        setScaleY(abs);
        setPivotX(moveX);
        setPivotY(moveY);
        setTranslationX(disX * abs);
        setTranslationY(disY * abs);
    }

    private void recoverToPosition() {
        animate().scaleX(1).scaleY(1).translationX(0).translationY(0).setInterpolator(new OvershootInterpolator());
    }

    private void moveToPoint() {
        float x1 = downP.x / mWidth;
        float y1 = downP.y / mHeight;
        float x = getPivotX() - getTranslationX() - mWidth * x1 * getScaleX();
        float y = getPivotY() - mHeight * getScaleY() * y1 - getTranslationY();
        animate().translationY(-y).translationX(-x).setInterpolator(new OvershootInterpolator()).withEndAction(new Runnable() {
            @Override
            public void run() {
                post(new Runnable() {
                    @Override
                    public void run() {
                        if (listener!=null){
                            listener.animotionEnd(MoveView1.this);
                        }

                    }
                });

            }
        });
    }

    @Override
    public boolean onDown(MotionEvent e) {

        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }


    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        this.clickListener = l;
    }

    @Override
    public void setOnLongClickListener(@Nullable OnLongClickListener l) {
        this.longClickListener = l;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {

        return false;
    }


    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

        if (!isSacel) {
            distanceY = e2.getRawY() - e1.getRawY();
            distanceX = e2.getRawX() - e1.getRawX();
            downP.x = e1.getRawX();
            downP.y = e1.getRawY();
            if (downP.x == 0) {
                downP.x = e2.getRawX();
                downP.y = e2.getRawY();
            }
            if (distanceY > Math.abs(distanceX) && Math.abs(distanceY) > DeviceUtils.getMinTouchSlop(getContext())) {
                isCanDrag = true;
            }
            if (isCanDrag) {
                scaleView(e2.getRawX(), e2.getRawY(), distanceY, distanceX);
            }
        }else {
            JLog.i("getTranslationX:"+getTranslationX()+"getTranslationY:"+getTranslationX()+"left:"+getLeft());
            float y = -distanceY + getTranslationY();
            float x =- distanceX+getTranslationX();
            setTranslationY(y);
            setTranslationX(x);
        }

        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        if (!isCanDrag) {
            if (listener != null) {
                listener.onLongClick(this);
            } else if (clickListener != null) {
                longClickListener.onLongClick(this);
            }
        }

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        if (!isCanDrag) {
            if (listener != null) {
                listener.onClick(this);
            } else if (clickListener != null) {
                clickListener.onClick(this);
            }
        }
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        ToastUtil.showTextToast("双击了");

        if (!isSacel) {
            setPivotX(e.getX());
            setPivotY(e.getX());
            animate().scaleX(doubliScale).scaleY(doubliScale).setInterpolator(new AccelerateInterpolator());
            isSacel=true;
        } else {
            animate().scaleX(1).scaleY(1).translationX(0).translationY(0).setInterpolator(new AccelerateInterpolator());
            isSacel=false;
        }
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }



    public interface MoveViewListener {
        void onLongClick(View view);
        void onClick(View view);
        void animotionEnd(View view);

    }

}
