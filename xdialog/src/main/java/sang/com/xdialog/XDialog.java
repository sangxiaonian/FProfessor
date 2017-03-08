package sang.com.xdialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.sang.viewfractory.view.PickerScrollView;

import sang.com.xdialog.inter.OnEntryClickListener;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/2/8 16:10
 */
public class XDialog<T> extends Dialog {
    PickerScrollView.OnPickerSelecterListener listener;
    protected int layoutId;
    public final static int NO_BUTTON = 0;
    public final static int BUTTON_UP = 1;
    public final static int ALEART_EDITTEXT = 2;
    public static final int ALEART_ONLY_ENTRY = 3;

    protected Button bt_cancel, bt_entry;
    protected OnCancelListener cancleListener;
    protected OnEntryClickListener entryListener;
    protected XDialog dialog;
    protected int style;
    protected Window window;
    protected View view;


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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
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
        initAnimotion();
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
        show();
    }

    /**
     * 设置要显示的数据
     *
     * @param datas
     */
    public void setDatas(T datas) {

    }

    /**
     * 设置滑动监听
     *
     * @param onPickerSelecterListener
     */
    public void setOnPickerSelecterListener(PickerScrollView.OnPickerSelecterListener onPickerSelecterListener) {
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
        if (style == ALEART_EDITTEXT) {
            layoutId = R.layout.alert_editext_dialog;
        }
    }

    /**
     * 设置风格
     *
     * @param style
     */
    public void setStyle(int style) {
        changeLayoutByStyle(style);
    }

    protected int animationstyle;


    protected void initAnimotion() {
        int style = 0;
        switch (animationstyle) {
            case 1:
                style = R.style.dialog_down;
                break;
        }

        if (style > 0) {
            window.setWindowAnimations(style);
        }
    }

}
