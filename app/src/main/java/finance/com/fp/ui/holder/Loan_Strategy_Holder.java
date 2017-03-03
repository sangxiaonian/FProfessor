package finance.com.fp.ui.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import finance.com.fp.R;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.bean.TranInfor;
import finance.com.fp.mode.http.Config;
import finance.com.fp.ui.ShowDetailActivity;
import finance.com.fp.utlis.Utils;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/17 11:09
 */
public class Loan_Strategy_Holder extends BasicHolder<Set_Item> implements View.OnClickListener {
    public Loan_Strategy_Holder(Context context, int itemID, Set_Item data) {
        super(context, itemID, data);
    }

    @Override
    public void initView(int position, Context context) {
        super.initView(position, context);
        ImageView im1 = (ImageView) itemView.findViewById(R.id.img1);
        ImageView im2 = (ImageView) itemView.findViewById(R.id.img2);
        ImageView im3 = (ImageView) itemView.findViewById(R.id.img3);

        im1.setOnClickListener(this);
        im2.setOnClickListener(this);
        im3.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String title ="";
        String content ="";
        switch (v.getId()) {
            case R.id.img1:
                  title ="谈谈中介";
                  content = Utils.getResStr(R.string.intermediary);
                break;
            case R.id.img2:
                  title ="把握申请节奏";
                  content =context.getString(R.string.apply_rhythm);
                break;
            case R.id.img3:
                  title ="上征信那点事儿";
                  content =Utils.getResStr(R.string.crite);
                break;
        }

        Intent intent = new Intent(context, ShowDetailActivity.class);
        TranInfor infor = new TranInfor();
        infor.title =  title;
        infor.type =  1;
        infor.content = content;
        infor.describe = content;
        intent.putExtra(Config.infors, infor);
        context.startActivity(intent);

    }
}
