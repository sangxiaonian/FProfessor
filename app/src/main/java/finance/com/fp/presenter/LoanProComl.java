package finance.com.fp.presenter;

import android.content.Context;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

import em.sang.com.allrecycleview.adapter.DefaultAdapter;
import em.sang.com.allrecycleview.holder.CustomHolder;
import em.sang.com.allrecycleview.holder.HeardHolder;
import em.sang.com.allrecycleview.holder.SimpleHolder;
import em.sang.com.allrecycleview.inter.DefaultAdapterViewLisenter;
import em.sang.com.allrecycleview.listener.OnToolsItemClickListener;
import finance.com.fp.CusApplication;
import finance.com.fp.R;
import finance.com.fp.mode.LoanDataComl;
import finance.com.fp.mode.bean.LoanSearchBean;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.datafractory.HttpFactory;
import finance.com.fp.mode.inter.LoanDataInter;
import finance.com.fp.presenter.inter.LoanInter;
import finance.com.fp.ui.holder.CardNotifiHolder;
import finance.com.fp.ui.holder.GrideHolder;
import finance.com.fp.ui.holder.HomeBodyHolder;
import finance.com.fp.ui.holder.HomeCarouselHolder;
import finance.com.fp.ui.holder.HomeToolsHolder;
import finance.com.fp.ui.inter.LoanView;
import finance.com.fp.utlis.ToastUtil;
import rx.Subscriber;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/20 14:17
 */
public class LoanProComl extends Subscriber<LoanSearchBean> implements LoanInter {
    private LoanView view;
    private LoanDataInter data;
    private HomeCarouselHolder carouselHolder;
    private CardNotifiHolder notifiHolder;
    private List<Set_Item> carouselDatas;

    public LoanProComl(LoanView view) {
        this.view = view;
        data = new LoanDataComl();
    }

    private List<LoanSearchBean> lists;

    DefaultAdapter<LoanSearchBean> adapter;
    @Override
    public DefaultAdapter<LoanSearchBean> getAdapter(Context context) {
        lists=new ArrayList<>();
          adapter = new DefaultAdapter<>(context, lists, R.layout.item_loan_item, new DefaultAdapterViewLisenter<LoanSearchBean>() {
            @Override
            public CustomHolder getBodyHolder(Context context, final List lists, int itemID) {
                return new HomeBodyHolder(context,lists,itemID);
            }
        });

        HttpFactory.getLoanSearch(0,0).take(3).subscribe(this);

        HomeToolsHolder toolsHolder = new HomeToolsHolder(context, data.getTools(), R.layout.item_home_tool);
        toolsHolder.setView(R.layout.view_tools_loan);
        toolsHolder.setOnToolsItemClickListener(view);
        adapter.addHead(toolsHolder);

        int dimension = setCarousel(context);

        notifiHolder = new CardNotifiHolder(context, data.getTools(), R.layout.item_card_navi);
        notifiHolder.setMagrin(0, 0, 0, dimension);
        adapter.addHead(notifiHolder);


        GrideHolder grideHolder = new GrideHolder(context, data.getGVLoan(), R.layout.item_grideview);
        grideHolder.setItemID(R.layout.gv_loan);
        grideHolder.setMagrin(0, 0, 0, dimension);
        grideHolder.setnumColuns(5);
        grideHolder.setOnToolsItemClickListener(new OnToolsItemClickListener<Set_Item>() {
            @Override
            public void onItemClick(int position, Set_Item item) {
                view.grideLoanClick(position,item);
            }
        });
        adapter.addHead(grideHolder);

        adapter.addHead(new HeardHolder(LayoutInflater.from(context).inflate(R.layout.item_loan_icons, null)));
        adapter.addHead(new SimpleHolder(context, R.layout.item_loan_hot));

        return adapter;
    }

    private int setCarousel(Context context) {
        carouselDatas=new ArrayList<>();
        carouselDatas.add(new Set_Item(R.mipmap.loading,""));
        carouselHolder = new HomeCarouselHolder(context, carouselDatas, R.layout.item_home_carousel);
        adapter.addHead(carouselHolder);
        int dimension = (int) context.getResources().getDimension(R.dimen.app_cut_big);
        carouselHolder.setMagrin(0,dimension,0,0);
        HttpFactory.getCarousel("31").subscribe(new Subscriber<Set_Item>() {
            @Override
            public void onStart() {
                super.onStart();
                carouselDatas.clear();

            }

            @Override
            public void onCompleted() {
                adapter.notifyItemRangeChanged(0, 1);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(Set_Item set_item) {
                carouselDatas.add(set_item);
            }
        });



        return dimension;
    }

    @Override
    public void stopCarousel() {
        carouselHolder.stopCarousel();
        notifiHolder.stopCarousel();
    }

    @Override
    public void onCompleted() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        ToastUtil.showTextToast(CusApplication.getContext().getString(R.string.net_error));
    }

    @Override
    public void onNext(LoanSearchBean loanSearchBean) {
        lists.add(loanSearchBean);
    }
}
