package sang.com.xdialog.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sang.com.xdialog.utils.ScrollUtils;

/**
 * Description：仿Ios滚动选择控件
 *
 * @Author：桑小年
 * @Data：2017/2/6 17:21
 */
public class PickerScrollView extends View {


    private Paint mPaint;
    private Rect textRectF, centerRectf;
    private int centerColor;
    private float mWidth, mHeight, centerY, startY, lineStartY, lineEndY, downY, cellHeight, textHeight;
    private int bigCap, textSize;
    private int currentPosition;
    private ValueAnimator animator, fillAnimator;
    private List<String> datas;
    private Map<Integer, Float> basicLines;
    private VelocityTracker velocityTracker;
    private OnPickerSelecterListener listener;


    public void setOnPickerSelecterListener(OnPickerSelecterListener listener){
        this.listener=listener;
    }

    public PickerScrollView(Context context) {
        super(context);
        initView(context, null, 0);
    }

    public PickerScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0);
    }

    public PickerScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        setBackgroundColor(Color.WHITE);
        centerColor = Color.BLACK;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textRectF = new Rect();
        centerRectf = new Rect();
        downY = -1;

        datas = new ArrayList<>();
        basicLines = new HashMap<>();
        velocityTracker = VelocityTracker.obtain();

        for (int i = 0; i <= 100; i++) {
            datas.add("test" + i);

        }


    }

    private void initDatas() {
        if (datas.size() > 0) {
            mPaint.setTextSize(textSize);
            mPaint.getTextBounds(datas.get(0), 0, datas.get(0).length(), textRectF);
            bigCap = (int) (textRectF.height() /5f);
            textHeight = textRectF.height();
            cellHeight = textHeight + 2 * bigCap;
        }

    }

    /**
     * 设置或更新要显示的数据
     * @param list
     */
    public void setDatas(List<String> list) {
        if (list != null && list.size() > 0) {
            datas.clear();
            datas.addAll(list);
            if (listener!=null){
                listener.onSelecter(datas.get(currentPosition),currentPosition);
            }
            postInvalidate();
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        if (lineEndY == 0) {
            if (textSize==0){
            textSize = (int) (mHeight / 5);}
            initDatas();
            centerY = (mHeight + textHeight) / 2;
            startY = centerY;
            lineStartY = (mHeight - textHeight) / 2 - bigCap;
            lineEndY = (mHeight + textHeight) / 2 + bigCap;

        }

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawSelectText(canvas);
        drawOtherText(canvas);
        mPaint.setColor(Color.GRAY);
        mPaint.setAlpha(255);
        canvas.drawLine(0, lineStartY, mWidth, lineStartY, mPaint);
        canvas.drawLine(0, lineEndY, mWidth, lineEndY, mPaint);

    }

    /**
     * 绘制其他文字
     * @param canvas
     */
    private void drawOtherText(Canvas canvas) {
        //绘制上面的
        mPaint.setColor(centerColor);
        float bassicLine = startY - cellHeight + bigCap;
        for (int i = currentPosition - 1; i >= 0; i--) {
            float v = bassicLine - cellHeight;
//            float scale = (float) Math.pow(2, Math.abs(v - lineStartY) / (2 * cellHeight));
            float scale = getScrall(v - lineStartY);
            mPaint.setAlpha((int) (255 / scale));
            String text = datas.get(i);
            mPaint.setTextSize(textSize / scale);
            mPaint.getTextBounds(text, 0, text.length(), centerRectf);
            float startX = (mWidth - centerRectf.width()) / 2;
            canvas.drawText(text, startX, bassicLine - (cellHeight - centerRectf.height()) / 2, mPaint);
            basicLines.put(i, bassicLine - (cellHeight - centerRectf.height()) / 2);
            bassicLine -= cellHeight;
        }


        //绘制下面的

        bassicLine = startY + bigCap + cellHeight;
        for (int i = currentPosition + 1; i < datas.size(); i++) {
            float v = bassicLine;
//            float scale = (float) Math.pow(2, Math.abs(v - lineEndY) / (2 * cellHeight));
            float scale =  getScrall(v - lineEndY);
            mPaint.setAlpha((int) (255 / (scale)));
            String text = datas.get(i);
            mPaint.setTextSize(textSize / scale);
            mPaint.getTextBounds(text, 0, text.length(), centerRectf);
            float startX = (mWidth - centerRectf.width()) / 2;
            canvas.drawText(text, startX, bassicLine - (cellHeight - centerRectf.height()) / 2, mPaint);
            basicLines.put(i, bassicLine - (cellHeight - centerRectf.height()) / 2);
            bassicLine += cellHeight;
        }

    }

    /**
     * 获取当前被选中的数据
     * @return
     */
    public int getSelect(){
        return currentPosition;
    }

    private float getScrall(float distance){
        float scale = (float) Math.pow(2, Math.abs(distance) / (2 * cellHeight));
        return scale;
    }

    /**
     * 绘制被选中的文字
     * @param canvas
     */
    private void drawSelectText(Canvas canvas) {
        if (startY - centerY >= cellHeight) {
            currentPosition--;
            if (currentPosition < 0) {
                currentPosition = 0;
                if (startY - centerY >= cellHeight * 2)
                    startY = cellHeight * 2 + centerY;
            } else {
                startY = centerY;
            }


        } else if (startY - centerY <= -cellHeight) {
            currentPosition++;
            if (currentPosition > datas.size() - 1) {
                currentPosition = datas.size() - 1;
                if (startY - centerY < -2 * cellHeight) {
                    startY = centerY - 2 * cellHeight;
                }
            } else {
                startY = centerY;
            }

        }
        mPaint.setColor(centerColor);
        float v = startY + bigCap;
//        float scale = (float) Math.pow(2, Math.abs(v - lineEndY) / (2 * cellHeight));
        float scale =  getScrall(v - lineEndY);
        mPaint.setAlpha((int) (255 / scale));
        String selectText = datas.get(currentPosition);
        mPaint.setTextSize(textSize / scale);
        mPaint.getTextBounds(selectText, 0, selectText.length(), textRectF);
        float startX = (mWidth - textRectF.width()) / 2;
        canvas.drawText(selectText, startX, startY, mPaint);
        basicLines.put(currentPosition, startY);
    }

    boolean isFill=true;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (downY == -1) {
            downY = event.getY();
        }

        velocityTracker.addMovement(event);
        cancleAnimotion();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = event.getY();
                if (!((currentPosition == 0 && downY < moveY) || (currentPosition == datas.size() - 1 && downY > moveY))) {
                    startY = startY + (moveY - downY);
                    downY = moveY;
                    isFill=true;
                    postInvalidate();
                } else {
                    float scale =  (Math.abs(startY-centerY)*5/cellHeight);
                    scale=scale<1?1:scale;

                    startY = startY + (moveY - downY)/scale;
                    downY = moveY;
                    isFill=false;
                    postInvalidate();
                }
                break;
            default:
                downY = -1;
                velocityTracker.computeCurrentVelocity(1000);
                float velocityY = velocityTracker.getYVelocity();
                if (Math.abs(velocityY) > 500&&datas.size()>5&&isFill) {//速度大于200时候，用惯性滑动
                    filling(velocityY);
                } else {
                    moveToPosition();
                }
                break;
        }


        return true;
    }

    private void cancleAnimotion() {
        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }
        if (fillAnimator != null && fillAnimator.isRunning()) {
            fillAnimator.cancel();
        }
    }

    float a = 0;

    private void filling(float velocityY) {
        ScrollUtils utils = new ScrollUtils(getContext(), velocityTracker);

        float minY = 0;
        if (velocityY < 0) {
            minY = -(datas.size() - currentPosition) * cellHeight;
        }
        if (velocityY > 0) {
            minY = (currentPosition + 1) * cellHeight;
        }
        float finalY = (float) utils.getSplineFlingDistance(minY);
        float t = (float) utils.getTiemByDistance(finalY);
        float endY = startY + finalY;
        float start = startY;
        fillAnimator = ValueAnimator.ofFloat(start, endY);
        fillAnimator.setInterpolator(new ScrollUtils.ViscousFluidInterpolator());
        fillAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                startY += (value - a);
                postInvalidate();
                a = value;
            }
        });
        fillAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                moveToPosition();
            }
        });

        fillAnimator.setDuration((long) Math.abs(t));
        fillAnimator.start();

    }

    private void moveToPosition() {
        float start = startY;
        if (startY - centerY > cellHeight / 2) {
            currentPosition--;
            currentPosition = currentPosition < 0 ? 0 : currentPosition;
        } else if (startY - centerY < -cellHeight / 2) {
            currentPosition++;
            currentPosition = currentPosition > datas.size() - 1 ? datas.size() - 1 : currentPosition;
        }

        if (listener!=null){
            listener.onSelecter(datas.get(currentPosition),currentPosition);
        }

        start = basicLines.get(currentPosition);
        animator = ValueAnimator.ofFloat(start, centerY);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                startY = value;
                postInvalidate();
            }
        });

        animator.setDuration(200);
        animator.start();
    }

    public String getCurrentData() {
        return datas.get(currentPosition);
    }


    public interface OnPickerSelecterListener{
        void onSelecter(String content,int position);
    }

}
