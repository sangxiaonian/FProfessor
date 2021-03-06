package finance.com.fp.ui.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import finance.com.fp.R;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.utlis.GlideUtils;
import finance.com.fp.utlis.Utils;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/3 11:17
 */
public class HomeToolsHolder extends BasicHolder {

    private List<Set_Item> lists;
    LinearLayout.LayoutParams params;


    private int itemId=R.layout.view_tools_card;


    public void setView(int itemId) {
        this.itemId = itemId;
    }

    public HomeToolsHolder(Context context, List lists, int itemID) {
        super(context, lists, itemID);
        this.lists = lists;
    }



    public void setImagParams(LinearLayout.LayoutParams params) {
        this.params = params;
    }

    @Override
    public void initView(int position, final Context context) {
        super.initView(position, context);

      LinearLayout  ll_icon= (LinearLayout) itemView.findViewById(R.id.ll_icon);
        ll_icon.removeAllViews();
        for (int i = 0; i < lists.size(); i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1);
            LinearLayout ll = (LinearLayout) View.inflate(context, itemId,  null);
            ll.setLayoutParams(params);
            final ImageView icon = (ImageView) ll.findViewById(R.id.img_tools);
            final Set_Item item = lists.get(i);

            GlideUtils.loadImage(context,icon,item.icon_id);
            final int id = i;
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        if(!Utils.isLogion(context)){
                            Utils.showLoginDialog(context);
                        }else {
                            listener.onItemClick(id, item);
                        }
                    }
                }
            });

            TextView tv = (TextView) ll.findViewById(R.id.tv_tools);

            tv.setText(item.title);

            ll_icon.addView(ll);

        }
    }


}
