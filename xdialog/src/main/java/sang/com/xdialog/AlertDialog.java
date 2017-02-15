package sang.com.xdialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import sang.com.xdialog.utils.DeviceUtils;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/2/9 10:10
 */
public class AlertDialog extends XDialog {
    private String msg_titles,msg_content;
    public AlertDialog(Context context) {
        this(context,R.style.DialogCutTheme);
//        super(context);
    }

    public AlertDialog(Context context, int themeResId) {
        super(context, themeResId);
    }
    private TextView txt_title,content;
    private EditText et;

    protected AlertDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int  width= (int) DeviceUtils.getScreenWidth(getContext());
        dialog.getWindow().setLayout(width*5/6, WindowManager.LayoutParams.WRAP_CONTENT);

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
                if (entryListener!=null){
                    if (style==ALEART_EDITTEXT) {
                        entryListener.onClick(dialog, 0, et.getText().toString().trim());
                    }else {
                        entryListener.onClick(dialog, 0, content.getText().toString().trim());
                    }
                }else {

                }
            }
        });
    }

    @Override
    protected void iniViews() {
        super.iniViews();
        txt_title= (TextView) findViewById(R.id.txt_title);

        if (TextUtils.isEmpty(msg_titles)){
            txt_title.setVisibility(View.GONE);
        }else {
            txt_title.setVisibility(View.VISIBLE);
            txt_title.setText(msg_titles);
        }
        if (style==ALEART_EDITTEXT){
            et= (EditText) findViewById(R.id.txt_msg);
            if (!TextUtils.isEmpty(msg_content)){
                et.setVisibility(View.VISIBLE);
                et.setHint(msg_content);
                et.requestFocus();

            }
        }else {
            content= (TextView) findViewById(R.id.txt_msg);
            if (TextUtils.isEmpty(msg_content)){
                content.setVisibility(View.GONE);
            }else {
                content.setVisibility(View.VISIBLE);
                content.setText(msg_content);
            }

        }
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        setContentView(R.layout.alert_dialog);
    }

    @Override
    public void showStyle(int style) {
        super.showStyle(style);
    }

    @Override
    protected void changeLayoutByStyle(int style) {
        switch (style){
            case ALEART_EDITTEXT:
                layoutId=R.layout.alert_editext_dialog;
                break;
        }
    }


    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        msg_titles= (String) title;
    }

    @Override
    public void setDatas(Object datas) {
        super.setDatas(datas);
        msg_content= (String) datas;

    }


}