package finance.com.fp.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import em.sang.com.allrecycleview.PullRecycleView;
import em.sang.com.allrecycleview.adapter.DefaultAdapter;
import em.sang.com.allrecycleview.inter.DefaultRefrushListener;
import finance.com.fp.BasisActivity;
import finance.com.fp.R;
import finance.com.fp.mode.bean.Config;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.bean.TranInfor;
import finance.com.fp.presenter.HomeSonPerComl;
import finance.com.fp.presenter.inter.HomeSonPreInter;
import finance.com.fp.ui.inter.HomeSonView;
import finance.com.fp.utlis.ToastUtil;

public class HomeSonActivity extends BasisActivity implements HomeSonView {

    private PullRecycleView rc;
    private HomeSonPreInter pre;
    private DefaultAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refush);
        pre = new HomeSonPerComl(this);
        initView();
        initData();

    }

    @Override
    public void initView() {
        super.initView();
        rc = (PullRecycleView) findViewById(R.id.rc);

    }

    @Override
    public void initData() {
        super.initData();
        pre.getTranInfor(this, Config.infors);
        rc.setLayoutManager(pre.getManager(this));
        rc.addItemDecoration(pre.getDivider(this));
        adapter = (DefaultAdapter) pre.getAdapter(this);
        rc.setAdapter(adapter);
        rc.setRefrushListener(new DefaultRefrushListener() {
           @Override
           public void onLoading() {

               pre.setDatas(HomeSonActivity.this);
           }
        });
        rc.setLoading();

    }


    @Override
    public void initTitle(String title) {
        initToolBar(title);
        setColor(this, getResources().getColor(R.color.statucolor));
    }

    @Override
    public void msgItemClick(View itemView, final Set_Item item) {

        Intent intent =new Intent(this, NoticeActivity.class);
        TranInfor infor  =new TranInfor();
        infor.title=item.title;
        infor.content=item.content;
        infor.updatetime=item.updatetime;
        intent.putExtra(Config.infors,infor);
        startActivity(intent);

    }

    @Override
    public void creditItemClick(View itemView, final Set_Item item) {


        ToastUtil.showTextToast(item.title);
    }

    @Override
    public void utilityItemClick(View itemView, final Set_Item item) {

        ToastUtil.showTextToast(item.title);

    }

    @Override
    public void initItemView(View itemView, Set_Item item, int position) {
        ImageView img = (ImageView) itemView.findViewById(R.id.img_item);
        TextView title = (TextView) itemView.findViewById(R.id.tv_item);
        TextView sub = (TextView) itemView.findViewById(R.id.tv_sub_item);
        TextView time = (TextView) itemView.findViewById(R.id.tv_item_tme);

        View flagView = itemView.findViewById(R.id.icon_flag);
        if (img != null) {

            if (!TextUtils.isEmpty(item.img_url)) {
                Glide.with(this)
                        .load(item.img_url)
                        .placeholder(item.placeholder)
                        .error(item.faildId)
                        .crossFade()
                        .into(img);
            } else if (item.icon_id > 0) {
                Glide.with(this)
                        .load(item.icon_id)
                        .placeholder(item.placeholder)
                        .error(item.faildId)
                        .crossFade().into(img);
            }
        }

        if (time!=null&&!TextUtils.isEmpty(item.updatetime)){
            time.setText(item.updatetime);
        }

        if (!TextUtils.isEmpty(item.title) && title != null) {
            title.setText(item.title);
        }
        if (sub != null && item.describe != null) {
            sub.setText(item.describe);
        }
        if (flagView != null) {
            if (position < 3) {

                flagView.setVisibility(View.VISIBLE);
                if (flagView instanceof TextView) {
                    ((TextView) flagView).setText(position + 1 + "");
                }

            } else {
                flagView.setVisibility(View.INVISIBLE);
            }

        }
        pre.onItemClick(itemView, item);

    }


    @Override
    public void showPhoneDialog(Set_Item data) {

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        View inflate = inflater.inflate(R.layout.dialog_item, null);
        dialog.setView(inflate);
        final AlertDialog show = dialog.show();
        show.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.transparent)));
        TextView tv_phone = (TextView) inflate.findViewById(R.id.tv_phone);
        TextView tv_phonenumber = (TextView) inflate.findViewById(R.id.tv_phonenumber);
        TextView tv_credit = (TextView) inflate.findViewById(R.id.tv_credit);
        TextView tv_creditnumber = (TextView) inflate.findViewById(R.id.tv_creditnumber);
        LinearLayout phone_call = (LinearLayout) inflate.findViewById(R.id.ll_phone);
        LinearLayout credit_call = (LinearLayout) inflate.findViewById(R.id.ll_credit);

        try {
            String[] split = data.describe.split("\n");
            final String[] phone = split[0].split(":");
            tv_phone.setText(phone[1]);
            tv_phonenumber.setText(phone[0]);
            if (split.length > 1) {
                credit_call.setVisibility(View.VISIBLE);
                final String[] credit = split[1].split(":");
                tv_credit.setText(credit[1]);
                tv_creditnumber.setText(credit[0]);
                credit_call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        show.dismiss();
                        Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + credit[1].replace("-", "")));
                        startActivity(intent);

                    }
                });
            } else {
                credit_call.setVisibility(View.GONE);
            }
            final String phoneNumber = phone[1];
            phone_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show.dismiss();
                    Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneNumber));
                    startActivity(intent);

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void allBalanceItemClick(Set_Item item) {
        ToastUtil.showTextToast(item.title);
    }

    @Override
    public void applyQueryItemClick(View itemView, Set_Item item) {
        ToastUtil.showTextToast(item.title);
    }

    @Override
    public void hotapplyItemClick(View itemView, Set_Item item) {
        ToastUtil.showTextToast(item.title);
    }

    @Override
    public void loan_strage_item(View itemView, Set_Item item) {
        ToastUtil.showTextToast(item.title);

    }

    @Override
    public void loan_one_key_item(View itemView, Set_Item item) {

        TranInfor tranInfor = new TranInfor();
        tranInfor.title=item.title;
        tranInfor.describe=item.describe;
        Intent intent = new Intent(this,ImportDetailActivity.class);
        intent.putExtra(Config.infors,tranInfor);
        startActivity(intent);
    }



    @Override
    public void loadSuccess() {

        rc.loadSuccess();

    }

    @Override
    public void loadFail() {
        rc.loadFail();

    }

}
