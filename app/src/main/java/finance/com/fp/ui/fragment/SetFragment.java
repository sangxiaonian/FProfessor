package finance.com.fp.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import em.sang.com.allrecycleview.adapter.DefaultAdapter;
import em.sang.com.allrecycleview.cutline.RecycleViewDivider;
import em.sang.com.allrecycleview.holder.CustomHolder;
import em.sang.com.allrecycleview.inter.DefaultAdapterViewLisenter;
import finance.com.fp.BasisFragment;
import finance.com.fp.R;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.http.Config;
import finance.com.fp.ui.IDActivity;
import finance.com.fp.ui.RegisterActivity;
import finance.com.fp.ui.holder.SetBodyHolder;
import finance.com.fp.utlis.Utils;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/12/27 16:41
 */
public class SetFragment extends BasisFragment {

    private View view;
    private RecyclerView rc;
    private TextView textView;
    private int[] icons = {R.mipmap.icon_handlecardprogress, R.mipmap.icon_netcreditinquiry,R.mipmap.icon_personalcenter
            , R.mipmap.icon_advices, R.mipmap.icon_feedback, R.mipmap.icon_set};
    private List<Set_Item> lists;

    @Override
    public View initViews(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_set, null);
        rc = (RecyclerView) view.findViewById(R.id.rc_find);
        textView= (TextView) view.findViewById(R.id.tv_set);

        return view;
    }

    @Override
    protected void initData() {
        String[] titles = getContext().getResources().getStringArray(R.array.set_items_title);
        lists = new ArrayList<>();

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.isLogion(getContext())) {
                    Intent intent = new Intent(getActivity(), RegisterActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getActivity(), IDActivity.class);
                    startActivity(intent);
                }
            }
        });


        for (int i = 0; i < icons.length; i++) {
            Set_Item item = new Set_Item();
            item.icon_id = icons[i];
            item.title = titles[i];
            lists.add(item);
        }

        DefaultAdapter<Set_Item> adapter = new DefaultAdapter<>(getContext(), lists, R.layout.item_set_item, new DefaultAdapterViewLisenter<Set_Item>() {
            @Override
            public CustomHolder getBodyHolder(Context context, List<Set_Item> lists, int itemID) {
                return new SetBodyHolder(context, lists, itemID);
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rc.setLayoutManager(manager);
        rc.addItemDecoration(new RecycleViewDivider(getContext(),LinearLayoutManager.VERTICAL));
        rc.setAdapter(adapter);

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            if (Utils.isLogion(getContext())){
                textView.setText(Utils.getSp(getContext(), Config.login_name));
            }else {
                textView.setText(getString(R.string.set_item));
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Utils.isLogion(getContext())){
            textView.setText(Utils.getSp(getContext(), Config.login_name));
        }else {
            textView.setText(getString(R.string.set_item));
        }
    }
}
