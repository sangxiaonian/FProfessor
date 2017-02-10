package sang.com.xdialog;

import android.content.Context;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/2/8 16:11
 */
public class DialogFactory {

    private XDialog dialog;
    private Context context;
    private static DialogFactory factory;
    /**
     * 底部滚动选择
     */
    public static final int ALEART_DIALOG = 0;
    public static final int PIKER_DIALOG = 1;
    public static final int SELECT_DIALOG = 2;


    private DialogFactory(Context context){
       this.context=context;
    }



    public static DialogFactory getInstance(Context context){
        if (factory==null){
            synchronized (DialogFactory.class){
                if (factory==null){
                    factory=new DialogFactory(context);
                }
            }
        }
        return factory;
    }

    public XDialog creatDiaolg(Context context, int style) {
        XDialog dialog ;
        switch (style){
            case PIKER_DIALOG:
                dialog=new PickerDialog(context);
                break;
            case ALEART_DIALOG:
                dialog=new AlertDialog(context);
                break;
            case SELECT_DIALOG:
                dialog=new SelectDialog(context);
                break;
            default:
                dialog=new PickerDialog(context);
                break;
        }
        return dialog;
    }

    public XDialog creatDiaolg (Context context) {
        return creatDiaolg(context,ALEART_DIALOG);
    }

}
