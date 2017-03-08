package sang.com.xdialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.sang.viewfractory.utils.DeviceUtils;
import com.sang.viewfractory.view.PickerScrollView;

import java.util.ArrayList;
import java.util.List;


/**
 * Description：
 *
 * @Author 桑小年
 * @Data： 2017/2/6 17:48
 */
public class PickerDialog extends XDialog<List<String>> {
    private Activity activity;
    private PickerScrollView pickerScrollView;
    private List<String> lists;



    public PickerDialog(Context context) {
        this(context,R.style.DialogTheme);
        initView(context);
    }

    public PickerDialog(Context context, int themeResId) {
        super(context, themeResId);
        initView(context);
    }

    protected PickerDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView(context);
    }

    private void initView(Context context){
        activity= (Activity) context;
        lists=new ArrayList<>();
    }

    @Override
    public void setOnCancelListener(OnCancelListener listener) {
        super.setOnCancelListener(listener);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int  width= (int) DeviceUtils.getScreenWidth(getContext());
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.copyFrom(getWindow().getAttributes());
        //设置dialog的界面宽度
        params.width = width;
        //设置dialog高度为包裹内容
        params.height =width*3/5;
        //设置dialog的重心
        params.gravity = Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL;
        //dialog.getWindow().setLayout(width-(width/6), LayoutParams.WRAP_CONTENT);
        //用这个方法设置dialog大小也可以，但是这个方法不能设置重心之类的参数，推荐用Attributes设置
        //最后把这个参数对象设置进去，即与dialog绑定
        getWindow().setAttributes(params);
        pickerScrollView = (PickerScrollView) findViewById(R.id.picker);
        window.setWindowAnimations(R.style.dialog_down);
        initDialog();


    }

    @Override
    protected void initContentView() {
        super.initContentView();
        pickerScrollView = new PickerScrollView(getContext());
        setContentView(pickerScrollView);
    }

    private void initDialog(){
        if (pickerScrollView!=null) {
            if (listener!=null){
                pickerScrollView.setOnPickerSelecterListener(listener);
            }
            if (lists!=null){
                pickerScrollView.setDatas(lists);
            }
        }


    }

    @Override
    protected void initEntryListener() {

        if (bt_entry!=null){
            bt_entry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (entryListener!=null){
                        entryListener.onClick(dialog,pickerScrollView.getSelect(),pickerScrollView.getCurrentData());
                    }else {
                        dismiss();
                    }
                }
            });
        }
    }

    @Override
    public void setDatas(List<String> datas) {
        super.setDatas(datas);
        if (datas!=null){
            lists.clear();
            lists.addAll(datas);
        }
    }

    @Override
    public void show(List<String> datas) {
        if (datas!=null){
            lists.clear();
            lists.addAll(datas);
        }
        super.show(datas);
    }

    @Override
    public void showStyle(int style) {

        super.showStyle(style);
    }

    @Override
    protected void changeLayoutByStyle(int style) {
        super.changeLayoutByStyle(style);
        switch (style){
            case BUTTON_UP:
                layoutId =R.layout.picker_button_up;
                break;
        }
    }
}
