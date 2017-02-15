package finance.com.fp.ui.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import em.sang.com.allrecycleview.holder.CustomHolder;
import finance.com.fp.R;
import finance.com.fp.mode.bean.Config;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.bean.TranInfor;
import finance.com.fp.mode.datafractory.CardDataFractory;
import finance.com.fp.ui.FeedbackActivity;
import finance.com.fp.ui.HomeSonActivity;
import finance.com.fp.ui.IDActivity;
import finance.com.fp.ui.Loan_Search_Activity;
import finance.com.fp.ui.Set_Activity;
import finance.com.fp.utlis.ToastUtil;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/3 10:22
 */
public class SetBodyHolder extends CustomHolder<Set_Item> {
    public SetBodyHolder(Context context, List lists, int itemID) {
        super(context, lists, itemID);
    }

    @Override
    public void initView(final int position, final List<Set_Item> datas, final Context context) {
        super.initView(position, datas, context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        if ((position - 1) % 2 == 0) {
            params.setMargins(0, 0, 0, (int) context.getResources().getDimension(R.dimen.home_item_time_margin));
        } else {
            params.setMargins(0, 0, 0, 0);
        }

        itemView.setLayoutParams(params);
        final Set_Item item = datas.get(position);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.img_set);
        TextView tv = (TextView) itemView.findViewById(R.id.tv_set);
        Glide.with(context)
                .load(item.icon_id)
                .placeholder(item.placeholder)
                .error(item.faildId)
                .centerCrop()
                .crossFade()
                .into(imageView);
        tv.setText(item.title);
        itemView.findViewById(R.id.more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToActivity(position, context);
            }
        });

    }

    private void jumpToActivity(int position, Context context) {
        Class c = null;
        Intent intent = new Intent();
        TranInfor tranInfor = new TranInfor();

        switch (position) {
            case 0://办卡进度

                tranInfor.activity_id = 1;
                tranInfor.item_id = CardDataFractory.APPLY_PROGRESS;
                tranInfor.title = context.getString(R.string.card_pro);
                intent.putExtra(Config.infors, tranInfor);
                c=HomeSonActivity.class;
                break;
            case 1://网贷进度
                c = Loan_Search_Activity.class;
                break;
            case 2://个人信息
                c = IDActivity.class;
                break;
            case 3://消息中心
                c=HomeSonActivity.class;
                tranInfor.activity_id = 0;
                tranInfor.item_id = 4;
                tranInfor.title = context.getString(R.string.msg_center);
                intent.putExtra(Config.infors,tranInfor);
                break;
            case 4://意见反馈
                c = FeedbackActivity.class;
                break;
            case 5:
                c = Set_Activity.class;
                break;

        }

        if (c != null) {
            intent.setClass(context, c);
            context.startActivity(intent);
        } else {
            ToastUtil.showTextToast(context, "功能尚未开放");
        }
    }
}
