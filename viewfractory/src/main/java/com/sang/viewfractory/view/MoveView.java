package com.sang.viewfractory.view;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.sang.viewfractory.utils.DeviceUtils;
import com.sang.viewfractory.utils.ViewUtils;

public class MoveView extends android.support.v7.widget.AppCompatImageView implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private PointF downP, destinationP, ratioP;

    /**
     * view开始和结束时后的位置
     */
    private PointF currentPoint, startPoint;
    private float maxScaleX, minScaleX;
    private boolean isCanDrag;
    private GestureDetector detector;
    private OnLongClickListener longClickListener;
    private OnClickListener clickListener;
    private MoveViewListener listener;
    private boolean isSacel;
    private float doubliScale;
    private float mHeight, mWidth;
    private Bitmap bitmap;
    private long time;


    /**
     * 原始View的位置
     */
    Rect viewRect, startRect;

    /**
     * 设置MoveView监听,设置此监听时候,${setOnClickListener}和${setOnLongClickListener}方法失效
     *
     * @param listener
     */
    public void setListener(MoveViewListener listener) {
        this.listener = listener;
    }

    public MoveView(Context context) {
        super(context);
        initView(context, null, 0);
    }

    public MoveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0);
    }

    public MoveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }


    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        downP = new PointF(0, 0);
        destinationP = new PointF(100, 100);
        ratioP = new PointF();
        minScaleX = 1;
        doubliScale = 2f;
        currentPoint = new PointF(0, 0);
        startPoint = new PointF(0, 0);
        setClickable(true);
        detector = new GestureDetector(context, this);
        time = 300;

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (viewRect != null && bitmap != null) {
            canvas.save();
            canvas.translate(currentPoint.x, currentPoint.y);
            canvas.drawBitmap(creatBitmap(bitmap, viewRect), 0, 0, null);
            canvas.restore();
        }

    }

    /**
     * 缩放bitmap
     *
     * @param bitmap
     * @param viewRect
     * @return
     */
    private Bitmap creatBitmap(Bitmap bitmap, Rect viewRect) {
        float height = viewRect.width() * (bitmap.getHeight() * 1.0f / bitmap.getWidth());
        return Bitmap.createScaledBitmap(bitmap, viewRect.width(), (int) height, true);
    }

    private void startAnimotion() {
        //起始数据
        float startTranslatY = startPoint.y;
        float stattTranslateX = startPoint.x;
        int right = startRect.right;
        int bottom = startRect.bottom;
        PointF pointF = new PointF();
        pointF.x = right;
        pointF.y = bottom;
        ViewPoint point = new ViewPoint(stattTranslateX, startTranslatY, pointF);
        //结束数据
        PointF pointF1 = new PointF(startRect.width() * maxScaleX, startRect.height() * maxScaleX);
        ViewPoint endPoint = new ViewPoint(0, 0, pointF1);
        getAnimotion(point, endPoint, false);

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimotion();
    }

    /**
     * 自定义估值算法
     * Created by Administrator on 2016/2/2.
     */
    public class MyTypeEvaluator implements TypeEvaluator<ViewPoint> {
        @Override
        public ViewPoint evaluate(float fraction, ViewPoint startValue, ViewPoint endValue) {
            ViewPoint point = new ViewPoint();
            float value = fraction*fraction;
           value= (float) Math.pow(value, 0.5);
            point.translateX = startValue.translateX + (endValue.translateX - startValue.translateX) * value;
            point.translateY = startValue.translateY + (endValue.translateY - startValue.translateY) * value;
            point.sizePoint.x = startValue.sizePoint.x + (endValue.sizePoint.x - startValue.sizePoint.x) * value;
            point.sizePoint.y = startValue.sizePoint.y + (endValue.sizePoint.y - startValue.sizePoint.y) * value;
            return point;
        }
    }

    /**
     * 保存小球坐标的类
     * Created by Administrator on 2016/2/2.
     */
    public class ViewPoint {
        public float translateX;
        public float translateY;
        public PointF sizePoint;

        public ViewPoint() {
            sizePoint = new PointF();
        }

        public ViewPoint(float translateX, float translateY, PointF sizePoint) {
            this.translateX = translateX;
            this.translateY = translateY;
            this.sizePoint = sizePoint;
        }
    }


    /**
     * 设置传入进来的View
     *
     * @param originView
     */
    public void setOriginView(View originView, Bitmap bitmap) {
        viewRect = ViewUtils.getViewRect(originView);
        startRect = ViewUtils.getViewRect(originView);
        int width = originView.getWidth();
        int height = originView.getHeight();
        viewRect.right = width;
        viewRect.bottom = height;
        startRect.right = width;
        startRect.bottom = height;


        int[] loaction = ViewUtils.getLoaction(originView);
        currentPoint.x = loaction[0];
        currentPoint.y = loaction[1] - DeviceUtils.getStatuBarHeight(getContext());
        startPoint.x = loaction[0];
        startPoint.y = loaction[1] - DeviceUtils.getStatuBarHeight(getContext());
        mWidth = DeviceUtils.getScreenWidth(getContext());
        mHeight = DeviceUtils.getScreenHeight(getContext());
        this.bitmap = bitmap;

        float maxScaleX = mWidth * 1.0f / startRect.width();
        float maxScaleY = mHeight * 1.0f / startRect.height();

        this.maxScaleX = Math.min(maxScaleX, maxScaleY);
        minScaleX = 1;
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
        clear();
        if (isCanDrag && event.getAction() != MotionEvent.ACTION_DOWN && event.getAction() != MotionEvent.ACTION_MOVE) {
            if (startRect.width() * 1.0 / viewRect.width() > 0.6) {
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


    private void recoverToPosition() {
        int right = viewRect.right;
        int bottom = viewRect.bottom;
        PointF pointF = new PointF();
        pointF.x = right;
        pointF.y = bottom;
        ViewPoint point = new ViewPoint(currentPoint.x, currentPoint.y, pointF);
        //结束数据
        PointF pointF1 = new PointF(startRect.width() * maxScaleX, startRect.height() * maxScaleX);
        ViewPoint endPoint = new ViewPoint(0, 0, pointF1);
        getAnimotion(point, endPoint, false);

    }

    /**
     * 结束
     */
    private void moveToPoint() {

        int right = viewRect.right;
        int bottom = viewRect.bottom;
        PointF pointF = new PointF();
        pointF.x = right;
        pointF.y = bottom;
        ViewPoint point = new ViewPoint(currentPoint.x, currentPoint.y, pointF);
        //结束数据
        PointF pointF1 = new PointF(startRect.width(), startRect.height());
        float startTranslatY = startPoint.y;
        float stattTranslateX = startPoint.x;
        ViewPoint endPoint = new ViewPoint(stattTranslateX, startTranslatY, pointF1);
        getAnimotion(point, endPoint, true);


    }

    ValueAnimator animator;

    private void getAnimotion(ViewPoint point, ViewPoint endPoint, final boolean miss) {
        clear();
        animator = ValueAnimator.ofObject(new MyTypeEvaluator(), point, endPoint);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewPoint point = (ViewPoint) animation.getAnimatedValue();
                viewRect.right = (int) point.sizePoint.x;
                viewRect.left = 0;
                viewRect.top = 0;
                viewRect.bottom = (int) point.sizePoint.y;
                currentPoint.y = point.translateY;
                currentPoint.x = point.translateX;
                postInvalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (listener != null && miss) {
                    listener.animotionEnd(MoveView.this);
                }
            }
        });
        animator.setDuration(time);
        animator.start();
    }

    private void clear() {
        if (animator != null) {
            animator.cancel();
        }
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

            if (distanceY > Math.abs(distanceX) && Math.abs(distanceY) > DeviceUtils.getMinTouchSlop(getContext())) {
                isCanDrag = true;
            }
            if (isCanDrag) {
                float abs = Math.abs((mHeight - distanceY) * maxScaleX / mHeight);
                abs = abs > maxScaleX ? maxScaleX : (abs < minScaleX ? minScaleX : abs);
                currentPoint.x = distanceX + (mWidth - (startRect.width() * abs)) / 2;
                currentPoint.y = distanceY;
                viewRect.right = (int) (startRect.width() * abs);
                viewRect.left = 0;
                viewRect.top = 0;
                viewRect.bottom = (int) (startRect.height() * abs);
                postInvalidate();
            }
        } else {
            float y = -distanceY + getTranslationY();
            float x = -distanceX + getTranslationX();
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
            if (isSacel){
                animate().scaleY(1).scaleX(1).withStartAction(new Runnable() {
                    @Override
                    public void run() {
                        moveToPoint();
                    }
                });
            }else {
                moveToPoint();
            }
        }

        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        if (!isSacel) {
            setPivotX(e.getX());
            setPivotY(e.getX());
            animate().scaleX(doubliScale).scaleY(doubliScale).setInterpolator(new AccelerateInterpolator());
            isSacel = true;
        } else {
            animate().scaleX(1).scaleY(1).translationX(0).translationY(0).setInterpolator(new AccelerateInterpolator());
            isSacel = false;
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
