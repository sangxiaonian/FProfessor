package finance.com.fp.presenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import em.sang.com.allrecycleview.adapter.RefrushAdapter;
import em.sang.com.allrecycleview.holder.CustomHolder;
import em.sang.com.allrecycleview.inter.DefaultAdapterViewLisenter;
import em.sang.com.allrecycleview.listener.OnToolsItemClickListener;
import finance.com.fp.R;
import finance.com.fp.mode.HomeFragmentDataCom;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.inter.HomeFragmentData;
import finance.com.fp.presenter.inter.HomeFragmentPre;
import finance.com.fp.ui.holder.CardMoreHolder;
import finance.com.fp.ui.holder.FindFriendHolder;
import finance.com.fp.ui.holder.FindFunctionHolder;
import finance.com.fp.ui.holder.HomeBodyHolder;
import finance.com.fp.ui.holder.HomeCarouselHolder;
import finance.com.fp.ui.holder.HomeToolsHolder;
import finance.com.fp.ui.inter.HomeFramentView;
import rx.Subscriber;

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

        adapter = new RefrushAdapter<>(context, lists, R.layout.item_loan_item, new DefaultAdapterViewLisenter<Set_Item>() {
            @Override
            public CustomHolder getBodyHolder(Context context, List<Set_Item> lists, int itemID) {
                return new HomeBodyHolder(context, lists, itemID);
            }
        });

        adapter.setRefrushPosition(2);

        HomeCarouselHolder carouselHolder = new HomeCarouselHolder(context,  data.getTools(), R.layout.item_home_carousel);

        adapter.addHead(carouselHolder);
        HomeToolsHolder toolsHolder = new HomeToolsHolder(context,  data.getTools(), R.layout.item_home_tool);
        float dimension = context.getResources().getDimension(R.dimen.app_cut_big);
        toolsHolder.setMagrin(0,dimension,0,dimension);
        toolsHolder.setOnToolsItemClickListener(view);
        adapter.addHead(toolsHolder);
        adapter.addHead(new CardMoreHolder(context,R.layout.item_card_more,new Set_Item(0,"热门网贷","更多产品")));
        return adapter;
    }


    private int checkId;

    @Override
    public RefrushAdapter<Set_Item> initFindAdapter(Context context) {
        lists=new ArrayList<>();
        adapter = new RefrushAdapter<>(context, lists, R.layout.item_home, new DefaultAdapterViewLisenter<Set_Item>() {
            @Override
            public CustomHolder getBodyHolder(Context context, List<Set_Item> lists, int itemID) {
                return new HomeBodyHolder(context, lists, itemID);
            }
        });

        adapter.setRefrushPosition(2);
        FindFriendHolder friendHolder = new FindFriendHolder(getContext(), null, R.layout.item_find_fc);
        float dimension = context.getResources().getDimension(R.dimen.find_title_item_margin_v);
        friendHolder.setMagrin(0,dimension,0,dimension);
        friendHolder.setOnToolsItemClickListener(view);


        adapter.addHead(friendHolder);
        FindFunctionHolder holder = new FindFunctionHolder(getContext(), null, R.layout.item_find_function);
        holder.setOnToolsItemClickListener(new OnToolsItemClickListener() {
            @Override
            public void onItemClick(int position, Object item) {
                view.showLoad();
               checkId=position;
                getData();
            }
        });

        float dimension1 = context.getResources().getDimension(R.dimen.app_cut_big);
        holder.setMagrin(0,0,0,dimension1);
        adapter.addHead(holder);
        return adapter;
    }

    @Override
    public void getFinceData(int i) {
        if (i>0) {
            data.getfinancialhole().take(i).subscribe(getSubscribre());
        }else {
            data.getfinancialhole().subscribe(getSubscribre());
        }
    }


    @Override
    public void unsubscribe() {
        if (subscriber!=null){
            subscriber.unsubscribe();
        }
    }

    @Override
    public void getData() {
        if (checkId==0){
            getFinceData(0);
        }else {
            getPartialDoor();
        }
    }

    private void getPartialDoor() {
        data.getPartialDoor().subscribe(getSubscribre());
    }

    Subscriber<Set_Item> subscriber;
    private Subscriber<Set_Item> getSubscribre(){
        unsubscribe();
         return subscriber = new Subscriber<Set_Item>() {

             @Override
             public void onStart() {
                 super.onStart();
                 lists.clear();
             }

             @Override
            public void onCompleted() {
                adapter.notifyDataSetChanged();
                view.loadSuccess();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                view.loadFail();
            }

            @Override
            public void onNext(Set_Item set_item) {
                lists.add(set_item);
            }
        };
    }




}
