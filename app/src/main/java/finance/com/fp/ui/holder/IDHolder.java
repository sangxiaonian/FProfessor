package finance.com.fp.ui.holder;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import em.sang.com.allrecycleview.holder.CustomHolder;
import em.sang.com.allrecycleview.listener.OnToolsItemClickListener;
import finance.com.fp.R;
import finance.com.fp.mode.bean.Set_Item;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/2/8 15:55
 */
public class IDHolder<T> extends CustomHolder<T> {

    private OnToolsItemClickListener listener;
    private int cup;

    public void setOnClickListener(OnToolsItemClickListener listener) {
        this.listener = listener;
    }

    public IDHolder(Context context, List<T> lists, int itemID) {
        super(context, lists, itemID);
        cup = (int) context.getResources().getDimension(R.dimen.app_cut_big);
    }

    public void setCup(int cup) {
        this.cup = cup;
    }

    @Override
    public void initView(final int position, final List<T> datas, Context context) {
        super.initView(position, datas, context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        if (position == 4) {
            params.setMargins(0, 0, 0, cup);
        } else {
            params.setMargins(0, 0, 0, 0);
        }

        itemView.setLayoutParams(params);


        itemView.findViewById(R.id.img_card_item).setVisibility(View.GONE);
        TextView title = (TextView) itemView.findViewById(R.id.tv_card_item_big);
        Set_Item item = (Set_Item) datas.get(position);
        title.setText(item.title);

        TextView sub = (TextView) itemView.findViewById(R.id.tv_card_item_small);
        sub.setTextColor(context.getResources().getColor(R.color.set_item));
        sub.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) title.getTextSize());


        if (item.isCheck) {
            itemView.findViewById(R.id.more_icon).setVisibility(View.GONE);
            if (TextUtils.isEmpty(item.describe)) {
                sub.setText("请填写");
            } else {

                sub.setText(item.describe);
            }

        } else {

            if (TextUtils.isEmpty(item.describe)) {
                itemView.findViewById(R.id.more_icon).setVisibility(View.VISIBLE);
                sub.setText("请选择");
            } else {
                itemView.findViewById(R.id.more_icon).setVisibility(View.GONE);
                sub.setText(item.describe);
            }

        }


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position, datas.get(position));
                }
            }
        });

    }


}
