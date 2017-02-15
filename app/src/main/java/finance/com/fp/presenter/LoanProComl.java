package finance.com.fp.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import em.sang.com.allrecycleview.adapter.DefaultAdapter;
import em.sang.com.allrecycleview.holder.CustomHolder;
import em.sang.com.allrecycleview.holder.HeardHolder;
import em.sang.com.allrecycleview.holder.SimpleHolder;
import em.sang.com.allrecycleview.inter.DefaultAdapterViewLisenter;
import finance.com.fp.R;
import finance.com.fp.ui.holder.CardNotifiHolder;
import finance.com.fp.ui.holder.GrideHolder;
import finance.com.fp.ui.holder.HomeCarouselHolder;
import finance.com.fp.ui.holder.HomeToolsHolder;
import finance.com.fp.mode.LoanDataComl;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.inter.LoanDataInter;
import finance.com.fp.presenter.inter.LoanInter;
import finance.com.fp.ui.inter.LoanView;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/20 14:17
 */
public class LoanProComl implements LoanInter {
    private LoanView view;
    private LoanDataInter data;
    private HomeCarouselHolder carouselHolder;
    private CardNotifiHolder notifiHolder;

    public LoanProComl(LoanView view) {
        this.view = view;
        data = new LoanDataComl();
    }


    @Override
    public DefaultAdapter<Set_Item> getAdapter(Context context) {

        DefaultAdapter<Set_Item> adapter = new DefaultAdapter<>(context, data.getHotLoan(), R.layout.item_loan_item, new DefaultAdapterViewLisenter<Set_Item>() {
            @Override
            public CustomHolder getBodyHolder(Context context, final List lists, int itemID) {
                return new CustomHolder<Set_Item>(context, lists, itemID) {
                    @Override
                    public void initView(int position, List<Set_Item> lists, Context context) {
                        super.initView(position, context);
                        ImageView img = (ImageView) itemView.findViewById(R.id.img_item_loan);
                        TextView title = (TextView) itemView.findViewById(R.id.tv_title_item_loan);
                        TextView sub = (TextView) itemView.findViewById(R.id.tv_title_sub_item_loan);
                        Set_Item item = lists.get(position);
                        Glide.with(context)
                                .load(item.icon_id)
                                .placeholder(item.placeholder)
                                .error(item.faildId)
                                .centerCrop()
                                .crossFade()
                                .into(img);
                        title.setText(item.title);
                        sub.setText(item.describe);
                        view.onListItemClick(position,item);
                    }
                };
            }
        });
        HomeToolsHolder toolsHolder = new HomeToolsHolder(context, data.getTools(), R.layout.item_home_tool);
        toolsHolder.setView(R.layout.view_tools_loan);
        toolsHolder.setOnToolsItemClickListener(view);
        adapter.addHead(toolsHolder);

        carouselHolder = new HomeCarouselHolder(context, data.getTools(), R.layout.item_home_carousel);
        adapter.addHead(carouselHolder);
        int dimension = (int) context.getResources().getDimension(R.dimen.app_cut_big);
        carouselHolder.setMagrin(0,dimension,0,0);
        notifiHolder = new CardNotifiHolder(context, data.getTools(), R.layout.item_card_navi);
        notifiHolder.setMagrin(0, 0, 0, dimension);
        adapter.addHead(notifiHolder);


        GrideHolder grideHolder = new GrideHolder(context, data.getGVLoan(), R.layout.item_grideview);
        grideHolder.setItemID(R.layout.gv_loan);
        grideHolder.setMagrin(0, 0, 0, dimension);
        grideHolder.setnumColuns(5);
        adapter.addHead(grideHolder);

        adapter.addHead(new HeardHolder(LayoutInflater.from(context).inflate(R.layout.item_loan_icons, null)));
        adapter.addHead(new SimpleHolder(context, R.layout.item_loan_hot));

        return adapter;
    }

    @Override
    public void stopCarousel() {
        carouselHolder.stopCarousel();
        notifiHolder.stopCarousel();
    }
}
