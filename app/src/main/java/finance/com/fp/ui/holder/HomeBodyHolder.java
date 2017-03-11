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
import finance.com.fp.mode.bean.LoanSearchBean;
import finance.com.fp.mode.http.Config;
import finance.com.fp.ui.LoanConditionActivity;
import finance.com.fp.utlis.GlideUtils;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/3 10:22
 */
public class HomeBodyHolder extends CustomHolder<LoanSearchBean> {
    public HomeBodyHolder(Context context, List<LoanSearchBean> lists, int itemID) {
        super(context, lists, itemID);
    }

    @Override
    public void initView(final int position, final List<LoanSearchBean> datas, final Context context) {
        super.initView(position, datas, context);
        ImageView img = (ImageView) itemView.findViewById(R.id.img_item_loan);
        TextView title = (TextView) itemView.findViewById(R.id.tv_title_item_loan);
        TextView time = (TextView) itemView.findViewById(R.id.tv_title_sub_item_loan);


        final LoanSearchBean item = (LoanSearchBean) datas.get(position);
        if (img != null && !TextUtils.isEmpty(item.getThumb())) {

            GlideUtils.loadImage(context, img, item.getThumb());
        }

        title.setText(item.getTitle());
        time.setText(item.getZhaiyao());
        itemView.findViewById(R.id.click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    Intent intent = new Intent(context, LoanConditionActivity.class);
                    intent.putExtra(Config.infors, item);
                    context.startActivity(intent);

            }
        });
    }
}