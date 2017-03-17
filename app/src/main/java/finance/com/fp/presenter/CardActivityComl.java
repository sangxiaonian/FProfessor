package finance.com.fp.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import em.sang.com.allrecycleview.adapter.DefaultAdapter;
import em.sang.com.allrecycleview.listener.OnToolsItemClickListener;
import finance.com.fp.R;
import finance.com.fp.mode.CardDataComl;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.bean.TranInfor;
import finance.com.fp.mode.datafractory.HttpFactory;
import finance.com.fp.mode.http.Config;
import finance.com.fp.mode.inter.CardDataInter;
import finance.com.fp.presenter.inter.CardActivityPre;
import finance.com.fp.ui.ShowDetailActivity;
import finance.com.fp.ui.holder.CardMoreHolder;
import finance.com.fp.ui.holder.CardNotifiHolder;
import finance.com.fp.ui.holder.GrideHolder;
import finance.com.fp.ui.holder.HomeCarouselHolder;
import finance.com.fp.ui.holder.HomeToolsHolder;
import finance.com.fp.ui.inter.CardView;
import finance.com.fp.utlis.Utils;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func3;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/19 17:34
 */
public class CardActivityComl implements CardActivityPre {

    private CardView view;
    private CardDataInter data;
    private DefaultAdapter<Set_Item> adapter;
    private HomeCarouselHolder carouselHolder;
    private CardNotifiHolder notifiHolder;
    private List<Set_Item> carouselList, cardLists,tempList;

    public CardActivityComl(CardView cardView) {
        view = cardView;
        data = new CardDataComl();
        carouselList = new ArrayList<>();
        cardLists = new ArrayList<>();
        tempList = new ArrayList<>();

    }

    @Override
    public DefaultAdapter<Set_Item> getAdapter(final Context context) {
        float item_cut = context.getResources().getDimension(R.dimen.app_cut_big);
        float item_cut_line = context.getResources().getDimension(R.dimen.app_cut_line);

        adapter = new DefaultAdapter<>(context, null, 0, null);
        HomeToolsHolder toolsHolder = new HomeToolsHolder(context, data.getTools(), R.layout.item_home_tool);
        toolsHolder.setView(R.layout.view_tools_card);
        toolsHolder.setMagrin(0, 0, 0, item_cut);
        toolsHolder.setOnToolsItemClickListener(view);

        adapter.addHead(toolsHolder);
        getCarouselHolder(context);



        notifiHolder = new CardNotifiHolder(context, Utils.getRandomData("成功申请","信用卡") , R.layout.item_card_navi);
        adapter.addHead(notifiHolder);

        Set_Item more = new Set_Item();
        more.title = context.getString(R.string.balance);
        more.describe = context.getString(R.string.query_all_balance);
        CardMoreHolder moreHolder = new CardMoreHolder(context, R.layout.item_card_more, more);

        moreHolder.setOnToolsItemClickListener(new OnToolsItemClickListener<Set_Item>() {
            @Override
            public void onItemClick(int position, Set_Item item) {
                view.onCardMoreClick(item);
            }
        });
        moreHolder.setMagrin(0, item_cut, 0, item_cut_line);
        adapter.addHead(moreHolder);

        GrideHolder balancesHolder = new GrideHolder(context,tempList, R.layout.item_grideview);
        balancesHolder.getGridView().setPadding(0, 0, 0, (int) (item_cut * 3 / 2));
        balancesHolder.setOnToolsItemClickListener(new OnToolsItemClickListener<Set_Item>() {
            @Override
            public void onItemClick(int position, Set_Item item) {
                view.onClickBanance(position,item,tempList.size());
            }
        });
        adapter.addHead(balancesHolder);


        //办卡进度
        Set_Item pro = new Set_Item();
        pro.icon_id = R.mipmap.icon_progress;
        pro.title = context.getString(R.string.card_pro);
        pro.describe = context.getString(R.string.card_once_query);
        CardMoreHolder cardPro = new CardMoreHolder(context, R.layout.item_card_more, pro);
        cardPro.setMagrin(0, item_cut, 0, item_cut);
        cardPro.setOnToolsItemClickListener(new OnToolsItemClickListener<Set_Item>() {
            @Override
            public void onItemClick(int position, Set_Item item) {
                view.onQueryProClick(item);
            }
        });
        adapter.addHead(cardPro);

        //本期力推荐卡
        Set_Item card_hot = new Set_Item();
        card_hot.title = context.getString(R.string.card_hot);
        CardMoreHolder card = new CardMoreHolder(context, R.layout.item_card_more, card_hot);
        card.itemView.setClickable(false);
        card.itemView.findViewById(R.id.more_icon).setVisibility(View.INVISIBLE);
        card.itemView.setBackgroundColor(context.getResources().getColor(R.color.white));

        card.setMagrin(0, 0, 0, item_cut_line);
        adapter.addHead(card);

        get(context);

        return adapter;
    }


    /**
     * 本期力荐卡
     *
     * @param context
     */
    private void get(final Context context) {
        GrideHolder cardHolder = new GrideHolder(context, cardLists, R.layout.item_grideview);
        cardHolder.setItemID(R.layout.view_gv_li_card);
        cardHolder.setnumColuns(3);
        cardHolder.setOnToolsItemClickListener(new OnToolsItemClickListener<Set_Item>() {
            @Override
            public void onItemClick(int position, Set_Item item) {
                Intent intent = new Intent(context, ShowDetailActivity.class);
                TranInfor infor = new TranInfor();
                infor.title = item.title;
                infor.type=1;
                infor.content = item.content;
                infor.describe = item.describe;
                intent.putExtra(Config.infors, infor);
                context.startActivity(intent);
            }
        });
        adapter.addHead(cardHolder);

    }


    Subscription subscribe;

    @Override
    public void initAllData(){
          subscribe = Observable.zip(HttpFactory.getRecommends().toList(), HttpFactory.getCarousel("30").toList()
                  , data.getGVbalances().toList(),
                  new Func3<List<Set_Item>, List<Set_Item>, List<Set_Item>, List<List<Set_Item>>>() {


            @Override
            public List<List<Set_Item>> call(List<Set_Item> set_items, List<Set_Item> set_items2, List<Set_Item> set_items3) {
                List<List<Set_Item>> lists = new ArrayList<List<Set_Item>>();
                lists.add(set_items);
                lists.add(set_items2);
                if (set_items3.size()<=7){
                    set_items3.add(new Set_Item(R.mipmap.icon_more_balabce,Utils.getResStr(R.string.more_balance)));
                }
                lists.add(set_items3);
                return lists;
            }
        }).subscribe(new Subscriber<List<List<Set_Item>>>() {

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
            public void onNext(List<List<Set_Item>> lists) {
                carouselList.clear();
                cardLists.clear();
                tempList.clear();
                cardLists.addAll(lists.get(0));
                carouselList.addAll(lists.get(1));
                tempList.addAll(lists.get(2));
            }
        });
    }


    private void getCarouselHolder(Context context) {
        carouselList.add(new Set_Item(R.mipmap.loading,""));
        carouselHolder = new HomeCarouselHolder(context, carouselList, R.layout.item_home_carousel);
        adapter.addHead(carouselHolder);

    }


    @Override
    public void clearThread() {
        notifiHolder.stopCarousel();
        carouselHolder.stopCarousel();
        if (subscribe != null) {
            subscribe.unsubscribe();
        }


    }


}
