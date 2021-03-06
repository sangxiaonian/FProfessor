package finance.com.fp.ui.holder;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import em.sang.com.allrecycleview.adapter.DefaultAdapter;
import em.sang.com.allrecycleview.holder.CustomHolder;
import em.sang.com.allrecycleview.inter.DefaultAdapterViewLisenter;
import finance.com.fp.R;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.utlis.GlideUtils;
import finance.com.fp.utlis.ToastUtil;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/3 10:22
 */
public class GrideHolder extends BasicHolder<Set_Item> {

    private RecyclerView gv;
    private int gvItem = R.layout.view_gv_card;

    private int numColuns = 4;
    private List<Set_Item> list;

    public GrideHolder(Context context, List lists, int itemID) {
        super(context, lists, itemID);
        list = lists;

    }

    public void setItemID(int itemID) {
        this.gvItem = itemID;
    }

    public RecyclerView getGridView() {
        return (RecyclerView) itemView.findViewById(R.id.gridview);
    }

    public void setnumColuns(int numColuns) {

        this.numColuns = numColuns;
    }


    @Override
    public void initView(final int position, final Context context) {
        super.initView(position, context);
        gv = (RecyclerView) itemView.findViewById(R.id.gridview);
        LinearLayoutManager manager = new GridLayoutManager(context, numColuns);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        gv.setLayoutManager(manager);


        DefaultAdapter<Set_Item> adapter = new DefaultAdapter<>(context, datas, gvItem, new DefaultAdapterViewLisenter<Set_Item>() {

            @Override
            public CustomHolder getBodyHolder(Context context, List<Set_Item> lists, final int itemID) {
                return new CustomHolder<Set_Item>(context, lists, itemID) {
                    @Override
                    public void initView(final int position, List<Set_Item> datas, final Context context) {
                        super.initView(position, datas, context);
                        ImageView imageView = (ImageView) itemView.findViewById(R.id.img_item);

                        TextView tv_title = (TextView) itemView.findViewById(R.id.tv_item);


                        final Set_Item item = datas.get(position);

                        if (item.img_url != null && item.img_url.length() > 0) {

                            GlideUtils.loadImage(context, imageView, item.img_url);
                        } else if (item.icon_id > 0) {
                            if (TextUtils.isEmpty(item.title)) {
                                Glide.with(context).load(item.icon_id ).placeholder(R.mipmap.loading)
                                        .error(R.mipmap.load_fail)
                                        .fitCenter()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .crossFade().into(imageView);
                            } else {
                                GlideUtils.loadImage(context, imageView, item.icon_id);
                            }
                        }
                        if (!TextUtils.isEmpty(item.title) && tv_title != null) {
                            tv_title.setText(item.title);
                        }

                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (GrideHolder.this.listener != null) {
                                    GrideHolder.this.listener.onItemClick(position, item);
                                } else {
                                    ToastUtil.showTextToast(context, item.title);
                                }
                            }
                        });
                    }
                };
            }
        });
        gv.setAdapter(adapter);


    }


}
