package finance.com.fp.presenter;

import android.content.Context;
import android.content.Intent;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import em.sang.com.allrecycleview.adapter.RefrushAdapter;
import em.sang.com.allrecycleview.holder.CustomHolder;
import em.sang.com.allrecycleview.inter.DefaultAdapterViewLisenter;
import em.sang.com.allrecycleview.listener.OnToolsItemClickListener;
import finance.com.fp.R;
import finance.com.fp.mode.HomeFragmentDataCom;
import finance.com.fp.mode.bean.LoanSearchBean;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.datafractory.HttpFactory;
import finance.com.fp.mode.inter.HomeFragmentData;
import finance.com.fp.presenter.inter.HomeFragmentPre;
import finance.com.fp.ui.Loan_Search_Activity;
import finance.com.fp.ui.holder.CardMoreHolder;
import finance.com.fp.ui.holder.FindFriendHolder;
import finance.com.fp.ui.holder.FindFunctionHolder;
import finance.com.fp.ui.holder.HomeBodyHolder;
import finance.com.fp.ui.holder.HomeCarouselHolder;
import finance.com.fp.ui.holder.HomeFindHolder;
import finance.com.fp.ui.holder.HomeToolsHolder;
import finance.com.fp.ui.inter.HomeFramentView;
import finance.com.fp.utlis.Utils;
import rx.Subscriber;
import rx.Subscription;

