package finance.com.fp.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import em.sang.com.allrecycleview.PullRecycleView;
import em.sang.com.allrecycleview.adapter.DefaultAdapter;
import em.sang.com.allrecycleview.adapter.RefrushAdapter;
import finance.com.fp.BasisActivity;
import finance.com.fp.R;
import finance.com.fp.holder.CardMoreHolder;
import finance.com.fp.holder.CardNotifiHolder;
import finance.com.fp.holder.GrideHolder;
import finance.com.fp.holder.HomeCarouselHolder;
import finance.com.fp.holder.HomeToolsHolder;
import finance.com.fp.mode.bean.Set_Item;

public class CardActivity extends BasisActivity {

    private Toolbar toolbar;
    private PullRecycleView recyclerView;
    private List<Set_Item> tools;
    private CardNotifiHolder notifiHolder;
    private HomeCarouselHolder carouselHolder;
    private Set_Item more;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        setColor(this,getResources().getColor(R.color.white));
        initView();
        initData();

    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onResume() {
        super.onResume();
        initListener();
    }
    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (PullRecycleView) findViewById(R.id.rc_card);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
    }

    private void initData() {
        tools = new ArrayList<>();
        int[] icons = {R.mipmap.icon_handlecardstrategy, R.mipmap.icon_list, R.mipmap.icon_circleoffeiengs,
                R.mipmap.icon_learningpianner};
        String[] titles = getResources().getStringArray(R.array.card_items_title);
        for (int i = 0; i < titles.length; i++) {
            Set_Item item = new Set_Item(icons[i], titles[i]);
            tools.add(item);
        }
        recyclerView.setAdapter(getAdapter());
//        recyclerView.upRefrush_state(PullRecycleView.LOADING);
    }


    private void initListener(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private DefaultAdapter<Set_Item> getAdapter() {
        float item_cut = getResources().getDimension(R.dimen.home_item_time_margin);
        float item_cut_line = getResources().getDimension(R.dimen.app_cut_line);

//        DefaultAdapter<Set_Item> adapter = new DefaultAdapter<>(this, null, 0, null);
        RefrushAdapter<Set_Item> adapter = new RefrushAdapter<>(this, null, 0, null);
        adapter.addHead(new HomeToolsHolder(this, tools, R.layout.item_card_tool));

        carouselHolder = new HomeCarouselHolder(this, tools, R.layout.item_home_carousel);
        adapter.addHead(carouselHolder);

        notifiHolder = new CardNotifiHolder(this, tools, R.layout.item_card_navi);
        adapter.addHead(notifiHolder);

        more = new Set_Item();
        more.title = getString(R.string.balance);
        more.describe = getString(R.string.query_all_balance);
        CardMoreHolder moreHolder = new CardMoreHolder(this, R.layout.item_card_more, more);
        moreHolder.setMagrin(0, item_cut, 0, item_cut_line);
        adapter.addHead(moreHolder);
        GrideHolder balancesHolder = new GrideHolder(this, getGVbalances(), R.layout.item_grideview);
        balancesHolder.getGridView().setPadding(0,0,0, (int) (item_cut*3/2));
        adapter.addHead(balancesHolder);

        Set_Item pro = new Set_Item();
        pro.icon_id=R.mipmap.icon_progress;
        pro.title = getString(R.string.card_pro);
        pro.describe = getString(R.string.card_once_query);
        CardMoreHolder cardPro = new CardMoreHolder(this, R.layout.item_card_more, pro);
        cardPro.setMagrin(0,item_cut,0,item_cut);
        adapter.addHead(cardPro);

        Set_Item card_hot = new Set_Item();
        card_hot.title = getString(R.string.card_hot);
        CardMoreHolder card = new CardMoreHolder(this, R.layout.item_card_more, card_hot);
        card.setMagrin(0,0,0,item_cut_line);
        adapter.addHead(card);

        GrideHolder cardHolder = new GrideHolder(this, getCards(), R.layout.item_grideview);
        cardHolder.setImageParams(getResources().getDimension(R.dimen.card_item_width),getResources().getDimension(R.dimen.card_item_height),3);
        adapter.addHead(cardHolder);
        return adapter;
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

    private List<Set_Item> getGVbalances() {
        List<Set_Item> datas = new ArrayList<>();
        String[] tltles = getResources().getStringArray(R.array.card_items_balances);
        int[] icons = {R.mipmap.icon_chinaciticbank, R.mipmap.icon_cib,
                R.mipmap.icon_shanghaipudongdevelopmentbank, R.mipmap.icon_everbrightbank,
                R.mipmap.icon_bankofcommunictions, R.mipmap.icon_minshengbank, R.mipmap.icon_chinamerchants
                , R.mipmap.icon_more};
        for (int i = 0; i < tltles.length; i++) {
            datas.add(new Set_Item(icons[i],tltles[i]));
        }
        return datas;
    }

    private List<Set_Item> getCards(){
        List<Set_Item> datas = new ArrayList<>();

        int[] icons = {R.mipmap.home_picture,R.mipmap.home_picture2,R.mipmap.home_picture3 };
        for (int i = 0; i < icons.length; i++) {
            Set_Item item = new Set_Item();
            item.icon_id=icons[i];
            datas.add(item);
        }
        return datas;
    }
}
