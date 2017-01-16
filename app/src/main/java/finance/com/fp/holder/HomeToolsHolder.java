package finance.com.fp.holder;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import em.sang.com.allrecycleview.holder.HeardHolder;
import em.sang.com.allrecycleview.listener.OnToolsItemClickListener;
import finance.com.fp.R;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.utlis.ToastUtil;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/3 11:17
 */
public class HomeToolsHolder extends HeardHolder {

    private List<Set_Item> lists;
    LinearLayout.LayoutParams params;
    private int size;
    private OnToolsItemClickListener listener;
    public void setOnToolsItemClickListener(OnToolsItemClickListener listener){
        this.listener=listener;
    }

    public HomeToolsHolder(Context context, List lists, int itemID) {
        super(context, lists, itemID);
        this.lists = lists;
        size = (int) context.getResources().getDimension(R.dimen.home_item_more_img_height);
        if (context instanceof OnToolsItemClickListener){
            this.listener= (OnToolsItemClickListener) context;
        }
    }

    public void setMagrin(int left, int top, int right, int bottom) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(left, top, right, bottom);
        itemView.setLayoutParams(params);
    }

    public void setImageSize(float size){
        this.size= (int) size;
    }

    public void setImagParams(LinearLayout.LayoutParams params) {
        this.params = params;
    }

    @Override
    public void initView(int position, final Context context) {
        super.initView(position, context);

        LinearLayout ll_icon = (LinearLayout) itemView.findViewById(R.id.ll_icon);
        LinearLayout ll_title = (LinearLayout) itemView.findViewById(R.id.ll_title);
        ll_icon.removeAllViews();
        ll_title.removeAllViews();

        int margin = (int) context.getResources().getDimension(R.dimen.home_item_more_marginLeft);
        for (int i = 0; i < lists.size(); i++) {
            LinearLayout.LayoutParams llparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            llparams.gravity = Gravity.CENTER;
            LinearLayout layout = new LinearLayout(context);
            layout.setGravity(Gravity.CENTER);
            layout.setOrientation(LinearLayout.VERTICAL);

            final ImageView icon = new ImageView(context);

            if (params == null) {
                params = new LinearLayout.LayoutParams(size, size, 1);
                params.setMargins(margin, 0, margin, 0);
            }

            icon.setLayoutParams(params);
            final Set_Item item = lists.get(i);
            icon.setImageResource(item.icon_id);
            ll_icon.addView(icon);
            final int id=i;
            icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null) {
                        listener.onItemClick(id, item);
                    }
                    else {
                        ToastUtil.showTextToast(context, item.title);

                    }
                }
            });

            LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(1, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            TextView tv = new TextView(context);
            tv.setLayoutParams(tvParams);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            tv.setText(item.title);
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            tv.setTextColor(context.getResources().getColor(R.color.text_home_item));
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    icon.performClick();
                }
            });
            ll_title.addView(tv);

        }
    }


}
