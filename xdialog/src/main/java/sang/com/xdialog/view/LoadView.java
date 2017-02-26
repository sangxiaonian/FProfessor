package sang.com.xdialog.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import sang.com.xdialog.R;
import sang.com.xdialog.utils.DeviceUtils;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/2/15 16:08
 */
public class LoadView extends View {

    private Paint mPaint;
    private int startColor,endColor;
    private float mwidth,mHeight,radius,cellWidth,cellAngle,centerX,centerY,startAngle;
    private int counts;
    private RectF rectF;
    private Rect textRectF;
    private String text = "";
    private int gap;


    public LoadView(Context context) {
        super(context);
        initView(context);
    }

    public LoadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LoadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        cellWidth= DeviceUtils.dip2px(context,8);
        gap=DeviceUtils.dip2px(context,10);
        cellAngle=3;
        counts= (int) (360/cellAngle);
        rectF = new RectF();
        textRectF=new Rect();
        startColor = Color.GRAY;
        endColor=Color.GRAY;
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);


        Drawable drawable = context.getResources().getDrawable(R.drawable.load_default_bg);
        setBackground(drawable);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mwidth=getMeasuredWidth();
        mHeight=getMeasuredWidth();


        radius=mwidth/4;
        centerX=mwidth/2;
        centerY=mHeight/2;
        if (!TextUtils.isEmpty(text)){
            float scal = 25f/(text.length()/5);
            if (scal>18){
                scal=18;
            }if (scal<12){
                scal=12;
            }
            mPaint.setTextSize(DeviceUtils.sp2px(getContext(),scal));
            mPaint.getTextBounds(text,0,text.length(),textRectF);
            centerY=centerY-textRectF.height()/2-gap;
        }
        rectF.left=centerX-radius;
        rectF.top=centerY-radius;
        rectF.right=centerX+radius;
        rectF.bottom=centerY+radius;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(startColor);
        mPaint.setStrokeWidth(cellWidth);
        for (int i = 0; i < counts; i++) {

            if (i%5==0){
                canvas.drawArc(rectF,startAngle+cellAngle*i,cellAngle,false,mPaint);
            }
        }

        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Paint.Style.FILL);
//        mPaint.setColor(Color.WHITE);
        if (!TextUtils.isEmpty(text)){
            canvas.drawText(text,centerX-textRectF.width()/2,mwidth-gap*2,mPaint);
        }


    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimotion();
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (animator!=null){
            animator.cancel();
            animator=null;
        }
    }

    ValueAnimator animator;

    public void setText(String text){
        this.text=text;
    }

    private void startAnimotion(){

        if (animator!=null){
            animator.cancel();
            animator=null;
        }

        animator = ValueAnimator.ofFloat(0, 360);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                startAngle= (float) animation.getAnimatedValue();
                postInvalidate();

            }
        });
        animator.setDuration(5000);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(Integer.MAX_VALUE);
        animator.start();
    }


}
