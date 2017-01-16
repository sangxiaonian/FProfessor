package finance.com.fp.holder;

import android.content.Context;
import android.view.View;

import java.util.List;

import em.sang.com.allrecycleview.holder.HeardHolder;
import finance.com.fp.utlis.ToastUtil;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/3 11:17
 */
public class FindFriendHolder extends HeardHolder{


    public FindFriendHolder(Context context, List lists, int itemID) {
        super(context, lists, itemID);
    }

    @Override
    public void initView(int position,  final Context context) {
        super.initView(position, context);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showTextToast(context,"朋友圈子");
            }
        });
    }
}
