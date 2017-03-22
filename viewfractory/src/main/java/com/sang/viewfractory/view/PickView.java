package com.sang.viewfractory.view;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import com.sang.viewfractory.listener.OnScrollSelectListener;
import com.sang.viewfractory.utils.DeviceUtils;
import com.sang.viewfractory.utils.ScrollUtils;

import java.util.ArrayList;
import java.util.List;

public class PickView extends View {

    private TextPaint mTextPaint;//画笔
    private int centerColor;
    private PointF centerPoint, downPoint;
    private float textSize, radio;
    private List<String> lists = new ArrayList<>();
    private float textHalf;

    float start = 0;
    private OnScrollSelectListener listener;
    private boolean isCycle;
    private VelocityTracker velocityTracker;
    private ValueAnimator fillAnimator;


    public PickView(Context context) {
        super(context);
        iniView(context, null, 0);

    }

    public PickView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        iniView(context, attrs, 0);
    }

    public PickView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        iniView(context, attrs, defStyleAttr);
    }

    private void iniView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        velocityTracker = VelocityTracker.obtain();
        centerColor = Color.BLACK;
        textSize = 60;
        mTextPaint = new TextPaint();
        mTextPaint.setColor(centerColor);
        mTextPaint.setTextSize(textSize);
        for (int i = 0; i < 150; i++) {
            lists.add("test" + i);
        }

        centerPoint = new PointF();
        downPoint = new PointF();
        downPoint.y = -1;
        isCycle=true;
        setBackgroundColor(Color.parseColor("#abcdef"));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        centerPoint.x = getMeasuredWidth() / 2;
        centerPoint.y = getMeasuredHeight() / 2;
        downPoint.x = getMeasuredWidth();
        downPoint.y = getMeasuredHeight();
        radio = getMeasuredHeight() / 2;
        //文本一半高度
        textHalf = (mTextPaint.descent() + mTextPaint.ascent() + 2 * mTextPaint.getFontSpacing()) / 2;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTest(canvas, textHalf * 2);
        mTextPaint.setColor(Color.RED);
        canvas.drawLine(0, canvas.getHeight() / 2 + textHalf, canvas.getWidth(), canvas.getHeight() / 2 + textHalf, mTextPaint);
        canvas.drawLine(0, canvas.getHeight() / 2 - textHalf, canvas.getWidth(), canvas.getHeight() / 2 - textHalf, mTextPaint);

    }

    private int initPosition(int position) {
        int size = lists.size();
        int count = position % size;
        int result = count < 0 ? (count + size) : count;

        return result;
    }

    private int position = 0;



    private void drawTest(Canvas canvas, float cellHeight) {
        canvas.save();
        //保证当前position为正,切相对位置不变


        //单个条目所占的角度
        float cellAngle = (float) (cellHeight * 180 / (radio * Math.PI));

        //一侧能显示的条目
        int cellPoint = (int) Math.ceil(Math.abs(90 * 1.0 / cellAngle));

        //上策显示的条目
        int maxPoint = position + cellPoint;
        //下面显示的条目
        int minPoint = position - cellPoint;

        int size = lists.size();
        if (!isCycle) {
            maxPoint = maxPoint > (size - 1) ? size - 1 : maxPoint;
            minPoint = minPoint < 0 ? 0 : minPoint;
            start = start > 0 ? (start > cellHeight * 3 ? cellHeight * 3 : start) : (start < -cellHeight * size - cellHeight * 2 ? -cellHeight * size - cellHeight * 2 : start);
        }


        for (int i = minPoint; i <= maxPoint; i++) {
            //相对于中心,第i段的弧长
            float length = start + cellHeight * i + cellHeight / 2;

            //相对于中心,第i段的角度
            float curentAngle = (float) (length * 180 / (radio * Math.PI));

            //顶部相对中心高度
            float top = (float) (radio * (Math.sin(Math.toRadians(curentAngle))));

            //底部相对中心高度
            float botoom = (float) (radio * (Math.sin(Math.toRadians(curentAngle - cellAngle))));

            //当前条目高度
            double currentHeight = top - botoom;

            //顶部真实高度
            float topLine = canvas.getHeight() / 2 - top;
            //底部真实高度
            float bottomLine = canvas.getHeight() / 2 - botoom;

            //绘制文字
            int index = initPosition(i);
            String text = lists.get(index);
            float textWidth = mTextPaint.measureText(text);

            int baseX = (int) (canvas.getWidth() / 2 - textWidth / 2);

            float textHeight = (mTextPaint.descent() + mTextPaint.ascent());

            float scale = (float) (currentHeight / cellHeight);

            //文字底部基线
            int baseY = (int) ((topLine + bottomLine) / 2 - textHeight * scale / 2);

            canvas.save();
            mTextPaint.setColor(Color.RED);
            mTextPaint.setAlpha((int) (255 * scale));
            canvas.clipRect(0, topLine, canvas.getWidth(), bottomLine);
            canvas.scale(1f, scale, canvas.getWidth() / 2, (topLine + bottomLine) / 2);
            canvas.drawText(text, baseX, baseY, mTextPaint);
            canvas.restore();

            //文字中心基线
            int textY = (int) Math.abs((topLine + bottomLine) / 2);

            if (Math.abs(textY - canvas.getHeight() / 2) < (cellHeight / 2) && i != position) {
                this.position = i;
                if (listener != null) {
                    listener.onPositionChenge(index, lists.get(index));
                }

            }
        }
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (downPoint.y == -1) {
            downPoint.x = event.getRawX();
            downPoint.y = event.getRawY();
        }
        clearAllAnimation();
        velocityTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downPoint.x = event.getRawX();
                downPoint.y = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getRawX();
                float moveY = event.getRawY();
                float disX = moveX - downPoint.x;
                float disY = moveY - downPoint.y;
                if (disX > DeviceUtils.getMinTouchSlop(getContext()) && disY < DeviceUtils.getMinTouchSlop(getContext())) {
                    return false;
                }
                start -= disY;
                downPoint.x = moveX;
                downPoint.y = moveY;
                postInvalidate();
                break;
            default:
                velocityTracker.computeCurrentVelocity(1000);
                float velocityY = velocityTracker.getYVelocity();
                if (Math.abs(velocityY) > 500&&lists.size()>5) {//速度大于200时候，用惯性滑动
                    filling(velocityY);
                } else {
                    startUpAnimator();

                }

                downPoint.y = -1;
                downPoint.x = -1;
                break;
        }
        return true;
    }

    private void filling(float velocityY) {
        ScrollUtils utils = new ScrollUtils(getContext(), velocityTracker);
        float minY = radio*12;

        if (!isCycle) {
            if (velocityY < 0) {
                minY = -(lists.size() - initPosition(position)) * textHalf*2;
            }
            if (velocityY > 0) {
                minY = (initPosition(position) + 1) * textHalf*2;
            }
        }

        float finalY = (float) utils.getSplineFlingDistance(minY);
        float t = (float) utils.getTiemByDistance(finalY);
        float endY = start + finalY;
        float startpoint = start;
        fillAnimator = ValueAnimator.ofFloat(startpoint, endY);
        fillAnimator.setInterpolator(new ScrollUtils.ViscousFluidInterpolator());
        fillAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                start=value;
                postInvalidate();
            }
        });
        fillAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startUpAnimator();
            }
        });

        fillAnimator.setDuration((long) Math.abs(t));
        fillAnimator.start();

    }
    private void clearAllAnimation() {
        if (upAnimator != null) {
            upAnimator.cancel();
            upAnimator = null;
        }
        if (fillAnimator != null) {
            fillAnimator.cancel();
            fillAnimator = null;
        }
    }


    ValueAnimator upAnimator;
    private void startUpAnimator() {

        float startPoint = start;
        upAnimator=ValueAnimator.ofFloat(startPoint,-textHalf*2*position);
        upAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                start= (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        upAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (listener!=null){
                    int i = initPosition(position);
                    listener.onStopPosition(i,lists.get(i));
                }
            }
        });
        upAnimator.setDuration(200);
        upAnimator.start();
    }




}
