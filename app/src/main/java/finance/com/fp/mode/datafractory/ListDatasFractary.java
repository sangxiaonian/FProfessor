package finance.com.fp.mode.datafractory;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import finance.com.fp.R;
import finance.com.fp.mode.bean.Set_Item;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/11 10:19
 */
public class ListDatasFractary extends BaseFractory{

    public static final int LOAN_ITEM=1;
    public static ListDatasFractary  fractory;

    public ListDatasFractary(Context context) {
        super(context);
    }

    public static ListDatasFractary getInstance(Context context){
        if (fractory==null){
            synchronized (ListDatasFractary.class){
                if (fractory==null){
                    fractory=new ListDatasFractary(context);
                }
            }
        }
        return (ListDatasFractary) fractory;
    }

    public  List<Set_Item> creatData(int type){
        List<Set_Item> lists = new ArrayList<Set_Item>();
        switch (type){
            case LOAN_ITEM:
                lists= getLoan_Item();
        }

        return lists;
    }


    private List<Set_Item> getLoan_Item() {
        List<Set_Item> lists=new ArrayList<>();
        int[] icons = {R.mipmap.icon_cash
                ,R.mipmap.icon_peace
                ,R.mipmap.icon_themoneytreasure};
        String[] data = context.getResources().getStringArray(R.array.loan_item);
        for (int i = 0; i < icons.length; i++) {

            lists.add(new Set_Item(icons[i],"现金白卡-快速贷",data[i]));
        }

        return lists;
    }


}
