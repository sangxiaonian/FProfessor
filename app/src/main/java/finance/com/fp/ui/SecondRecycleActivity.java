package finance.com.fp.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import finance.com.fp.BasisActivity;
import finance.com.fp.R;
import finance.com.fp.mode.bean.Config;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.presenter.SecondRecyclePreComl;
import finance.com.fp.presenter.inter.SecondRecyclePre;
import finance.com.fp.ui.inter.SecondRecycleInter;
import finance.com.fp.utlis.DividerGridItemDecoration;

public class SecondRecycleActivity extends BasisActivity implements SecondRecycleInter {
    RequestManager builder;
    private RecyclerView rc;
    private SecondRecyclePre pre;
    private String tranInfo = Config.infors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_recycle);
        setColor(this, getResources().getColor(R.color.white));
        rc = (RecyclerView) findViewById(R.id.rc);
        initData();

    }

    private void initData() {
        pre = new SecondRecyclePreComl(this);
        builder = Glide.with(this);
        pre.getTranData(this, tranInfo);
        rc.setLayoutManager(pre.getLayoutManager(this));

        rc.addItemDecoration(pre.getDivider(this));
        rc.addItemDecoration(new DividerGridItemDecoration(this, R.drawable.divider_line));
        rc.setAdapter(pre.getAdapter(this));


    }


    @Override
    public void initBodyHolder(int position, final Set_Item data, View itemView) {
        TextView tv = (TextView) itemView.findViewById(R.id.tv_item);
        tv.setText(data.title);
        ImageView img = (ImageView) itemView.findViewById(R.id.img_item);
        builder.load(data.icon_id)
                .placeholder(R.mipmap.loading)
                .error(R.mipmap.loading)
                .crossFade()
                .centerCrop()
                .into(img);

        TextView sub = (TextView) itemView.findViewById(R.id.tv_sub_item);
        if (sub != null && !TextUtils.isEmpty(data.describe)) {
            sub.setText(data.describe);
        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pre.clickItem(data);
            }
        });


    }

    @Override
    public void setTitle(String title) {
        initToolBar(title);
    }

    @Override
    public void showPhoneDialog(Set_Item data) {

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        // Get the layout inflater

        LayoutInflater inflater = this.getLayoutInflater();


        // Inflate and set the layout for the dialog
        View inflate = inflater.inflate(R.layout.dialog_item, null);

        dialog.setView(inflate);
        final AlertDialog show = dialog.show();

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
            if (split.length>1){
                credit_call.setVisibility(View.VISIBLE);
                final String[] credit = split[1].split(":");
                tv_credit.setText(credit[1]);
                tv_creditnumber.setText(credit[0]);
                credit_call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        show.dismiss();
                        Intent intent=new Intent("android.intent.action.CALL", Uri.parse("tel:"+credit[1].replace("-","")));
                        startActivity(intent);

                    }
                });
            }else {
                credit_call.setVisibility(View.GONE);
            }
            final String phoneNumber = phone[1];
            phone_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show.dismiss();
                    Intent intent=new Intent("android.intent.action.CALL", Uri.parse("tel:"+phoneNumber));
                    startActivity(intent);

                }
            });



        }catch (Exception e){
            e.printStackTrace();
        }






    }


}
