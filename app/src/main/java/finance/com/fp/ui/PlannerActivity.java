package finance.com.fp.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import finance.com.fp.BasisActivity;
import finance.com.fp.R;
import finance.com.fp.mode.bean.Set_Item;

/**
 * 学习规划师
 */
public class PlannerActivity extends BasisActivity {

    private RecyclerView rc;
    private EditText et;
    private ImageView img;
    private List<Set_Item> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);
        initToolBar("学习・规划师");
        setColor(this, getResources().getColor(R.color.white));
        rc = (RecyclerView) findViewById(R.id.rc);
        et = (EditText) findViewById(R.id.et_planner);
        img = (ImageView) findViewById(R.id.img_planner);
        initData();
    }


    private void initData() {
        lists = new ArrayList<>();
        final Set_Item item = new Set_Item();
        item.title = getString(R.string.planner_start);
        lists.add(item);
        StaggeredGridLayoutManager manage = new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL);
        rc.setLayoutManager(manage);
        final PlannerAdapter adapter = new PlannerAdapter();
        rc.setAdapter(adapter);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg = et.getText().toString().trim();
                if (!TextUtils.isEmpty(msg)){
                    Set_Item item1 = new Set_Item();
                    item1.title=msg;
                    item1.type=1;
                    lists.add(item1);
                    adapter.notifyItemInserted(adapter.getItemCount()-1);
                    rc.scrollToPosition(adapter.getItemCount()-1);
                    et.setText("");
                }

            }
        });


    }


    public class PlannerAdapter extends RecyclerView.Adapter {

        @Override
        public int getItemViewType(int position) {

            return lists.get(position).type;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder holder;
            if (viewType == 0) {
                holder = new PlannerHolder(View.inflate(PlannerActivity.this, R.layout.item_planner_sys, null));
            } else {
                holder = new PlannerHolder(View.inflate(PlannerActivity.this, R.layout.item_planner_cus, null));
            }

            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((PlannerHolder)holder).initView(lists.get(position));

        }

        @Override
        public int getItemCount() {
            return lists.size();
        }
    }

    public class PlannerHolder extends RecyclerView.ViewHolder {
        public ImageView view;
        public TextView text;
        public View itemView;

        public PlannerHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        public void initView(Set_Item item) {
            if (item.icon_id != 0) {
                view = (ImageView) itemView.findViewById(R.id.img_icon);
                Glide.with(PlannerActivity.this).load(item.icon_id).placeholder(R.mipmap.icon_tcacher).crossFade().into(view);
            }

            if (!TextUtils.isEmpty(item.title)){
                text = (TextView) itemView.findViewById(R.id.tv_planner);
                text.setText(item.title);
            }
        }


    }
}
