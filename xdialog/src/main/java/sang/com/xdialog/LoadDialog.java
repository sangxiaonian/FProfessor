package sang.com.xdialog;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.sang.viewfractory.utils.DeviceUtils;

import sang.com.xdialog.view.LoadView;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/2/9 10:10
 */
public class LoadDialog extends XDialog {
    private String msg_titles, msg_content;

    public LoadDialog(Context context) {
        this(context, R.style.DialogCutTheme);

    }

    public LoadDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    private TextView txt_title, content;
    private EditText et;

    protected LoadDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int width = (int) DeviceUtils.getScreenWidth(getContext());
        dialog.getWindow().setLayout(width/2, width/2);


    }

    @Override
    protected void onStart() {
        super.onStart();
        iniViews();
    }




    @Override
    protected void initContentView() {
        super.initContentView();
        LoadView view = new LoadView(getContext());
        view.setText(msg_titles);
        setContentView(view);
    }

    @Override
    public void showStyle(int style) {
        super.showStyle(style);
    }

    @Override
    protected void changeLayoutByStyle(int style) {
        switch (style) {
            case ALEART_EDITTEXT:
                layoutId = R.layout.alert_editext_dialog;
                break;
        }
    }


    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        msg_titles = (String) title;
    }





    @Override
    public void show() {
        super.show();
        setCancelable(false);
    }

    @Override
    public void show(Object datas) {
        setTitle((String) datas);
        super.show(datas);
        setCancelable(false);
    }
}
