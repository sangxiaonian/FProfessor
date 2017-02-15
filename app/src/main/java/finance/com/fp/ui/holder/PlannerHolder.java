package finance.com.fp.ui.holder;

import android.content.Context;
import android.view.View;

import java.util.List;

import em.sang.com.allrecycleview.holder.CustomHolder;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/12 11:56
 */
public class PlannerHolder extends CustomHolder {

    public PlannerHolder(View itemView) {
        super(itemView);
    }

    public PlannerHolder(List datas, View itemView) {
        super(datas, itemView);
    }

    public PlannerHolder(Context context, List lists, int itemID) {
        super(context, lists, itemID);
    }


}
