package finance.com.fp.ui.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import finance.com.fp.R;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.utlis.GlideUtils;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/3 10:22
 */
public class CardMoreHolder extends BasicHolder<Set_Item> {




    public CardMoreHolder(Context context, int itemID, Set_Item data) {
        super(context, itemID, data);
    }


    @Override
    public void initView(final int position,final Context context) {
        super.initView(position, context);
        final Set_Item item =  data;
        ImageView imageView = (ImageView) itemView.findViewById(R.id.img_card_item);
        TextView tv_title = (TextView) itemView.findViewById(R.id.tv_card_item_big);
        TextView tv_describe= (TextView) itemView.findViewById(R.id.tv_card_item_small);
        if (item.icon_id>0) {
            imageView.setVisibility(View.VISIBLE);

            GlideUtils.loadImage(context,imageView,item.icon_id);
        }else {
            imageView.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(item.title)){
            tv_title.setText(item.title);
        }
         if (!TextUtils.isEmpty(item.describe)){
             tv_describe.setText(item.describe);
        }


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener==null) {

                } else {
                    listener.onItemClick(position,item);
                }
            }
        });
    }


}
