package em.sang.com.allrecycleview.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/11/7 16:46
 */
public  class CustomPeakHolder<T> extends RecyclerView.ViewHolder {


    public   T data;
    public Context context;
    public View itemView;
    public List<T> datas;



    public CustomPeakHolder(View itemView) {
        super(itemView);
        this.itemView=itemView;
    }



    public CustomPeakHolder(Context context,int itemID, T data ){
        this(LayoutInflater.from(context).inflate(itemID,null));
        this.data= data;
        this.context =context;

    }

    public CustomPeakHolder(Context context, List<T> lists, int itemID) {
        this(LayoutInflater.from(context).inflate(itemID,null));
        this.context =context;
        this.datas=lists;
    }


    /**
     * 初始化holder中的数据
     * @param position
     * @return
     */
    public void initView(int position,Context context) {

    }


    public View getItemView(){
        return  itemView;
    }
}
