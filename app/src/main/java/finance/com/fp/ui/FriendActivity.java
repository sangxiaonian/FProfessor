package finance.com.fp.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.sang.viewfractory.utils.BarUtils;
import com.sang.viewfractory.utils.JLog;
import com.sang.viewfractory.view.MoveView;

import java.util.ArrayList;
import java.util.List;

import em.sang.com.allrecycleview.RefrushRecycleView;
import em.sang.com.allrecycleview.adapter.RefrushAdapter;
import em.sang.com.allrecycleview.cutline.RecycleViewDivider;
import em.sang.com.allrecycleview.holder.CustomHolder;
import em.sang.com.allrecycleview.inter.DefaultAdapterViewLisenter;
import em.sang.com.allrecycleview.inter.RefrushListener;
import em.sang.com.allrecycleview.listener.OnToolsItemClickListener;
import finance.com.fp.BasisActivity;
import finance.com.fp.R;
import finance.com.fp.mode.bean.FriendBean;
import finance.com.fp.mode.bean.TranInfor;
import finance.com.fp.mode.datafractory.HttpFactory;
import finance.com.fp.mode.http.Config;
import finance.com.fp.ui.holder.FriendHolder;
import finance.com.fp.utlis.ToastUtil;
import finance.com.fp.utlis.Utils;
import rx.Observer;
import sang.com.xdialog.XDialogBuilder;
import sang.com.xdialog.inter.OnEntryClickListener;

public class FriendActivity extends BasisActivity implements Observer<FriendBean>,OnToolsItemClickListener<FriendBean> {

    private RefrushRecycleView recycleView;
    private List<FriendBean> lists, tempLists;
    private int page;
    private MoveView mv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        initToolBar("朋友圈");
        setColor(this, getResources().getColor(R.color.text_home_more_text));
        initView();
        initData();
    }

    @Override
    public void initView() {
        mv= (MoveView) findViewById(R.id.mv);
        mv.setVisibility(View.GONE);
        recycleView = (RefrushRecycleView) findViewById(R.id.rc);
        recycleView.setHasBoom(true);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);
        recycleView.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.VERTICAL));
        recycleView.setRefrushListener(new RefrushListener() {
            @Override
            public void onLoading() {
                page=0;
                HttpFactory.getFriend(page).subscribe(FriendActivity.this);
            }

            @Override
            public void onLoadDowning() {
                page++;
                HttpFactory.getFriend(page).subscribe(FriendActivity.this);
            }
        });

    }
    RefrushAdapter adapter;
    @Override
    public void initData() {
        super.initData();
        lists=new ArrayList<>();
        tempLists=new ArrayList<>();
          adapter = new RefrushAdapter(this, lists, R.layout.item_friend, new DefaultAdapterViewLisenter() {
            @Override
            public CustomHolder getBodyHolder(Context context, List lists, int itemID) {
                FriendHolder holder = new FriendHolder(context, lists, itemID){
                    @Override
                    protected void showImag(final Context context, FriendBean.ImagesBean bean, final View v, final Bitmap resource) {
                        mv.setVisibility(View.VISIBLE);
                        mv.setClickable(true);
                        v.setVisibility(View.INVISIBLE);
                        mv.setListener(new   MoveView.MoveViewListener() {
                            @Override
                            public void onLongClick(View view) {

                                setOnLongClick(view,context,resource);
                            }


                            @Override
                            public void onClick(View view) {

                            }


                            @Override
                            public void animotionEnd(View view) {
                                mv.setVisibility(View.GONE);
                                v.setVisibility(View.VISIBLE);
                            }
                        });
                        mv.setOriginView(v,resource);

                    }
                };
                holder.setOnTOnToolsItemClickListener(FriendActivity.this);
                return holder;
            }
        });

        recycleView.setAdapter(adapter);
        recycleView.setLoading();


    }

    @Override
    public void onCompleted() {
        recycleView.loadSuccess();
        if (tempLists.size()==0){
            page--;
            ToastUtil.showTextToast(Utils.getResStr(R.string.no_more));
        }else {
            if (!recycleView.isLoadMore()){
                lists.clear();
            }
            lists.addAll(tempLists);
            tempLists.clear();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (recycleView.isLoadMore()){
            page--;
        }
        recycleView.loadFail();
    }

    @Override
    public void onNext(FriendBean friendBean) {
        tempLists.add(friendBean);
    }


    @Override
    public void onItemClick(int position, FriendBean item) {
        Intent intent = new Intent(this, ShowDetailActivity.class);
        TranInfor infor = new TranInfor();
        infor.title = item.getTitle();
        infor.content = item.getCurl();
        infor.describe = item.getUpdatetime();
        intent.putExtra(Config.infors, infor);
        startActivity(intent);
    }

    private void setOnLongClick(View v, final Context context, final Bitmap bitmap) {
        try {
            List<String> list = new ArrayList<String>();
            list.add(context.getResources().getString(R.string.friend_save));
            if (bitmap != null) {
                new XDialogBuilder<>(this).setDatas(list)
                        .setThemeID(sang.com.xdialog.R.style.DialogTheme)
                        .setEntryListener(new OnEntryClickListener() {
                            @Override
                            public void onClick(Dialog dialog, int which, Object data) {
                                Utils.saveImageToGallery(context, bitmap);
                                dialog.dismiss();

                            }
                        })
                        .setDialogStyle(XDialogBuilder.SELECT_DIALOG)
                        .builder()
                        .show();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}




