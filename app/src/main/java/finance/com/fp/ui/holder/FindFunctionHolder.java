package finance.com.fp.ui.holder;

import android.content.Context;
import android.widget.RadioGroup;

import java.util.List;

import finance.com.fp.R;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/3 11:17
 */
public class FindFunctionHolder extends BasicHolder{


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
                        listener.onItemClick(0,null);
                        break;
                    case R.id.rb_lao:
                        listener.onItemClick(1,null);
                        break;
                }
            }
        });


    }


}
