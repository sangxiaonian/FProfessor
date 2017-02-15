package finance.com.fp.ui.holder;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.List;

import finance.com.fp.R;
import finance.com.fp.mode.bean.Set_Item;

/**
 * Description：办卡页面轮播文字
 *
 * @Author：桑小年
 * @Data：2017/1/5 9:41
 */
public class CardNotifiHolder extends BasicHolder {

    private TextSwitcher ts;
    private int item;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (item>=datas.size()){
                item=0;
            }

            ts.setText(((Set_Item)datas.get(item)).title);

//            ts.showNext();
            handler.sendEmptyMessageDelayed(0,3000);
            item++;
        }
    };


    public CardNotifiHolder(Context context, List lists, int itemID) {
        super(context, lists, itemID);
    }

    @Override
    public void initView(int position, final Context context) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,0, (int) context.getResources().getDimension(R.dimen.home_item_time_margin));

        if (ts!=null){
            return;
        }
        ts= (TextSwitcher) itemView.findViewById(R.id.ts_card);

        ts.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(context);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1);
                textView.setLayoutParams(params);
                textView.setMaxLines(1);
                textView.setEllipsize(TextUtils.TruncateAt.END);
                textView.setTextColor(context.getResources().getColor(R.color.text_card_item_more));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimensionPixelSize(R.dimen.home_item_time_text));
                return textView;
            }
        });

        // 设置切入动画
        ts.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_bottom_in));
        // 设置切出动画
        ts.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_top_out));
        handler.sendEmptyMessage(0);

    }
    public void stopCarousel() {
        if (handler != null && handler.hasMessages(0)) {
            handler.removeMessages(0);
        }
    }

    public void startCarousel() {
        if (handler != null) {
            if (handler.hasMessages(0)) {
                handler.removeMessages(0);
            }
            handler.sendEmptyMessageDelayed(0, 1000);
        }
    }


}
