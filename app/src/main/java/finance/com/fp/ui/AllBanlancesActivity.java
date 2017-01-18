package finance.com.fp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import em.sang.com.allrecycleview.adapter.DefaultAdapter;
import em.sang.com.allrecycleview.holder.CustomHolder;
import em.sang.com.allrecycleview.inter.DefaultAdapterViewLisenter;
import finance.com.fp.BasisActivity;
import finance.com.fp.R;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.datafractory.CardDataFractory;
import finance.com.fp.mode.datafractory.DataLoadLisetner;
import finance.com.fp.utlis.DividerGridItemDecoration;

/**
 * 所有热门银行
 */
public class AllBanlancesActivity extends BasisActivity {

    private RecyclerView rc;
    private DefaultAdapter adapter;
    private List<Set_Item> lists;
    private CardDataFractory fractory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        initToolBar(getString(R.string.all_balance));
        setColor(this,getResources().getColor(R.color.white));
        rc= (RecyclerView) findViewById(R.id.rc);
        initData();
    }

    @Override
    public void initData() {
        super.initData();
        fractory = CardDataFractory.getInstance(this);
        lists=new ArrayList<>();
        adapter=new DefaultAdapter<Set_Item>(this,lists,R.layout.item_tool,new DefaultAdapterViewLisenter<Set_Item>(){
            @Override
            public CustomHolder getBodyHolder(Context context, List<Set_Item> lists, final int itemID) {
                return new CustomHolder<Set_Item>(context,lists,itemID){
                    @Override
                    public void initView(int position, List<Set_Item> datas, Context context) {
                        Set_Item item = datas.get(position);
                        super.initView(position, datas, context);
                        TextView tv = (TextView) itemView.findViewById(R.id.tv_item);
                        tv.setText(item.title);
                        ImageView img = (ImageView) itemView.findViewById(R.id.img_item);
                        Glide.with(AllBanlancesActivity.this).load(item.icon_id)
                                .centerCrop()
                                .placeholder(R.mipmap.loading)
                                .error(R.mipmap.loading)
                                .crossFade()
                                .into(img);

                    }
                };
            }
        });
        GridLayoutManager manager = new GridLayoutManager(this,3);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rc.setLayoutManager(manager);
         rc.addItemDecoration(new DividerGridItemDecoration(this,R.drawable.divider_line_height));
        rc.setAdapter(adapter);

        fractory.getAllBanances(new DataLoadLisetner<Set_Item>() {
            @Override
            public void loadOver(List<Set_Item> lists) {
                AllBanlancesActivity.this.lists.addAll(lists);
                adapter.notifyDataSetChanged();
            }
        });

    }
}
