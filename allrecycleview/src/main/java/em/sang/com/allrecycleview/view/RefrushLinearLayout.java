package em.sang.com.allrecycleview.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import em.sang.com.allrecycleview.utils.Apputils;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/12/9 10:50
 */
public class RefrushLinearLayout extends LinearLayout {

    private TextView tvMsg,tvTime;
    private BasicView shapeView;

    public RefrushLinearLayout(Context context) {
        super(context);
        initView(context,null,0);
    }

    public RefrushLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs,0);
    }

    public RefrushLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs,defStyleAttr);
    }

    Rect rect;
    private void initView (Context context, AttributeSet attrs, int defStyleAttr){

        int gap = Apputils.dip2px(context, 5);

        shapeView = new ShapeView(context);
        setGravity(Gravity.CENTER);
        addView(shapeView);
        tvMsg = new TextView(context);
        tvMsg.setTextSize(Apputils.sp2px(context,6));
        tvMsg.setText("下拉刷新数据");
        tvTime = new TextView(context);
        tvTime.setText("上次刷新时间:");
        LinearLayout l = new LinearLayout(context);
        l.addView(tvMsg);
        l.addView(tvTime);
        l.setOrientation(VERTICAL);
        l.setPadding(gap,0,0,0);
        addView(l);
        setPadding(gap,gap,gap,gap);
    }



    public void setTvMsg(String msg){
        tvMsg.setText(msg);
    }

    public void setTvTime(String msg){
        tvTime.setText("上次刷新时间:"+msg);
    }

    public void upState(int state){
        shapeView.upState(state);
    }

    public String getTest(){
        return tvMsg.getText().toString();
    }

}
