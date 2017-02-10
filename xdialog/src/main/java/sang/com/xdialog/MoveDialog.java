package sang.com.xdialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/17 15:19
 */
public class MoveDialog extends Dialog {
    private Activity mContext;

    public MoveDialog(Context context) {
        super(context);
       initView(context );
    }

    public MoveDialog(Context context, int themeResId) {
        super(context, themeResId);
        initView(context );
    }

    protected MoveDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView(context );

    }

    private void initView( Context context){
        mContext= (Activity) context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));

    }


    /**
     * 根据坐标位置显示
     * @param x
     * @param y
     */
    public void show(float x,float y){
        Window mWindow = getWindow();
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        lp.x = 10;   //新位置X坐标
        lp.y = 120; //新位置Y坐标
        mWindow.setAttributes(lp);//
        show();
    }

    /**
     * 根据点击的按钮显示在按钮下方
     * @param view
     */
    public void show(final View view){
        Window mWindow = getWindow();
        mWindow.setGravity(Gravity.LEFT | Gravity.TOP);
        int[] location = new int[2] ;

        view.getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标

        WindowManager.LayoutParams lp = mWindow.getAttributes();
        lp.x = location[0];   //新位置X坐标
        lp.y = location[1];

       //新位置Y坐标
        mWindow.setAttributes(lp);//

        Window dialogWindow =  getWindow();
        WindowManager m = mContext.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高度
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getWidth() * 0.5); // 高度设置为屏幕的0.6，根据实际情况调整
        p.width = (int) (d.getHeight() * 0.5); // 宽度设置为屏幕的0.65，根据实际情况调整
        dialogWindow.setAttributes(p);
        show();
    }






}
