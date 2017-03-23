package sang.com.xdialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.sang.viewfractory.listener.OnScrollSelectListener;

import sang.com.xdialog.inter.OnEntryClickListener;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/2/8 16:10
 */
public class XDialog<T> extends Dialog {
    protected OnScrollSelectListener listener;
    protected int layoutId;
    protected T datas;
    public final static int NO_BUTTON = 0;
    public final static int BUTTON_UP = 1;
    /**
     * 有一个Edittext
     */
    public final static int ALEART_EDITTEXT = 2;
    /**
     * 提示框中,只有一个确认按钮
     */
    public static final int ALEART_ONLY_ENTRY = 3;
    /**
     * 选择循环显示
     */
    public static final int PICK_NOCYCLE = 4;

    /**
     * selectDialog 宽布满整个屏幕
     */
    public static final int SELECT_WIDTH_FULL = 5;

    protected Button bt_cancel, bt_entry;
    protected OnCancelListener cancleListener;
    protected OnEntryClickListener entryListener;
    protected XDialog dialog;
    protected int style;
    protected Window window;
    protected View view;
    /**
     * 动画效果
     */
    private int animationsStyle;

    /**
     * 设置动画效果
     * @param animationsStyle
     */
    public void setAnimationsStyle(int animationsStyle) {
        this.animationsStyle = animationsStyle;
    }

    public XDialog(Context context) {
        super(context);
    }

    public XDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected XDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if (layoutId == 0) {
            initContentView();
        } else {
            setContentView(layoutId);
        }
        if (view != null) {
            setContentView(view);
        }
        bt_cancel = (Button) findViewById(R.id.bt_cancel);
        bt_entry = (Button) findViewById(R.id.bt_entry);

        dialog = this;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        layoutId = layoutResID;
    }

    protected void iniViews() {

    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        this.view = view;
    }

    protected void initContentView() {

    }


    /**
     * 确认按钮监听
     *
     * @param listener
     */
    public void setOnClickListener(OnEntryClickListener listener) {
        entryListener = listener;
    }

    public void setOnCancelListener(OnCancelListener listener) {
        cancleListener = listener;
    }

    @Override
    protected void onStart() {
        super.onStart();
        initCancelListener();
        initEntryListener();
        initAnimation();
    }

    public void initCancelListener() {
        if (bt_cancel != null) {
            bt_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cancleListener != null) {
                        cancleListener.onCancel(dialog);
                    } else {
                        dismiss();
                    }
                }
            });
        }

    }

    protected void initEntryListener() {
        if (bt_entry != null) {
            bt_entry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (entryListener != null) {
                        entryListener.onClick(dialog, 0, null);
                    } else {
                        dismiss();
                    }
                }
            });
        }
    }

    /**
     * 展示的数据
     *
     * @param datas
     */
    public void show(T datas) {
        setDatas(datas);
        show();
    }

    /**
     * 设置要显示的数据
     *
     * @param datas
     */
    public void setDatas(T datas) {
        this.datas=datas;
    }

    /**
     * 设置滑动监听
     *
     * @param onPickerSelecterListener
     */
    public void setOnPickerSelecterListener(OnScrollSelectListener onPickerSelecterListener) {
        this.listener = onPickerSelecterListener;
    }

    /**
     * 显示指定风格的数据
     *
     * @param style
     */
    public void showStyle(int style) {
        this.style = style;
        changeLayoutByStyle(style);
        show();
    }

    protected void changeLayoutByStyle(int style) {

    }


    /**
     * 设置风格
     *
     * @param style
     */
    public void setStyle(int style) {
        changeLayoutByStyle(style);
    }



    protected void initAnimation() {
        if (animationsStyle > 0) {
            window.setWindowAnimations(animationsStyle);
        }
    }

    protected String be_entry_name, bt_cancle_name;

    /**
     * 设置按钮的文字信息 默认为"取消" 和"确认" 在show之前调用
     *
     * @param entry  确认按钮
     * @param cancle 取消按钮
     */
    public void setButtonName(String entry, String cancle) {
        if (!TextUtils.isEmpty(entry)) {
            be_entry_name = entry;
        }
        if (!TextUtils.isEmpty(cancle)) {
            bt_cancle_name = entry;
        }
    }

}
