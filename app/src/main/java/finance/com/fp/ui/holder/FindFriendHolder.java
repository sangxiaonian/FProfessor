package finance.com.fp.ui.holder;

import android.content.Context;
import android.view.View;

import java.util.List;

import finance.com.fp.R;
import finance.com.fp.utlis.Utils;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/3 11:17
 */
public class FindFriendHolder extends BasicHolder{


    public FindFriendHolder(Context context, List lists, int itemID) {
        super(context, lists, itemID);
    }

    @Override
    public void initView(final int position, final Context context) {
        super.initView(position, context);
        itemView.findViewById(R.id.img_red).setVisibility(View.VISIBLE);


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Utils.isLogion(context)){
                    Utils.showLoginDialog(context);
                }else {
                    listener.onItemClick(position, data);
                }
            }
        });
    }
}
