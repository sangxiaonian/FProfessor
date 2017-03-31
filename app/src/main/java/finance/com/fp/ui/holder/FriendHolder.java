package finance.com.fp.ui.holder;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.sang.viewfractory.utils.ViewUtils;
import com.sang.viewfractory.view.FloatView;
import com.sang.viewfractory.view.MoveView;

import java.util.ArrayList;
import java.util.List;

import em.sang.com.allrecycleview.adapter.DefaultAdapter;
import em.sang.com.allrecycleview.holder.CustomHolder;
import em.sang.com.allrecycleview.inter.DefaultAdapterViewLisenter;
import finance.com.fp.R;
import finance.com.fp.mode.bean.FriendBean;
import finance.com.fp.utlis.Utils;
import sang.com.xdialog.DialogFactory;
import sang.com.xdialog.XDialog;
import sang.com.xdialog.XDialogBuilder;
import sang.com.xdialog.inter.OnEntryClickListener;

import static finance.com.fp.R.id.img_icon;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/2/26 15:29
 */
public class FriendHolder extends CustomHolder<FriendBean> {
    private XDialog dialog, load;
    private XDialogBuilder selectbuilder;

    public FriendHolder(Context context, List<FriendBean> lists, int itemID) {
        super(context, lists, itemID);
        dialog = DialogFactory.getInstance().creatDiaolg(context, DialogFactory.ALEART_DIALOG);
        selectbuilder = new XDialogBuilder(context);
        load = DialogFactory.getInstance().creatDiaolg(context, DialogFactory.LOAD_DIALOG);

    }

    @Override
    public void initView(final int position, List<FriendBean> datas, Context context) {
        super.initView(position, datas, context);
        final FriendBean bean = datas.get(position);
        TextView title = (TextView) itemView.findViewById(R.id.tv_title);
        if (!TextUtils.isEmpty(bean.getTitle())) {
            title.setText(bean.getTitle());
        }
        final FloatView content = (FloatView) itemView.findViewById(R.id.tv_content);
        if (bean.getContent() != null) {
            content.setText(Html.fromHtml(bean.getContent()));
        }

        TextView time = (TextView) itemView.findViewById(R.id.tv_time);
        if (bean.getUpdatetime() != null) {
            time.setText(ViewUtils.formatDateTime(bean.getUpdatetime() + "000"));
        }


        TextView textView = (TextView) itemView.findViewById(R.id.tv_details);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position, bean);

                }
            }
        });
        Glide.with(context).load(bean.getThumb()).placeholder(R.mipmap.loading)
                .fitCenter().error(R.mipmap.load_fail)
                .crossFade().into((ImageView) itemView.findViewById(img_icon));

        RecyclerView recyclerView = (RecyclerView) itemView.findViewById(R.id.rc);
        final List<FriendBean.ImagesBean> images = bean.getImages();
        if (bean.getImages() == null || bean.getImages().size() == 0) {
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
            recyclerView.setAdapter(new DefaultAdapter<FriendBean.ImagesBean>(context, images, R.layout.view_img, new DefaultAdapterViewLisenter<FriendBean.ImagesBean>() {
                @Override
                public CustomHolder getBodyHolder(Context context, List<FriendBean.ImagesBean> lists, int itemID) {
                    return new CustomHolder<FriendBean.ImagesBean>(context, lists, itemID) {
                        @Override
                        public void initView(int position, List<FriendBean.ImagesBean> datas, final Context context) {
                            super.initView(position, datas, context);
                            final FriendBean.ImagesBean imagesBean = datas.get(position);
                            final ImageView imageView = (ImageView) itemView.findViewById(img_icon);
                            Glide.with(context).load(imagesBean.getUrl()).asBitmap().into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(final Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    imageView.setImageBitmap(resource);
                                    imageView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            showImag(context, imagesBean, v,resource);
                                        }
                                    });
                                }
                            });

                        }
                    };
                }
            }));
        }


    }

    private void showImag(final Context context, final FriendBean.ImagesBean bean, View v, final Bitmap resource) {

        final MoveView view = new  MoveView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        view.setClickable(true);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        view.setListener(new   MoveView.MoveViewListener() {
            @Override
            public void onLongClick(View view) {
                setOnLongClick(view,context,resource);
            }


            @Override
            public void onClick(View view) {

            }


            @Override
            public void animotionEnd(View view) {
                dialog.dismiss();
            }
        });
        view.setOriginView(v,resource);
        dialog.show();
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

    }

    private void setOnLongClick(View v, final Context context, final Bitmap bitmap) {

        try {

            List<String> list = new ArrayList<String>();
            list.add(context.getResources().getString(R.string.friend_save));
            if (bitmap != null) {
                selectbuilder.setDatas(list)
                        .setThemeID(sang.com.xdialog.R.style.DialogTheme)
                        .setEntryListener(new OnEntryClickListener() {
                            @Override
                            public void onClick(Dialog dialog, int which, Object data) {
                                Utils.saveImageToGallery(context, bitmap);
                                dialog.dismiss();

                            }
                        })
                        .setDialogStyle(XDialogBuilder.SELECT_DIALOG)
                        .builder()
                        .show();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

