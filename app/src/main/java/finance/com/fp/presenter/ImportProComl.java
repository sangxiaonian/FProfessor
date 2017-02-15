package finance.com.fp.presenter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import em.sang.com.allrecycleview.adapter.DefaultAdapter;
import em.sang.com.allrecycleview.adapter.RefrushAdapter;
import em.sang.com.allrecycleview.holder.CustomHolder;
import em.sang.com.allrecycleview.inter.DefaultAdapterViewLisenter;
import em.sang.com.allrecycleview.listener.OnToolsItemClickListener;
import finance.com.fp.R;
import finance.com.fp.ui.holder.CardNotifiHolder;
import finance.com.fp.ui.holder.GrideHolder;
import finance.com.fp.ui.holder.HomeCarouselHolder;
import finance.com.fp.ui.holder.HomeMoreHolder;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.inter.ImportDataComl;
import finance.com.fp.mode.inter.ImportDataInter;
import finance.com.fp.presenter.inter.ImportInter;
import finance.com.fp.ui.inter.ImportView;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/20 14:17
 */
public class ImportProComl implements ImportInter {
    private ImportView view;
    private ImportDataInter data;
    private HomeCarouselHolder carouselHolder;
    private CardNotifiHolder notifiHolder;

    public ImportProComl(ImportView view) {
        this.view = view;
        data = new ImportDataComl();
    }


    @Override
    public DefaultAdapter<Set_Item> getAdapter(Context context) {

        RefrushAdapter adapter = new RefrushAdapter(context, data.getImport(), R.layout.item_import, new DefaultAdapterViewLisenter() {
            @Override
            public CustomHolder<Set_Item> getBodyHolder(Context context, List lists, int itemID) {
                return new CustomHolder<Set_Item>(context, lists, itemID) {
                    @Override
                    public void initView(final int position, List<Set_Item> datas, final Context context) {
                        final Set_Item item = datas.get(position);
                        ImageView img = (ImageView) itemView.findViewById(R.id.img_item_loan);
                        TextView title = (TextView) itemView.findViewById(R.id.tv_title_item_loan);
                        TextView des = (TextView) itemView.findViewById(R.id.tv_sub_item_loan);
                        Button btn = (Button) itemView.findViewById(R.id.btn_start_item_loan);
                        Glide.with(context)
                                .load(item.icon_id)
                                .placeholder(item.placeholder)
                                .error(item.faildId)
                                .centerCrop()
                                .crossFade()
                                .into(img);
                        title.setText(item.title);
                        des.setText(item.describe);
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                               view.onItemClick(item,position);
                            }
                        });
                    }
                };
            }
        });


        addOneKeyImport(context, adapter);

        int dimension = (int) context.getResources().getDimension(R.dimen.app_cut_big);
        GrideHolder grideHolder = new GrideHolder(context, data.getBalances(), R.layout.item_grideview);
        grideHolder.setItemID(R.layout.gv_import);
        grideHolder.itemView.setBackground(new ColorDrawable(context.getResources().getColor(R.color.transparent)));
        grideHolder.getGridView().setPadding(0, 0, 0, dimension);


        adapter.addHead(grideHolder);

        adapter.addHead(new HomeCarouselHolder(context, data.getImport(), R.layout.item_home_carousel));


        return adapter;
    }

    /**
     * 一键提额
     * @param context
     * @param adapter
     */
    private void addOneKeyImport(Context context, RefrushAdapter adapter) {
        HomeMoreHolder moreHolder = new HomeMoreHolder(context, null, R.layout.item_import_import);
        moreHolder.setOnToolsItemClickListener(new OnToolsItemClickListener() {
            @Override
            public void onItemClick(int position, Object item) {
                view.oneKeyImport();
            }
        });
        adapter.addHead(moreHolder);
    }

    @Override
    public void stopCarousel() {
        carouselHolder.stopCarousel();
        notifiHolder.stopCarousel();
    }
}
