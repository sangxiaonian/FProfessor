package sang.com.xdialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.sang.viewfractory.utils.DeviceUtils;


/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/2/9 10:10
 */
public class AlertDialog extends XDialog<String> {

    private String msg_titles;

    public AlertDialog(Context context) {
        this(context, R.style.DialogCutTheme);
    }

    public AlertDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    private TextView txt_title, content;
    private EditText et;

    protected AlertDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int width = (int) DeviceUtils.getScreenWidth(getContext());
        dialog.getWindow().setLayout(width * 5 / 6, WindowManager.LayoutParams.WRAP_CONTENT);

    }

    @Override
    protected void onStart() {
        super.onStart();
        iniViews();
    }

    @Override
    protected void initEntryListener() {
        super.initEntryListener();
        bt_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entryListener != null) {
                    if (style == ALEART_EDITTEXT) {
                        entryListener.onClick(dialog, 0, et.getText().toString().trim());
                    } else {
                        entryListener.onClick(dialog, 0, content.getText().toString().trim());
                    }
                } else {
                    dismiss();
                }
            }
        });
    }

    @Override
    protected void iniViews() {
        super.iniViews();
        txt_title = (TextView) findViewById(R.id.txt_title);

        if (txt_title!=null) {
            if (TextUtils.isEmpty(msg_titles)) {
                txt_title.setVisibility(View.GONE);
            } else {
                txt_title.setVisibility(View.VISIBLE);
                txt_title.setText(msg_titles);
            }
        }

        if (style == ALEART_EDITTEXT) {
            et = (EditText) findViewById(R.id.txt_msg);
            if (!TextUtils.isEmpty(datas)) {
                et.setVisibility(View.VISIBLE);
                et.setHint(datas);
                et.requestFocus();

            }
        } else {
            content = (TextView) findViewById(R.id.txt_msg);
            if (content!=null) {
                if (TextUtils.isEmpty(datas)) {
                    content.setVisibility(View.GONE);
                } else {
                    content.setVisibility(View.VISIBLE);
                    content.setText(datas);
                }
            }

        }
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        setContentView(R.layout.alert_dialog);
    }


    @Override
    protected void changeLayoutByStyle(int style) {
        switch (style) {
            case ALEART_EDITTEXT:
                layoutId = R.layout.alert_editext_dialog;
                break;
            case ALEART_ONLY_ENTRY:
                layoutId = R.layout.alert_only_entry_dialog;
                break;
        }
    }


    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        msg_titles = (String) title;
    }

    @Override
    public void setDatas(String datas) {
        super.setDatas(datas);
        this.datas = datas;
    }

    @Override
    public void show() {
        super.show();

    }
}
