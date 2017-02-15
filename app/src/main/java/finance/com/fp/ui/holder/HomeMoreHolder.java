package finance.com.fp.ui.holder;

import android.content.Context;
import android.view.View;

import java.util.List;

import finance.com.fp.R;
import finance.com.fp.utlis.ToastUtil;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/3 11:17
 */
public class HomeMoreHolder extends BasicHolder {


    public HomeMoreHolder(Context context, List lists, int itemID) {
        super(context, lists, itemID);
    }


    @Override
    public void initView(final int position, final Context context) {
        super.initView(position, context);
        View view = itemView.findViewById(R.id.more);
        View click = itemView;
        if (itemView.isClickable()) {
            click = itemView;
        } else if (view != null && view.isClickable()) {
            click = view;
        }
        click.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         if (listener == null) {
                                             ToastUtil.showTextToast(context, "更多被点击了!");
                                         } else {
                                             listener.onItemClick(position,null);
                                         }
                                     }
                                 }

        );

    }
}
