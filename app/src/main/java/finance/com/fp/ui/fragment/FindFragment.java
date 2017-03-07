package finance.com.fp.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import em.sang.com.allrecycleview.PullRecycleView;
import em.sang.com.allrecycleview.adapter.RefrushAdapter;
import em.sang.com.allrecycleview.inter.RefrushListener;
import finance.com.fp.BasisFragment;
import finance.com.fp.R;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.presenter.HomeFragmentPreComl;
import finance.com.fp.presenter.inter.HomeFragmentPre;
import finance.com.fp.ui.FriendActivity;
import finance.com.fp.ui.inter.HomeFramentView;
import finance.com.fp.utlis.RecycleViewDivider;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/12/27 16:41
 */
public class FindFragment extends BasisFragment implements HomeFramentView {


    private PullRecycleView rc;
    private HomeFragmentPre pre;
    private RefrushAdapter<Set_Item> adapter;


    @Override
    public View initViews(LayoutInflater inflater, ViewGroup container) {
       View view = inflater.inflate(R.layout.fragment_find, null);
        rc = (PullRecycleView) view.findViewById(R.id.rc_find);
        rc.setFlag("absdc");
        return view;
    }



    @Override
    protected void initData() {
        pre = new HomeFragmentPreComl(this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rc.setLayoutManager(manager);
        rc.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayout.VERTICAL));
        pre = new HomeFragmentPreComl(this);
        adapter = pre.initFindAdapter(getActivity());
        rc.setHasBoom(true);
        rc.setAdapter(adapter);

        rc.setRefrushListener(new RefrushListener() {
            @Override
            public void onLoading() {
                pre.getData();
            }

            @Override
            public void onLoadDowning() {
                pre.loadMore();
            }
        });
        rc.setLoading();


    }

    @Override
    public void onItemClick(int position, Set_Item item) {
        Intent intent = new Intent(getActivity(), FriendActivity.class);
        startActivity(intent);
    }

    @Override
    public void loadSuccess() {
        rc.loadSuccess();
    }

    @Override
    public void loadFail() {
        rc.loadFail();

    }

    @Override
    public void showLoad() {
        rc.setLoading();
    }
}
