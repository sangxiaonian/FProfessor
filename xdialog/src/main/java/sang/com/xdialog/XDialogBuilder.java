package sang.com.xdialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.sang.viewfractory.listener.OnScrollSelectListener;

import sang.com.xdialog.inter.OnEntryClickListener;
import sang.com.xdialog.utils.JLog;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/2/8 16:10
 */
public class XDialogBuilder<T> {
    private final Context context;
    protected OnScrollSelectListener listener;
    protected int layoutId;

    /**
     * 底部滚动选择
     */
    public static final int ALEART_DIALOG = 0;
    public static final int PIKER_DIALOG = 1;
    public static final int SELECT_DIALOG = 2;
    public static final int LOAD_DIALOG = 3;


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
    public static final int PICK_NOCYCLE =4 ;

    /**
     * selectDialog 宽布满整个屏幕
     */
    public static final int SELECT_WIDTH_FULL=5;

    protected OnEntryClickListener entryListener;
    protected int style;
    protected View view;
    private int dialogStyle;
    private int animationsStyle;
    private int themeID;
    private String title;

    protected String be_entry_name,bt_cancle_name;

    public String getTitle() {
        return title;
    }

    public XDialogBuilder setTitle(String title) {
        this.title = title;
        return this;
    }



    public XDialogBuilder(Context context ) {
        this.context=context;


    }




    public Context getContext() {
        return context;
    }

    public OnScrollSelectListener getListener() {
        return listener;
    }

    public XDialogBuilder setListener(OnScrollSelectListener listener) {
        this.listener = listener;
        return this;
    }

    /**
     * 获取布局id
     * @return
     */
    public int getLayoutId() {
        return layoutId;
    }

    /**
     * 设置布局Id
     * @param layoutId
     */
    public XDialogBuilder setLayoutId(int layoutId) {
        this.layoutId = layoutId;
        return this;
    }


    /**
     * 确认监听
     * @return
     */
    public OnEntryClickListener getEntryListener() {
        return entryListener;
    }
    /**
     * 确认监听
     * @return
     */
    public XDialogBuilder setEntryListener(OnEntryClickListener entryListener) {
        this.entryListener = entryListener;
        return this;
    }


    /**
     * Xdialog 类型
     * @return
     */
    public int getStyle() {
        return style;
    }
    /**
     * Xdialog 类型
     * @return
     */
    public XDialogBuilder setStyle(int style) {
        this.style = style;
        return this;
    }



    public View getView() {
        return view;
    }

    /**
     * 显示的布局(优先显示)
     * @param view
     */
    public XDialogBuilder setView(View view) {
        this.view = view;
        return this;
    }

    /**
     * XDialog子类的具体类型
     * @return
     */
    public int getDialogStyle() {
        return dialogStyle;
    }
    /**
     * 每个XDialog子类的具体类型
     * @return
     */
    public XDialogBuilder setDialogStyle(int dialogStyle) {
        this.dialogStyle = dialogStyle;
        return this;
    }

    /**
     * 确认键名字
     * @return
     */
    public String getBe_entry_name() {
        return be_entry_name;
    }
    /**
     * 确认键名字
     * @return
     */
    public XDialogBuilder setBe_entry_name(String be_entry_name) {
        this.be_entry_name = be_entry_name;
        return this;
    }
    /**
     * 取消键名字
     * @return
     */
    public String getBt_cancle_name() {
        return bt_cancle_name;
    }
    /**
     * 取消键名字
     * @return
     */
    public XDialogBuilder setBt_cancle_name(String bt_cancle_name) {
        this.bt_cancle_name = bt_cancle_name;
        return this;
    }

    /**
     * 动画显示风格
     * @return
     */
    public int getAnimationstyle() {
        return animationsStyle;
    }

    public XDialogBuilder setAnimationstyle(int animationstyle) {
        this.animationsStyle = animationstyle;
        return this;
    }
    /**
     * 社知数据
     * @return
     */
    public T getDatas() {
        return datas;
    }
    /**
     * 设置数据
     * @return
     */
    public XDialogBuilder setDatas(T datas) {
        this.datas = datas;
        return this;
    }


    /**
     * 设置按钮的文字信息 默认为"取消" 和"确认" 在show之前调用
     * @param entry 确认按钮
     * @param cancle 取消按钮
     */
    public XDialogBuilder setButtonName(String entry, String cancle){
        if (!TextUtils.isEmpty(entry)){
            be_entry_name=entry;
        }
        if (!TextUtils.isEmpty(cancle)){
            bt_cancle_name=entry;
        }
        return this;
    }
    private T datas;

   public XDialog builder(){
       JLog.i("------------"+themeID+"--------------");
       XDialog dialog;
       if (themeID>0) {
           dialog = DialogFactory.getInstance().creatDiaolg(context,dialogStyle,themeID);
       } else {
           dialog = DialogFactory.getInstance().creatDiaolg(context,dialogStyle);
       }
       if (view==null&&layoutId!=0){
           dialog.setContentView(layoutId);
       }
       if (view!=null){
           dialog.setContentView(view);
       }
       dialog.setTitle(title);
       dialog.setDatas(datas);
       dialog.setButtonName(be_entry_name,bt_cancle_name);
       dialog.setStyle(style);
       dialog.setAnimationsStyle(animationsStyle);
        return dialog;
    }


    public XDialogBuilder setThemeID(int themeID) {
        this.themeID = themeID;
        return this;
    }
}
