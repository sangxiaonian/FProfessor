package finance.com.fp.ui;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import em.sang.com.allrecycleview.PullRecycleView;
import em.sang.com.allrecycleview.adapter.RefrushAdapter;
import em.sang.com.allrecycleview.holder.CustomHolder;
import em.sang.com.allrecycleview.inter.DefaultAdapterViewLisenter;
import finance.com.fp.BasisFragment;
import finance.com.fp.R;
import finance.com.fp.holder.FindFriendHolder;
import finance.com.fp.holder.FindFunctionHolder;
import finance.com.fp.holder.HomeBodyHolder;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/12/27 16:41
 */
public class FindFragment extends BasisFragment {

    private View view;
    private TextView tv;
    private ArrayList<String> lists;
    PullRecycleView rc;
    @Override
    public View initViews(LayoutInflater inflater, ViewGroup container) {
        view=inflater.inflate(R.layout.fragment_find,null);

        rc = (PullRecycleView) view.findViewById(R.id.rc_find);
        initData();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    protected void initData() {
        lists=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            lists.add("这是测试数据"+i);
        }


        RefrushAdapter<String> adapter = new RefrushAdapter<>(getContext(), lists, R.layout.item_home, new DefaultAdapterViewLisenter<String>() {
            @Override
            public CustomHolder getBodyHolder(Context context, List<String> lists, int itemID) {
                return new HomeBodyHolder(context, lists, itemID);
            }
        });
        adapter.setRefrushPosition(2);
        adapter.addHead(new FindFriendHolder(getContext(),null,R.layout.item_find_fc));
        adapter.addHead(new FindFunctionHolder(getContext(),null,R.layout.item_find_function));
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rc.setLayoutManager(manager);
        rc.setAdapter(adapter);

    }
}
