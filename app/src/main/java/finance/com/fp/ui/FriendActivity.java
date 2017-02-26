package finance.com.fp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import em.sang.com.allrecycleview.PullRecycleView;
import em.sang.com.allrecycleview.adapter.RefrushAdapter;
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
import finance.com.fp.utlis.RecycleViewDivider;
import finance.com.fp.utlis.ToastUtil;
import finance.com.fp.utlis.Utils;
import rx.Observer;

public class FriendActivity extends BasisActivity implements Observer<FriendBean>,OnToolsItemClickListener<FriendBean> {

    private PullRecycleView recycleView;
    private List<FriendBean> lists, tempLists;
    private int page;

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

        recycleView = (PullRecycleView) findViewById(R.id.rc);
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
                FriendHolder holder = new FriendHolder(context, lists, itemID);
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
        infor.content = item.getContent();
        infor.describe = item.getUpdatetime();
        intent.putExtra(Config.infors, infor);
        startActivity(intent);
    }
}




