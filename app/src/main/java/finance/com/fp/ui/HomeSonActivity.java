package finance.com.fp.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import em.sang.com.allrecycleview.RefrushRecycleView;
import em.sang.com.allrecycleview.adapter.DefaultAdapter;
import em.sang.com.allrecycleview.inter.DefaultRefrushListener;
import finance.com.fp.BasisActivity;
import finance.com.fp.R;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.bean.TranInfor;
import finance.com.fp.mode.http.Config;
import finance.com.fp.presenter.HomeSonPerComl;
import finance.com.fp.presenter.inter.HomeSonPreInter;
import finance.com.fp.ui.inter.HomeSonView;
import finance.com.fp.utlis.PermissionUtils;
import finance.com.fp.utlis.ToastUtil;
import finance.com.fp.utlis.Utils;
import sang.com.xdialog.DialogFactory;
import sang.com.xdialog.XDialog;
import sang.com.xdialog.inter.OnEntryClickListener;

public class HomeSonActivity extends BasisActivity implements HomeSonView {

    private RefrushRecycleView rc;
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
        rc = (RefrushRecycleView) findViewById(R.id.rc);
        rc.setHasBoom(true);
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

            @Override
            public void onLoadDowning() {
                super.onLoadDowning();
                pre.pageAdd();
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

        if (!TextUtils.isEmpty(item.content)) {

            if (TextUtils.equals(item.content,"ali")){
                try {
                    PackageManager packageManager
                            = this.getApplicationContext().getPackageManager();
                    Intent intent = packageManager.
                            getLaunchIntentForPackage("com.eg.android.AlipayGphone");
                    startActivity(intent);
                }catch (Exception e) {
                    e.printStackTrace();
                    XDialog dialog = DialogFactory.getInstance().creatDiaolg(this, DialogFactory.ALEART_DIALOG);
                    dialog.setTitle(getString(R.string.attention));
                    dialog.setDatas(getString(R.string.no_ali));
                    dialog.setOnClickListener(new OnEntryClickListener() {
                        @Override
                        public void onClick(Dialog dialog, int which, Object data) {
                            String url = "https://ds.alipay.com/?from=mobileweb";
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(url));
                            startActivity(intent);
                        }
                    });
                    dialog.show();

                }
            }else {

                Intent intent = new Intent(this, ShowDetailActivity.class);
                TranInfor infor = new TranInfor();
                infor.title = item.title;
                infor.type = 1;
                infor.content = item.content;
                infor.describe = item.describe;
                intent.putExtra(Config.infors, infor);
                startActivity(intent);
            }
        }else {
            ToastUtil.showTextToast(item.content);
        }
    }

    @Override
    public void utilityItemClick(View itemView, final Set_Item item) {
        if (!TextUtils.isEmpty(item.content)) {
            Intent intent = new Intent(this, ShowDetailActivity.class);
            TranInfor infor = new TranInfor();
            infor.title = item.title;
            infor.type=1;
            infor.content = item.content;
            infor.describe = item.describe;
            intent.putExtra(Config.infors, infor);
            startActivity(intent);
        }else {
            ToastUtil.showTextToast(item.content);
        }

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
        // 首先保存图片
        boolean has = PermissionUtils.getInstance()
                .showMsg(Utils.getResStr(R.string.no_permission), Utils.getResStr(R.string.phone_permission))
                .has(this, Manifest.permission.CALL_PHONE);
        if (has) {
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


    }

    @Override
    public void allBalanceItemClick(Set_Item item) {
        Intent intent = new Intent(this, ShowDetailActivity.class);
        TranInfor infor = new TranInfor();
        infor.title = item.title;
        infor.type = 1;

        infor.content = item.describe;
        infor.describe = item.describe;
        intent.putExtra(Config.infors, infor);
        startActivity(intent);
    }

    public void applyQueryItemClick(View itemView, Set_Item item) {

        if (!TextUtils.isEmpty(item.content)) {
            Intent intent = new Intent(this, ShowDetailActivity.class);
            TranInfor infor = new TranInfor();
            infor.title = item.title;
            infor.type=1;
            infor.content = item.content;
            infor.describe = item.describe;
            intent.putExtra(Config.infors, infor);
            startActivity(intent);
        }else {
            ToastUtil.showTextToast(item.content);
        }
    }

    @Override
    public void hotapplyItemClick(View itemView, Set_Item item) {
        Intent intent = new Intent(this, ShowDetailActivity.class);
        TranInfor infor = new TranInfor();
        infor.title = item.title;
        infor.type = 1;

        infor.content = item.content;
        infor.describe = item.describe;
        intent.putExtra(Config.infors, infor);
        startActivity(intent);
    }

    @Override
    public void loan_strage_item(View itemView, Set_Item item) {
        Intent intent = new Intent(this, ShowDetailActivity.class);
        TranInfor infor = new TranInfor();
        infor.title = item.title;

        infor.content = item.content;
        infor.describe = item.describe;
        intent.putExtra(Config.infors, infor);
        startActivity(intent);

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

    @Override
    public boolean isLoadMore() {
        return rc.isLoadMore();
    }

    @Override
    public void loan_jing_click(View itemView, Set_Item item) {
        Intent intent = new Intent(this, ShowDetailActivity.class);
        TranInfor infor = new TranInfor();
        infor.title = item.title;

        infor.content = item.content;
        infor.describe = item.describe;
        intent.putExtra(Config.infors, infor);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pre.unsubscribe();
    }
}
