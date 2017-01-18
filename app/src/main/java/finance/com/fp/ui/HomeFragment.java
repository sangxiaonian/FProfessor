package finance.com.fp.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import em.sang.com.allrecycleview.adapter.DefaultAdapter;
import em.sang.com.allrecycleview.holder.CustomHolder;
import em.sang.com.allrecycleview.inter.DefaultAdapterViewLisenter;
import em.sang.com.allrecycleview.listener.OnToolsItemClickListener;
import finance.com.fp.BasisFragment;
import finance.com.fp.R;
import finance.com.fp.holder.HomeBodyHolder;
import finance.com.fp.holder.HomeCarouselHolder;
import finance.com.fp.holder.HomeMoreHolder;
import finance.com.fp.holder.HomeToolsHolder;
import finance.com.fp.mode.bean.Config;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.bean.TranInfor;
import finance.com.fp.mode.http.HttpClient;
import finance.com.fp.mode.http.HttpService;
import finance.com.fp.utlis.ToastUtil;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/12/27 16:41
 */
public class HomeFragment extends BasisFragment implements View.OnClickListener, OnToolsItemClickListener<Set_Item> {


    private RecyclerView recyclerView;

    private List<String> lists;
    private HomeCarouselHolder carouselHolder;
    private ImageButton card, lending, forheard, title_card, ltitle_ending, title_forheard;

    @Override
    public View initViews(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_main, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.rc_home);
        card = (ImageButton) view.findViewById(R.id.img_main_card);
        lending = (ImageButton) view.findViewById(R.id.img_main_net);
        forheard = (ImageButton) view.findViewById(R.id.img_main_imp);
        title_card = (ImageButton) view.findViewById(R.id.img_main_card_icon);
        ltitle_ending = (ImageButton) view.findViewById(R.id.img_main_net_icon);
        title_forheard = (ImageButton) view.findViewById(R.id.img_main_imp_icon);

        return view;
    }


    private void getData() {
        Retrofit client = HttpClient.getClient("http://192.168.0.113/phpcms/");
        HttpService service = client.create(HttpService.class);
        Map<String, String> map = new HashMap<>();

        map.put("m", "content");
        map.put("c", "doserver");
        map.put("a", "get_app_content");

//        service.getReslut("content","doserver","get_app_content").subscribeOn(Schedulers.io()).subscribe(new Subscriber<String>() {
        service.getReslut(map).subscribeOn(Schedulers.io()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(String s) {
                Logger.i(s);
            }
        });
    }


    @Override
    public void initListener() {
        super.initListener();
        card.setOnClickListener(this);
        lending.setOnClickListener(this);
        forheard.setOnClickListener(this);
        title_card.setOnClickListener(this);
        ltitle_ending.setOnClickListener(this);
        title_forheard.setOnClickListener(this);
    }


    @Override
    protected void initData() {
        lists = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            lists.add("这是测试数据" + i);
        }

        List<Set_Item> funs = new ArrayList<>();
        String[] titles = getContext().getResources().getStringArray(R.array.home_items_title);
        int[] icons = {R.mipmap.icon_planner, R.mipmap.icon_telephone, R.mipmap.icon_credit_reporting_queries, R.mipmap.icon_tool};
        for (int i = 0; i < titles.length; i++) {
            Set_Item item = new Set_Item();
            item.title = titles[i];
            item.icon_id = icons[i];
            funs.add(item);
        }


        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        DefaultAdapter<String> adapter = new DefaultAdapter<>(getContext(), lists, R.layout.item_home, new DefaultAdapterViewLisenter<String>() {
            @Override
            public CustomHolder getBodyHolder(Context context, List<String> lists, int itemID) {
                return new HomeBodyHolder(context, lists, itemID);
            }
        });


        carouselHolder = new HomeCarouselHolder(getContext(), lists, R.layout.item_home_carousel);
        adapter.addHead(carouselHolder);
        HomeToolsHolder toolsHolder = new HomeToolsHolder(getContext(), funs, R.layout.item_home_tool);
        toolsHolder.setMagrin(0, (int) getContext().getResources().getDimension(R.dimen.app_item_cut_margin), 0, 0);
        toolsHolder.setOnToolsItemClickListener(this);
        adapter.addHead(toolsHolder);

        adapter.addHead(new HomeMoreHolder(getContext(), null, R.layout.item_home_more));

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        Class c = null;
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.img_main_card:
            case R.id.img_main_card_icon:
                c = CardActivity.class;
                break;
            case R.id.img_main_net:
            case R.id.img_main_net_icon:
                c = LoanActivity.class;
                break;
            case R.id.img_main_imp:
            case R.id.img_main_imp_icon:
                c = ImportActivity.class;
                break;
        }
        getData();
        if (c == null) {

            ToastUtil.showTextToast(cnt, "该功能尚未开放");
        } else {
            intent.setClass(cnt, c);
            cnt.startActivity(intent);
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        clearThread();
    }

    private void clearThread() {
        carouselHolder.stopCarousel();
    }

    @Override
    public void onItemClick(int position, Set_Item item) {
        TranInfor tranInfor = new TranInfor();
        Intent intent = new Intent(getActivity(), SecondRecycleActivity.class);
        tranInfor.activity_id = 0;
        tranInfor.item_id = position;
        tranInfor.title = item.title;

        switch (position) {
            case 0:
                intent = new Intent(getActivity(), PlannerActivity.class);
                break;
            case 1:
                tranInfor.layoutId = R.layout.item_tool_balance_details;
                break;
            case 2:
                tranInfor.layoutId = R.layout.item_tool_pboc;
                break;
            case 3:
                tranInfor.manager_type = 1;
                tranInfor.manager_row_num = 3;
                tranInfor.layoutId = R.layout.item_tool;
                break;

        }
        intent.putExtra(Config.infors, tranInfor);
        getActivity().startActivity(intent);
    }
}
