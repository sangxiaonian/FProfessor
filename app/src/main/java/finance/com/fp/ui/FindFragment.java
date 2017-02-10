package finance.com.fp.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import em.sang.com.allrecycleview.PullRecycleView;
import em.sang.com.allrecycleview.adapter.RefrushAdapter;
import finance.com.fp.BasisFragment;
import finance.com.fp.R;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.presenter.inter.HomeFragmentPre;
import finance.com.fp.presenter.HomeFragmentPreComl;
import finance.com.fp.ui.inter.HomeFramentView;
import finance.com.fp.utlis.RecycleViewDivider;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/12/27 16:41
 */
public class FindFragment extends BasisFragment implements HomeFramentView{

    private View view;
    private TextView tv;
    private ArrayList<String> lists;
    private PullRecycleView rc;
    private HomeFragmentPre pre;
    private RefrushAdapter<Set_Item> adapter;


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
        pre = new HomeFragmentPreComl(this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rc.setLayoutManager(manager);
        rc.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayout.VERTICAL));
        pre = new HomeFragmentPreComl(this);
        adapter=pre.initFindAdapter(getContext());
        rc.setAdapter(adapter);




    }

    @Override
    public void onItemClick(int position, Set_Item item) {

    }
}
