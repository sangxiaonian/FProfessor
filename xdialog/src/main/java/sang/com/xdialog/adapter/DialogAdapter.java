package sang.com.xdialog.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import sang.com.xdialog.R;
import sang.com.xdialog.inter.OnEntryClickListener;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/2/9 17:16
 */
public class DialogAdapter extends RecyclerView.Adapter{

    private final int layoutID;
    private final List<String> datas;
    private final Context context;

    public DialogAdapter(Context context, List<String> datas, int layoutID){
        this.context=context;
        this.datas=datas;
        this.layoutID=layoutID;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new DialogHolder(layoutID,context);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DialogHolder h = (DialogHolder) holder;

        h.initView(position,datas.get(position));

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    private OnEntryClickListener<String> listener;

    public void setListener(OnEntryClickListener<String> listener) {
        this.listener = listener;
    }



    public class DialogHolder extends RecyclerView.ViewHolder{

        public View view;
        public TextView tv_msg;
        public DialogHolder(int layoutId,Context context) {
            this( View.inflate(context,layoutId,null));

        }
        public DialogHolder(View itemView) {
            super(itemView);
            view=itemView;
        }


        public void initView(final int position, final String t) {

            tv_msg= (TextView) view.findViewById(R.id.txt_item);
            tv_msg.setClickable(true);
            tv_msg.setText(t);
            tv_msg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        listener.onClick(null,position,t);
                    }
                }
            });
        }
    }

}
