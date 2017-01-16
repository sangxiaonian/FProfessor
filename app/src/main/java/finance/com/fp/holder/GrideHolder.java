package finance.com.fp.holder;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.List;

import em.sang.com.allrecycleview.holder.HeardHolder;
import finance.com.fp.R;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.utlis.ToastUtil;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/3 10:22
 */
public class GrideHolder extends HeardHolder<Set_Item> {

    private GridView gv;
    RequestManager manager;
    private int numColuns=4;
    private int imageWidth,imageHeight;
    private LinearLayout.LayoutParams imgParams,textParams;

    public GrideHolder(Context context, List lists, int itemID) {
        super(context, lists, itemID);
        manager = Glide.with(context);
        imageWidth=(int) context.getResources().getDimension(R.dimen.card_item_gri_img);
        imageHeight=(int) context.getResources().getDimension(R.dimen.card_item_gri_img);
    }

    public GridView getGridView(){
        return (GridView) itemView.findViewById(R.id.gridview);
    }

    public void setImageParams(float width, float height, int numColuns ){
        this.imageHeight= (int) height;
        this.imageWidth= (int) width;
        this.numColuns=numColuns;
    }

    public void setImageParams(LinearLayout.LayoutParams params){
        imgParams=params;
    }

    public void setTextParams(LinearLayout.LayoutParams params){
        textParams=params;
    }

    @Override
    public void initView(final int position,final Context context) {
        super.initView(position, context);
        gv = (GridView) itemView.findViewById(R.id.gridview);
        gv.setNumColumns(numColuns);
        GVAdater adater = new GVAdater();
        gv.setAdapter(adater);
        int col=numColuns;
        int totalHeight=0;
        for (int i = 0; i < adater.getCount(); i += col) {
            // 获取listview的每一个item
            View listItem = adater.getView(i, null, gv);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight()+gv.getVerticalSpacing();
        }
        // 获取listview的布局参数
        ViewGroup.LayoutParams params = gv.getLayoutParams();
        // 设置高度
        params.height = totalHeight+gv.getPaddingBottom();

        // 设置参数
        gv.setLayoutParams(params);

    }



    public class GVAdater extends BaseAdapter {

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LinearLayout ll = new LinearLayout(context);


            int dimension = (int) context.getResources().getDimension(R.dimen.app_item_cut_margin);

            if (imgParams==null) {
                imgParams = new LinearLayout.LayoutParams(imageWidth, imageHeight);
                imgParams.setMargins(0, dimension, 0, dimension);
            }

            ll.setGravity(Gravity.CENTER);
            ll.setOrientation(LinearLayout.VERTICAL);
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(imgParams);
            TextView tv_title = new TextView(context);
            if (textParams!=null){
                tv_title.setLayoutParams(textParams);
            }
            tv_title.setTextColor(context.getResources().getColor(R.color.text_home_item));
            tv_title.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimensionPixelSize(R.dimen.card_item_small_textSize));
            tv_title.setGravity(Gravity.CENTER);

            final Set_Item item= (Set_Item) datas.get(position);
            if (item.icon_id>0) {
                manager.load(item.icon_id)
                        .centerCrop()
                        .into(imageView);
                ll.addView(imageView);
            }
            if (!TextUtils.isEmpty(item.title)){
                tv_title.setText(item.title);
                ll.addView(tv_title);
            }

            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showTextToast(context, item.title);
                }
            });
            return ll;
        }
    }


}
