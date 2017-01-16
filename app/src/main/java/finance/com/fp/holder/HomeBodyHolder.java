package finance.com.fp.holder;

import android.content.Context;
import android.view.View;

import java.util.List;

import em.sang.com.allrecycleview.holder.CustomHolder;
import finance.com.fp.utlis.ToastUtil;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/3 10:22
 */
public class HomeBodyHolder extends CustomHolder {
    public HomeBodyHolder(Context context, List lists, int itemID) {
        super(context, lists, itemID);
    }

    @Override
    public void initView(final int position, final List datas, final Context context) {
        super.initView(position, datas, context);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showTextToast(context,(String) datas.get(position));
            }
        });
    }
}
