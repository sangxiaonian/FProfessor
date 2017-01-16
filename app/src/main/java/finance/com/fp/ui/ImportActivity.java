package finance.com.fp.ui;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import em.sang.com.allrecycleview.adapter.DefaultAdapter;
import em.sang.com.allrecycleview.adapter.RefrushAdapter;
import em.sang.com.allrecycleview.holder.CustomHolder;
import em.sang.com.allrecycleview.inter.DefaultAdapterViewLisenter;
import finance.com.fp.BasisActivity;
import finance.com.fp.R;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.holder.GrideHolder;
import finance.com.fp.holder.HomeCarouselHolder;
import finance.com.fp.holder.HomeMoreHolder;
import finance.com.fp.utlis.ToastUtil;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/5 17:39
 */
public class ImportActivity extends BasisActivity {
    private Toolbar toolbar;
    private TextView title;
    private RecyclerView recyclerView;
    private List<Set_Item> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        setColor(this,getResources().getColor(R.color.white));
        initView();

    }
    @Override
    protected void onStart() {
        super.onStart();
        initData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initListener();
    }

    private void initData() {
        title.setText(getResources().getString(R.string.import_title));
        lists = getLists();
        recyclerView.setAdapter(getAdapter());
    }

    private void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) findViewById(R.id.title);
        recyclerView = (RecyclerView) findViewById(R.id.rc_card);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
    }

    public DefaultAdapter getAdapter() {
        RefrushAdapter adapter = new RefrushAdapter(this, lists, R.layout.item_import, new DefaultAdapterViewLisenter() {
            @Override
            public CustomHolder<Set_Item> getBodyHolder(Context context, List lists, int itemID) {
                return new CustomHolder<Set_Item>(context, lists, itemID) {
                    @Override
                    public void initView(int position, List<Set_Item> datas, final Context context) {
                        final Set_Item item = datas.get(position);
                        ImageView img = (ImageView) itemView.findViewById(R.id.img_item_loan);
                        TextView title = (TextView) itemView.findViewById(R.id.tv_title_item_loan);
                        TextView des = (TextView) itemView.findViewById(R.id.tv_sub_item_loan);
                        Button btn = (Button) itemView.findViewById(R.id.btn_start_item_loan);
                        Glide.with(context)
                                .load(item.icon_id)
                                .centerCrop()
                                .crossFade()
                                .into(img);
                        title.setText(item.title);
                        des.setText(item.describe);
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtil.showTextToast(context,item.title);
                            }
                        });
                    }
                };
            }
        });


        adapter.addHead(new HomeMoreHolder(this,lists,R.layout.item_import_import));


        float imgWidth = getResources().getDimension(R.dimen.loan_item_btn_width);
        float imgHeight = getResources().getDimension(R.dimen.loan_item_btn_height);
        int dimension = (int) getResources().getDimension(R.dimen.title_card_height);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) imgWidth, (int)imgHeight);
//        params.setMargins(0, (int) dimension,0,0);
        GrideHolder grideHolder = new GrideHolder(this, getBanances(), R.layout.item_grideview);
        grideHolder.itemView.setBackground(new ColorDrawable(getResources().getColor(R.color.transparent)));
        grideHolder.getGridView().setPadding(dimension,dimension,dimension,dimension);
        grideHolder.getGridView().setVerticalSpacing(dimension);
        grideHolder.setImageParams(params);

        adapter.addHead(grideHolder);

        adapter.addHead(new HomeCarouselHolder(this,lists,R.layout.item_home_carousel));


        return adapter;
    }

    private List<Set_Item> getBanances() {
        List<Set_Item> datas = new ArrayList<>();
        int[] icons = {R.mipmap.icon_chinamerchantsbank, R.mipmap.icon_guangdongdevelopmentbankk,R.mipmap.icon_chinaciticbank_small,
                R.mipmap.icon_bankofcommunications, R.mipmap.icon_shanghaipudongdevelopmentbank_small,
                R.mipmap.icon_constructionbankccb, R.mipmap.icon_chinamerchantsbank_small, R.mipmap.icon_other
                };
        for (int i = 0; i < icons.length; i++) {
            Set_Item item = new Set_Item();
            item.icon_id=icons[i];
            datas.add(item);
        }
        return datas;

    }


    public List<Set_Item> getLists() {
        List<Set_Item> datas = new ArrayList<>();
        datas.add(new Set_Item(R.mipmap.icon_mentionthefrontalstrategy, "提额攻略", "各家银行提额攻略"));
        datas.add(new Set_Item(R.mipmap.icon_mentionthefrontalstrategy, "提额攻略", "各家银行提额攻略"));
        datas.add(new Set_Item(R.mipmap.icon_mentionthefrontalstrategy, "提额攻略", "各家银行提额攻略"));

        return datas;
    }
}
