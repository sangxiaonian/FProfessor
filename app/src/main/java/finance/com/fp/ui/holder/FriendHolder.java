package finance.com.fp.ui.holder;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import em.sang.com.allrecycleview.adapter.DefaultAdapter;
import em.sang.com.allrecycleview.holder.CustomHolder;
import em.sang.com.allrecycleview.inter.DefaultAdapterViewLisenter;
import finance.com.fp.R;
import finance.com.fp.mode.bean.FriendBean;
import finance.com.fp.utlis.GlideUtils;
import sang.com.xdialog.DialogFactory;
import sang.com.xdialog.XDialog;

import static finance.com.fp.R.id.img_icon;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/2/26 15:29
 */
public class FriendHolder extends CustomHolder<FriendBean> {
    public FriendHolder(Context context, List<FriendBean> lists, int itemID) {
        super(context, lists, itemID);
    }

    @Override
    public void initView(final int position, List<FriendBean> datas, Context context) {
        super.initView(position, datas, context);
        final FriendBean bean = datas.get(position);
        TextView title = (TextView) itemView.findViewById(R.id.tv_title);
        if (!TextUtils.isEmpty(bean.getTitle())){
            title.setText(bean.getTitle());
        }
        TextView content = (TextView) itemView.findViewById(R.id.tv_content);
        if (bean.getContent()!=null){
            content.setText(bean.getContent());
        }

        TextView time = (TextView) itemView.findViewById(R.id.tv_time);
        if (bean.getUpdatetime()!=null){
            time.setText(em.sang.com.allrecycleview.utils.Utils.formatDateTime(bean.getUpdatetime()+"000"));
        }


        TextView textView = (TextView) itemView.findViewById(R.id.tv_details);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onItemClick(position,bean);

                }
            }
        });
        Glide.with(context).load(bean.getThumb()).placeholder(R.mipmap.loading)
                .fitCenter().error(R.mipmap.load_fail)
                .crossFade().into((ImageView) itemView.findViewById(img_icon));

        RecyclerView recyclerView = (RecyclerView) itemView.findViewById(R.id.rc);
        final List<FriendBean.ImagesBean> images = bean.getImages();
        if (bean.getImages()==null||bean.getImages().size()==0){
            recyclerView.setVisibility(View.GONE);
        }else {
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
            recyclerView.setAdapter(new DefaultAdapter<FriendBean.ImagesBean>(context,images,R.layout.view_img,new DefaultAdapterViewLisenter<FriendBean.ImagesBean>(){
                @Override
                public CustomHolder getBodyHolder(Context context, List<FriendBean.ImagesBean> lists, int itemID) {
                    return new CustomHolder<FriendBean.ImagesBean>(context, lists, itemID){
                        @Override
                        public void initView(int position, List<FriendBean.ImagesBean> datas, final Context context) {
                            super.initView(position, datas, context);
                            final FriendBean.ImagesBean imagesBean = datas.get(position);
                            ImageView imageView = (ImageView) itemView.findViewById(img_icon);
                            GlideUtils.loadImage(context,imageView,imagesBean.getUrl());
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final XDialog dialog = DialogFactory.getInstance().creatDiaolg(context,DialogFactory.ALEART_DIALOG);
                                    ImageView view = new ImageView(context);
                                    view.setClickable(true);
                                    view.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                                    view.setLayoutParams(params);
                                    GlideUtils.loadImage(context,view,imagesBean.getUrl());
                                    dialog.show();
                                    dialog.setContentView(view);
                                    dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);
                                }
                            });
                        }
                    };
                }
            }));
        }


    }
}
