package em.sang.com.allrecycleview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import em.sang.com.allrecycleview.utils.Apputils;
import em.sang.com.allrecycleview.utils.Config;
import em.sang.com.allrecycleview.utils.Utils;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/12/9 10:50
 */
public class RefrushLinearLayout extends LinearLayout {

    private TextView tvMsg, tvTime;
    private BasicView shapeView;

    private String flag;

    public RefrushLinearLayout(Context context) {
        super(context);
        initView(context, null, 0);
    }

    public RefrushLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0);
    }

    public RefrushLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    LinearLayout l;

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {

        int gap = Apputils.dip2px(context, 5);
        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);
        shapeView = new ShapeView(context);
        LayoutParams params = new LayoutParams(Apputils.dip2px(context, 30), Apputils.dip2px(context, 30));
        shapeView.setLayoutParams(params);
        tvMsg = new TextView(context);
        tvMsg.setTextSize(TypedValue.COMPLEX_UNIT_PX, Apputils.sp2px(context, 16));
        tvMsg.setText("准备刷新数据");
        tvTime = new TextView(context);
        tvTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, Apputils.sp2px(context, 12));
        tvTime.setVisibility(INVISIBLE);

        l = new LinearLayout(context);
        l.addView(tvMsg);
        l.addView(tvTime);
        tvTime.setVisibility(GONE);
        l.setOrientation(LinearLayout.VERTICAL);
        l.setPadding(gap, 0, 0, 0);
        addView(shapeView);
        addView(l);
        setPadding(gap, gap, gap, gap);
    }



    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        setOrientation(HORIZONTAL);
    }

    /**
     * 设置显示数据
     *
     * @param msg
     */
    public void setTvMsg(String msg) {
        tvMsg.setText(msg);
    }

    /**
     * 设置刷新时间
     *
     * @param context
     */
    public void setTvTime(Context context) {
        tvTime.setText("上次刷新:" + Utils.getTime(Config.sp_name, context));
    }

    /**
     * 设置刷新时间
     *
     * @param flag    设置刷新时间的view标志
     * @param context 上下文
     */
    public void setTvTime(String flag, Context context) {
        tvTime.setText("上次刷新:" + Utils.getTime(flag, context));

    }

    /**
     * 设置刷新时间的flag
     *
     * @param flag 用来判断当前刷新的View,如果不设置,则无法显示刷新时间
     */
    public void setFlag(String flag) {
        this.flag = flag;
        tvTime.setVisibility(VISIBLE);
    }


    public void upState(int state) {
        shapeView.upState(state);
        if (state == ShapeView.LOAD_SUCCESS) {
            Utils.setTime(flag,getContext());
        }else {
            setTvTime(flag,getContext());
        }
    }

    public String getTest() {
        return tvMsg.getText().toString();
    }

}
