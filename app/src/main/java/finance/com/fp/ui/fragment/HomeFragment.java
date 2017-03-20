package finance.com.fp.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import em.sang.com.allrecycleview.RefrushRecycleView;
import em.sang.com.allrecycleview.adapter.RefrushAdapter;
import em.sang.com.allrecycleview.cutline.RecycleViewDivider;
import em.sang.com.allrecycleview.inter.DefaultRefrushListener;
import finance.com.fp.BasisFragment;
import finance.com.fp.R;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.bean.TranInfor;
import finance.com.fp.mode.datafractory.HomeDataFractory;
import finance.com.fp.mode.http.Config;
import finance.com.fp.presenter.HomeFragmentPreComl;
import finance.com.fp.presenter.inter.HomeFragmentPre;
import finance.com.fp.ui.CardActivity;
import finance.com.fp.ui.HomeSonActivity;
import finance.com.fp.ui.ImportActivity;
import finance.com.fp.ui.LoanActivity;
import finance.com.fp.ui.PlannerActivity;
import finance.com.fp.ui.inter.HomeFramentView;
import finance.com.fp.ui.inter.MainView;
import finance.com.fp.utlis.ToastUtil;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/12/27 16:41
 */
public class HomeFragment extends BasisFragment implements View.OnClickListener, HomeFramentView {


    private RefrushRecycleView recyclerView;
    private ImageView msg_red;
    private ImageButton title_card, ltitle_ending, title_forheard, msg, msg_small;
    private LinearLayout tv_card, tv_loan, tv_import;
    private HomeFragmentPre pre;
    private RefrushAdapter adapter;
    private MainView view;


    @Override
    public View initViews(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_main, null);
        recyclerView = (RefrushRecycleView) view.findViewById(R.id.rc_home);
        title_card = (ImageButton) view.findViewById(R.id.img_main_card_icon);
        ltitle_ending = (ImageButton) view.findViewById(R.id.img_main_net_icon);
        title_forheard = (ImageButton) view.findViewById(R.id.img_main_imp_icon);
        msg = (ImageButton) view.findViewById(R.id.img_main_msg);
        msg_small = (ImageButton) view.findViewById(R.id.img_main_msg_small);
        tv_card = (LinearLayout) view.findViewById(R.id.tv_card);
        tv_import = (LinearLayout) view.findViewById(R.id.tv_improt);
        tv_loan = (LinearLayout) view.findViewById(R.id.tv_loan);
        msg_red = (ImageView) view.findViewById(R.id.img_red);

        return view;
    }


    @Override
    public void initListener() {
        super.initListener();

        title_card.setOnClickListener(this);
        ltitle_ending.setOnClickListener(this);
        title_forheard.setOnClickListener(this);
        msg.setOnClickListener(this);
        msg_small.setOnClickListener(this);

        tv_import.setOnClickListener(this);
        tv_loan.setOnClickListener(this);
        tv_card.setOnClickListener(this);
            if (view != null) {
                if (view.hasNewMsg()) {
                    showRed();
                } else {
                    msg_red.setVisibility(View.GONE);
                }
            }
    }


    @Override
    protected void initData() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayout.VERTICAL));
        pre = new HomeFragmentPreComl(this);
        adapter = pre.initAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setRefrushListener(new DefaultRefrushListener() {
            @Override
            public void onLoading() {
                pre.getFinceData(6);
            }
        });
        recyclerView.setLoading();


    }

    @Override
    public void onClick(View v) {

        Class c = null;
        Intent intent = new Intent();
        switch (v.getId()) {

            case R.id.img_main_card_icon:
            case R.id.tv_card:
                c = CardActivity.class;
                break;

            case R.id.img_main_net_icon:
            case R.id.tv_loan:
                c = LoanActivity.class;
                break;

            case R.id.img_main_imp_icon:
            case R.id.tv_improt:
                c = ImportActivity.class;
                break;
            case R.id.img_main_msg:
            case R.id.img_main_msg_small:
                removeRed();
                c = HomeSonActivity.class;
                TranInfor tranInfor = new TranInfor();
                tranInfor.activity_id = 0;
                tranInfor.item_id = 4;
                tranInfor.title = getString(R.string.msg_center);
                intent.putExtra(Config.infors, tranInfor);
                break;
        }
        if (c == null) {
            ToastUtil.showTextToast(cnt, "该功能尚未开放");
        } else {
            intent.setClass(cnt, c);
            cnt.startActivity(intent);
        }
    }

    private void removeRed() {
        if (view != null) {
            view.removeMsg();
            msg_red.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pre.unsubscribe();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainView) {
            view = (MainView) context;
        }
    }


    @Override
    public void onItemClick(int position, Set_Item item) {
        TranInfor tranInfor = new TranInfor();
        Intent intent = new Intent();
        tranInfor.activity_id = 0;

        tranInfor.title = item.title;
        Class c = null;
        switch (position) {
            case 0:
                tranInfor.item_id = position;
                c = PlannerActivity.class;
//                c= MapActivity.class;
                break;
            case 1:
                c = HomeSonActivity.class;
                tranInfor.item_id = HomeDataFractory.BALANCE_CALL;
                break;
            case 2:
                c = HomeSonActivity.class;
                tranInfor.item_id = HomeDataFractory.CREDIT;
                break;
            case 3:
                tranInfor.item_id = HomeDataFractory.UTILITY_TOLL;
                c = HomeSonActivity.class;
                break;
        }
        if (c == null) {
            ToastUtil.showTextToast("该功能尚未开放");
        } else {
            intent.putExtra(Config.infors, tranInfor);
            intent.setClass(getActivity(), c);
            getActivity().startActivity(intent);
        }
    }

    @Override
    public void loadSuccess() {
        recyclerView.loadSuccess();
    }

    @Override
    public void loadFail() {
        recyclerView.loadFail();
    }

    @Override
    public void showLoad() {
        recyclerView.setLoading();
    }

    @Override
    public void loadNoMore() {
        recyclerView.LoadNoMore();
    }

    public void showRed() {
        msg_red.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (view.hasNewMsg()) {
                msg_red.setVisibility(View.VISIBLE);
            } else {
                msg_red.setVisibility(View.GONE);
            }
        } else {
            pre.getCouser();
        }
    }
}
