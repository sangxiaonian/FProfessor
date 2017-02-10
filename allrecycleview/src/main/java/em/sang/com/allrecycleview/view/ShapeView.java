package em.sang.com.allrecycleview.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/12/6 10:49
 */
public class ShapeView extends BasicView {



    public ShapeView(Context context) {
        super(context);


    }

    public ShapeView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public ShapeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }



    private boolean change;
    protected void flipAnimation() {
        clearViewAnimation();
        flip = ValueAnimator.ofFloat(1f, 0f, 1f);
        flip.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                if (change) {
                    setScaleX(value);
                } else {
                    setScaleY(value);
                }

            }
        });
        flip.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                change = !change;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                setScaleX(1);
                setScaleY(1);

            }
        });
        flip.setRepeatCount(Integer.MAX_VALUE);
        flip.setDuration(1000);
        flip.start();
    }

    @Override
    protected Bitmap creatShape(int mWidth,int mHeight) {
        return factory.creatShap(mWidth,mHeight);
    }
}