import static anet.channel.util.Utils.context;
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
    private List<Set_Item> lists, carouselList, finLists, dLists;
    private List<LoanSearchBean> homeList;
    private RefrushAdapter<Set_Item> adapter;
    HomeCarouselHolder carouselHolder;


    public HomeFragmentPreComl(HomeFramentView view) {
        this.view = view;
        data = new HomeFragmentDataCom();
        lists = new ArrayList<>();
        finLists = new ArrayList<>();
        dLists = new ArrayList<>();
        carouselList = new ArrayList<>();
        homeList = new ArrayList<>();
    }

    @Override
    public RefrushAdapter<Set_Item> getAdapter(Context context) {
        return adapter;
    }

    @Override
    public RefrushAdapter<Set_Item> initAdapter(final Context context) {

        adapter = new RefrushAdapter<>(context, homeList, R.layout.item_loan_item, new DefaultAdapterViewLisenter<LoanSearchBean>() {
            @Override
            public CustomHolder getBodyHolder(Context context, List<LoanSearchBean> lists, int itemID) {
                return new HomeBodyHolder(context, lists, itemID);
            }
        });

        adapter.setRefrushPosition(2);

        getCarouselHolder(context);
        HomeToolsHolder toolsHolder = new HomeToolsHolder(context, data.getTools(), R.layout.item_home_tool);
        float dimension = context.getResources().getDimension(R.dimen.app_cut_big);
        toolsHolder.setMagrin(0, dimension, 0, dimension);
        toolsHolder.setOnToolsItemClickListener(view);
        adapter.addHead(toolsHolder);
        CardMoreHolder moreHolder = new CardMoreHolder(context, R.layout.item_card_more, new Set_Item(0, "热门网贷", "更多产品"));
        moreHolder.setOnToolsItemClickListener(new OnToolsItemClickListener() {
            @Override
            public void onItemClick(int position, Object item) {
                if (!Utils.isLogion(context)) {
                    Utils.showLoginDialog(context);
                } else {
                    context.startActivity(new Intent(context, Loan_Search_Activity.class));
                }
            }
        });
        adapter.addHead(moreHolder);
        return adapter;
    }

    List<Set_Item> temp;

    private void getCarouselHolder(Context context) {
        carouselList.add(new Set_Item(R.mipmap.loading, ""));
        carouselHolder = new HomeCarouselHolder(context, carouselList, R.layout.item_home_carousel);
        adapter.addHead(carouselHolder);
        temp = new ArrayList<>();
        getCouser();
    }


    private boolean isRefrush;
    @Override
    public void getCouser() {
        if (!isRefrush) {
            Subscription subscribe = HttpFactory.getCarousel("29").subscribe(new Subscriber<Set_Item>() {
                @Override
                public void onStart() {
                    super.onStart();
                    temp.clear();
                    isRefrush=true;
                }

                @Override
                public void onCompleted() {
                    carouselList.clear();
                    carouselList.addAll(temp);
                    adapter.notifyItemRangeChanged(0, 1);
                    adapter.notifyDataSetChanged();
                    temp.clear();
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    isRefrush=false;
                }

                @Override
                public void onNext(Set_Item set_item) {

                    temp.add(set_item);
                }
            });

        }
    }


    private int checkId;

    @Override
    public RefrushAdapter<Set_Item> initFindAdapter(Context context) {
        lists = new ArrayList<>();
        adapter = new RefrushAdapter<>(context, lists, R.layout.item_home, new DefaultAdapterViewLisenter<Set_Item>() {
            @Override
            public CustomHolder getBodyHolder(Context context, List<Set_Item> lists, int itemID) {
                return new HomeFindHolder(context, lists, itemID);
            }
        });

        adapter.setRefrushPosition(2);
        FindFriendHolder friendHolder = new FindFriendHolder(getContext(), null, R.layout.item_find_fc);
        float dimension = context.getResources().getDimension(R.dimen.find_title_item_margin_v);
        friendHolder.setMagrin(0, dimension, 0, dimension);
        friendHolder.setOnToolsItemClickListener(view);


        adapter.addHead(friendHolder);
        FindFunctionHolder holder = new FindFunctionHolder(getContext(), null, R.layout.item_find_function);
        holder.setOnToolsItemClickListener(new OnToolsItemClickListener() {
            @Override
            public void onItemClick(int position, Object item) {
                checkId = position;
                view.showLoad();
            }
        });

        float dimension1 = context.getResources().getDimension(R.dimen.app_cut_big);
        holder.setMagrin(0, 0, 0, dimension1);
        adapter.addHead(holder);
        return adapter;
    }

    Subscription fSubscription, DSubscription;

    @Override
    public void getFinceData(int i) {
        if (Dsubscriber != null) {
            Dsubscriber.unsubscribe();
        }
        if (i > 0) {
            data.getLoanSearch(i).subscribe(new Subscriber<LoanSearchBean>() {

                @Override
                public void onStart() {
                    super.onStart();
                    homeList.clear();
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
                public void onNext(LoanSearchBean set_item) {
                    homeList.add(set_item);
                }
            });

        } else {
            fSubscription = data.getfinancialhole(fin).subscribe(getSubscribre());
        }
    }


    @Override
    public void unsubscribe() {
        if (subscriber != null) {
            subscriber.unsubscribe();
        }

        if (Dsubscriber != null) {
            Dsubscriber.unsubscribe();
        }
    }

    @Override
    public void getData() {
        isloadMore = false;
        if (checkId == 1) {
            fin = 0;
            getFinceData(0);
        } else {
            d = 0;
            getPartialDoor();
        }
    }

    private int fin, d;
    private boolean isloadMore;

    @Override
    public void loadMore() {
        isloadMore = true;
        if (checkId == 1) {
            fin++;
            getFinceData(0);
        } else {
            d++;
            getPartialDoor();
        }
    }

    private void getPartialDoor() {
        if (subscriber != null) {
            subscriber.unsubscribe();
        }
        DSubscription = data.getPartialDoor(d).subscribe(getDSubscribre());
    }

    Subscriber<Set_Item> subscriber;

    private Subscriber<Set_Item> getSubscribre() {
        unsubscribe();
        return subscriber = new Subscriber<Set_Item>() {

            @Override
            public void onStart() {
                super.onStart();
                lists.clear();
                lists.addAll(finLists);
//                adapter.notifyDataSetChanged();
                if (!isloadMore) {
                    finLists.clear();
                }
            }

            @Override
            public void onCompleted() {


                if (finLists.size() == lists.size()) {
                    if (isloadMore) {
                        view.loadNoMore();
                        fin--;
                    }else {
                        view.loadSuccess();
                    }
                } else {
                    view.loadSuccess();
                }
                lists.clear();
                lists.addAll(finLists);
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                view.loadFail();
            }

            @Override
            public void onNext(Set_Item set_item) {
                finLists.add(set_item);

            }


        };
    }

    Subscriber<Set_Item> Dsubscriber;

    private Subscriber<Set_Item> getDSubscribre() {

        return Dsubscriber = new Subscriber<Set_Item>() {

            @Override
            public void onStart() {
                super.onStart();
                lists.clear();
                lists.addAll(dLists);
//                adapter.notifyDataSetChanged();
                if (!isloadMore) {
                    dLists.clear();
                }
            }

            @Override
            public void onCompleted() {

                if (isloadMore) {
                    if (dLists.size() == lists.size()) {
                        view.loadNoMore();
                        d--;
                    }else {
                        view.loadSuccess();
                    }
                }else {
                    view.loadSuccess();
                }
                lists.clear();
                lists.addAll(dLists);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                d--;
                view.loadFail();
            }

            @Override
            public void onNext(Set_Item set_item) {
                dLists.add(set_item);
            }


        };
    }


}
