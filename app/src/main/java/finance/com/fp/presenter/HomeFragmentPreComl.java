package finance.com.fp.presenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import em.sang.com.allrecycleview.adapter.RefrushAdapter;
import em.sang.com.allrecycleview.holder.CustomHolder;
import em.sang.com.allrecycleview.holder.SimpleHolder;
import em.sang.com.allrecycleview.inter.DefaultAdapterViewLisenter;
import finance.com.fp.R;
import finance.com.fp.holder.FindFriendHolder;
import finance.com.fp.holder.FindFunctionHolder;
import finance.com.fp.holder.HomeBodyHolder;
import finance.com.fp.holder.HomeCarouselHolder;
import finance.com.fp.holder.HomeToolsHolder;
import finance.com.fp.mode.HomeFragmentDataCom;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.inter.HomeFragmentData;
import finance.com.fp.presenter.inter.HomeFragmentPre;
import finance.com.fp.ui.inter.HomeFramentView;

import static finance.com.fp.CusApplication.getContext;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/3 9:48
 */
public class HomeFragmentPreComl implements HomeFragmentPre {

    private HomeFramentView view;
    private HomeFragmentData data;
    private List<Set_Item> lists;
    private RefrushAdapter<Set_Item> adapter;
    public HomeFragmentPreComl(HomeFramentView view) {
        this.view=view;
        data=new HomeFragmentDataCom();

    }

    @Override
    public RefrushAdapter<Set_Item> getAdapter(Context context) {
        return adapter;
    }

    @Override
    public RefrushAdapter<Set_Item> initAdapter(Context context) {
        lists = new ArrayList<>();
        List<Set_Item> funs = data.getTools();

        adapter = new RefrushAdapter<>(context, funs, R.layout.item_home, new DefaultAdapterViewLisenter<Set_Item>() {
            @Override
            public CustomHolder getBodyHolder(Context context, List<Set_Item> lists, int itemID) {
                return new HomeBodyHolder(context, lists, itemID);
            }
        });

        HomeCarouselHolder carouselHolder = new HomeCarouselHolder(context, funs, R.layout.item_home_carousel);

        adapter.addHead(carouselHolder);
        HomeToolsHolder toolsHolder = new HomeToolsHolder(context, funs, R.layout.item_home_tool);
        float dimension = context.getResources().getDimension(R.dimen.app_cut_big);
        toolsHolder.setMagrin(0,dimension,0,dimension);
        toolsHolder.setOnToolsItemClickListener(view);
        adapter.addHead(toolsHolder);
        adapter.addHead(new SimpleHolder(context,R.layout.item_home_more));
        return adapter;
    }

    @Override
    public RefrushAdapter<Set_Item> initFindAdapter(Context context) {
        lists=data.getfinancialhole();
        adapter = new RefrushAdapter<>(getContext(), lists, R.layout.item_home, new DefaultAdapterViewLisenter<Set_Item>() {
            @Override
            public CustomHolder getBodyHolder(Context context, List<Set_Item> lists, int itemID) {
                return new HomeBodyHolder(context, lists, itemID);
            }
        });
        adapter.setRefrushPosition(2);
        FindFriendHolder friendHolder = new FindFriendHolder(getContext(), null, R.layout.item_find_fc);
        float dimension = context.getResources().getDimension(R.dimen.find_title_item_margin_v);
        friendHolder.setMagrin(0,dimension,0,dimension);
        adapter.addHead(friendHolder);
        FindFunctionHolder holder = new FindFunctionHolder(getContext(), null, R.layout.item_find_function);
        float dimension1 = context.getResources().getDimension(R.dimen.app_cut_big);
        holder.setMagrin(0,0,0,dimension1);
        adapter.addHead(holder);
        return adapter;
    }


}
