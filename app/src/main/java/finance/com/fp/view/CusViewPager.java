package finance.com.fp.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.orhanobut.logger.Logger;

import finance.com.fp.utlis.DeviceUtils;

/**
 * Description：
 *
 * @Author：小年
 * @Data：2017/1/4 11:53
 */
public class CusViewPager extends ViewPager {
    public CusViewPager(Context context) {
        super(context);
    }

    public CusViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    float startX, startY;
    boolean isLeft = true;
    boolean isv = false;


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int currentItem = getCurrentItem();

        int count = getAdapter().getCount();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = ev.getX();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float currentX = ev.getX();
                isLeft = (currentX - startX) > DeviceUtils.getMinTouchSlop(getContext());
                isv = (currentX - startX )< (ev.getY() - startY);
                break;
            default:
                startX = 0;
                startY=0;
                break;

        }
        Logger.i(currentItem + "  " + count + "===========" + isLeft + "=============" + ev.getAction());
        if (isv) {
            requestDisallowInterceptTouchEvent(true);
        } else if (currentItem == 0 && isLeft) {
            requestDisallowInterceptTouchEvent(false);
        } else if (currentItem == count - 1 && !isLeft) {
            requestDisallowInterceptTouchEvent(false);
        } else {
            requestDisallowInterceptTouchEvent(true);
        }

        return super.dispatchTouchEvent(ev);
    }


}
