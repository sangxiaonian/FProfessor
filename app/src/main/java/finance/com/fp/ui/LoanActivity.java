package finance.com.fp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import em.sang.com.allrecycleview.adapter.DefaultAdapter;
import em.sang.com.allrecycleview.holder.CustomHolder;
import em.sang.com.allrecycleview.holder.HeardHolder;
import em.sang.com.allrecycleview.holder.SimpleHolder;
import em.sang.com.allrecycleview.inter.DefaultAdapterViewLisenter;
import finance.com.fp.BasisActivity;
import finance.com.fp.R;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.holder.CardNotifiHolder;
import finance.com.fp.holder.GrideHolder;
import finance.com.fp.holder.HomeCarouselHolder;
import finance.com.fp.holder.HomeToolsHolder;
import finance.com.fp.utlis.ListDatasFractary;

/**
 *
 * 网贷
 */
public class LoanActivity extends BasisActivity {

    private Toolbar toolbar;
    private TextView title;
    private RecyclerView recyclerView;
    private HomeCarouselHolder carouselHolder;
    private CardNotifiHolder notifiHolder;
    
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

        title.setText(getResources().getString(R.string.loan_title));

        lists=dataFractory.creatData(ListDatasFractary.LOAN_ITEM);
        
        
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


    public RecyclerView.Adapter getAdapter() {
        
        DefaultAdapter<Set_Item> adapter = new DefaultAdapter<>(this, lists, R.layout.item_loan_item, new DefaultAdapterViewLisenter<Set_Item>(){
            @Override
            public CustomHolder getBodyHolder(Context context, final List lists, int itemID) {
                return new CustomHolder<Set_Item>(context,lists,itemID){
                    @Override
                    public void initView(int position, List<Set_Item> lists,Context context) {
                        super.initView(position, context);
                        ImageView img = (ImageView) itemView.findViewById(R.id.img_item_loan);
                        TextView title = (TextView) itemView.findViewById(R.id.tv_title_item_loan);
                        TextView sub = (TextView) itemView.findViewById(R.id.tv_title_sub_item_loan);

                        Set_Item item=lists.get(position);
                        Glide.with(context)
                                .load(item.icon_id)
                                .centerCrop()
                                .crossFade()
                                .into(img);
                        title.setText(item.title);
                        sub.setText(item.describe);
                    }
                };
            }
        });
        HomeToolsHolder toolsHolder = new HomeToolsHolder(this, getTools(), R.layout.item_card_tool);
        toolsHolder.setImageSize(getResources().getDimension(R.dimen.loan_item_tools_size));
        adapter.addHead(toolsHolder);

        carouselHolder = new HomeCarouselHolder(this, getTools(), R.layout.item_home_carousel);
        adapter.addHead(carouselHolder);

        int dimension = (int) getResources().getDimension(R.dimen.title_card_height);
        notifiHolder = new CardNotifiHolder(this, getTools(), R.layout.item_card_navi);
        notifiHolder.setMagrin(0,0,0, dimension);
        adapter.addHead(notifiHolder);


        GrideHolder grideHolder = new GrideHolder(this, getGrive(), R.layout.item_grideview);
        int imgSize = (int) getResources().getDimension(R.dimen.loan_item_btn_height);
        grideHolder.setImageParams(imgSize,imgSize,5);
        LinearLayout.LayoutParams gvparams = new LinearLayout.LayoutParams(imgSize, imgSize);
        gvparams.setMargins(0,dimension,0,dimension);
        grideHolder.setImageParams(gvparams);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,0,dimension);
        grideHolder.setTextParams(params);
        adapter.addHead(grideHolder);

        adapter.addHead(new HeardHolder(LayoutInflater.from(this).inflate(R.layout.item_loan_icons,null)));

        adapter.addHead(new SimpleHolder(this,R.layout.item_loan_hot));

        return adapter;
    }



    private List<Set_Item> getTools(){
        ArrayList<Set_Item> tools = new ArrayList<>();
        int[] icons = {R.mipmap.icon_strategy,
                R.mipmap.icon_learningpianner_small, R.mipmap.icon_netcreditsearch};
        String[] titles = getResources().getStringArray(R.array.loan_tools);
        for (int i = 0; i < titles.length; i++) {
            Set_Item item = new Set_Item(icons[i], titles[i]);
            tools.add(item);
        }
        return tools;
    }

    private List<Set_Item> getGrive(){
        ArrayList<Set_Item> tools = new ArrayList<>();
        int[] icons = {R.mipmap.icon_creditcardloanst,
                R.mipmap.icon_thebillisborrowed,
                R.mipmap.icon_creditcardalso,
                R.mipmap.icon_sesamepointsborrow,
                R.mipmap.icon_workingtoborrow,
                R.mipmap.icon_idcardtoborrow,
                R.mipmap.icon_college,
                R.mipmap.icon_consumercredit,
                R.mipmap.icon_wechatqq,
                R.mipmap.icon_allcategories };
        String[] titles = getResources().getStringArray(R.array.loan_grid);

        for (int i = 0; i < 10; i++) {
            Set_Item item = new Set_Item(icons[i], titles[i]);

            tools.add(item);
        }

        return tools;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearThread();
    }

    private void clearThread() {
        notifiHolder.stopCarousel();
        carouselHolder.stopCarousel();
    }

}
