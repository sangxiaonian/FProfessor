package finance.com.fp.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.sang.viewfractory.view.RefrushLinearLayout;
import com.sang.viewfractory.view.ShapeView;

import java.util.ArrayList;
import java.util.List;

import finance.com.fp.BasisActivity;
import finance.com.fp.R;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.datafractory.HttpFactory;
import finance.com.fp.mode.http.Config;
import finance.com.fp.ui.holder.BasicHolder;
import finance.com.fp.utlis.ToastUtil;
import finance.com.fp.utlis.Utils;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;

/**
 * 学习规划师
 */
public class PlannerActivity extends BasisActivity implements Observer<Set_Item> {

    private RecyclerView rc;
    private EditText et;
    private Button btn;
    private List<Set_Item> lists;
    private RefrushLinearLayout bottomView;
    private Set_Item bottom;
    private String send = "发送中..";
    private String send_fail = "网络异常,发送失败!";
    private int state;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);
        initToolBar("规划师");
        setColor(this, getResources().getColor(R.color.statucolor));
        rc = (RecyclerView) findViewById(R.id.rc);
        et = (EditText) findViewById(R.id.et_planner);
        btn = (Button) findViewById(R.id.btn_planner_send);
        lists = new ArrayList<>();


        bottomView = new RefrushLinearLayout(this);
        bottomView.setStyle(RefrushLinearLayout.STYLE_LOAD);
        bottomView.setVisibility(View.INVISIBLE);
        bottom = new Set_Item();
        bottom.title = getString(R.string.loan_apply_detail);
        bottom.type = 2;
        initData();
    }



    Subscription subscribe;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unSunsriber();
    }

    private void unSunsriber() {
        if (subscribe != null) {
            subscribe.unsubscribe();
        }
    }

    PlannerAdapter adapter;

    public void initData() {

        StaggeredGridLayoutManager manage = new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL);
        rc.setLayoutManager(manage);
        adapter = new PlannerAdapter();
        rc.setAdapter(adapter);
        subscribe = HttpFactory.getPlanner("测试", Utils.getSp(PlannerActivity.this, Config.login_name)).subscribe(new Subscriber<Set_Item>() {
            @Override
            public void onStart() {
                super.onStart();
                show();
            }

            @Override
            public void onCompleted() {
                hiden();
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                fail();
            }

            @Override
            public void onNext(Set_Item set_item) {
                if (TextUtils.isEmpty(set_item.title)) {
                    set_item.title = getString(R.string.planner_start);
                }
                lists.add(set_item);
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = et.getText().toString().trim();
                if (!TextUtils.isEmpty(msg)) {
                    Set_Item item1 = new Set_Item();
                    item1.title = msg;
                    item1.type = 1;
                    item1.icon_id = R.mipmap.login_boyhead;
                    lists.add(item1);
                    adapter.notifyDataSetChanged();
                    Logger.i(adapter.getItemCount()+"----------"+lists.size()+"----------"+lists.get(0).title);
                    rc.scrollToPosition(adapter.getItemCount() - 1);
                    et.setText("");
                    btn.setText("发送中..");
                    btn.setEnabled(false);
                    unSunsriber();
                    show();
                    subscribe = HttpFactory.getPlanner(msg, Utils.getSp(PlannerActivity.this, Config.login_name)).subscribe(PlannerActivity.this);
                }

            }
        });


    }

    private void show() {
        bottom.title = send;
        state = ShapeView.LOADING;
        bottomView.setVisibility(View.VISIBLE);

        adapter.notifyItemChanged(adapter.getItemCount() - 1);
    }

    private void hiden() {
        bottomView.setVisibility(View.INVISIBLE);
    }

    private void fail() {
        bottom.title = send_fail;
        state = ShapeView.LOAD_FAIL;
        bottomView.setVisibility(View.VISIBLE);
        adapter.notifyItemChanged(adapter.getItemCount() - 1);
    }

    @Override
    public void onCompleted() {
        hiden();
        adapter.notifyDataSetChanged();
        btn.setText("发送");
        btn.setEnabled(true);
    }

    @Override
    public void onError(Throwable e) {
        btn.setText("发送");
        btn.setEnabled(true);
        e.printStackTrace();
        fail();
        ToastUtil.showTextToast(getString(R.string.net_error));
    }

    @Override
    public void onNext(Set_Item set_item) {
        if (!TextUtils.isEmpty(set_item.title)) {
            lists.add(set_item);
        }
    }


    public class PlannerAdapter extends RecyclerView.Adapter {

        @Override
        public int getItemViewType(int position) {
            if (position == getItemCount() - 1) {
                return 2;
            }
            return lists.get(position).type;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder holder;
            if (viewType == 0) {
                holder = new PlannerHolder(View.inflate(PlannerActivity.this, R.layout.item_planner_sys, null));
            } else if (viewType == 2) {
                holder = new SimpleHolder(bottomView);
            } else {
                holder = new PlannerHolder(View.inflate(PlannerActivity.this, R.layout.item_planner_cus, null));
            }

            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (position != getItemCount() - 1) {
                ((PlannerHolder) holder).initView(lists.get(position));
            } else {
                bottomView.setTvMsg(bottom.title);
                bottomView.upState(state);
            }
        }

        @Override
        public int getItemCount() {
            return lists.size()+1;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        unSunsriber();

    }

    public class PlannerHolder extends BasicHolder {
        public ImageView view;
        public TextView text;
        public View itemView;

        public PlannerHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        public void initView(Set_Item item) {
            view = (ImageView) itemView.findViewById(R.id.img_icon);
            if (item.icon_id != 0) {
                Glide.with(PlannerActivity.this).load(item.icon_id).placeholder(R.mipmap.icon_tcacher).crossFade().into(view);
            } else {
                Glide.with(PlannerActivity.this).load(R.mipmap.icon_tcacher).placeholder(R.mipmap.icon_tcacher).crossFade().into(view);
            }

            if (!TextUtils.isEmpty(item.title)) {
                text = (TextView) itemView.findViewById(R.id.tv_planner);
                text.setText(item.title);
            }
        }


    }

    public class SimpleHolder extends PlannerHolder{

        public SimpleHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void initView(Set_Item item) {

        }
    }
}
