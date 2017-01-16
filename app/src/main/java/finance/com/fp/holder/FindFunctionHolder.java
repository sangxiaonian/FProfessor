package finance.com.fp.holder;

import android.content.Context;
import android.widget.RadioGroup;

import java.util.List;

import em.sang.com.allrecycleview.holder.HeardHolder;
import finance.com.fp.R;
import finance.com.fp.utlis.ToastUtil;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/3 11:17
 */
public class FindFunctionHolder extends HeardHolder{


    public FindFunctionHolder(Context context, List lists, int itemID) {
        super(context, lists, itemID);
    }

    @Override
    public void initView(int position, final Context context) {
        super.initView(position, context);
        RadioGroup rg = (RadioGroup) itemView.findViewById(R.id.rg_find);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_fun:
                        ToastUtil.showTextToast(context,"最新金融口子");
                        break;
                    case R.id.rb_lao:
                        ToastUtil.showTextToast(context,"捞偏门");
                        break;
                }
            }
        });

    }


}
