package finance.com.fp.ui.holder;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import em.sang.com.allrecycleview.holder.CustomHolder;
import finance.com.fp.R;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.bean.TranInfor;
import finance.com.fp.mode.http.Config;
import finance.com.fp.ui.ShowDetailActivity;
import finance.com.fp.utlis.GlideUtils;
import finance.com.fp.utlis.Utils;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/3 10:22
 */
public class HomeFindHolder extends CustomHolder<Set_Item> {
    public HomeFindHolder(Context context, List<Set_Item> lists, int itemID) {
        super(context, lists, itemID);
    }



    @Override
    public void initView(final int position, final List<Set_Item> datas, final Context context) {
        super.initView(position, datas, context);
        ImageView img = (ImageView) itemView.findViewById(R.id.img_item_loan);
        TextView title = (TextView) itemView.findViewById(R.id.tv_title_item_loan);
        TextView time = (TextView) itemView.findViewById(R.id.tv_title_sub_item_loan);

        final Set_Item item = datas.get(position);
        if (img != null && !TextUtils.isEmpty(item.img_url)) {

            GlideUtils.loadImage(context,img,item.img_url);
        }

        title.setText(item.title);
        time.setText(item.updatetime);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Utils.isLogion(context)){
                    Utils.showLoginDialog(context);
                }else {
                    Intent intent = new Intent(context, ShowDetailActivity.class);
                    TranInfor infor = new TranInfor();
                    infor.title = item.title;
                    infor.content = item.content;
                    infor.describe = item.describe;
                    intent.putExtra(Config.infors, infor);
                    context.startActivity(intent);
                }
            }
        });
    }
}


