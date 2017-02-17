package finance.com.fp.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import em.sang.com.allrecycleview.PullUPRecycleView;
import em.sang.com.allrecycleview.adapter.RefrushAdapter;
import em.sang.com.allrecycleview.inter.DefaultRefrushListener;
import finance.com.fp.BasisFragment;
import finance.com.fp.R;
import finance.com.fp.mode.bean.Config;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.bean.TranInfor;
import finance.com.fp.mode.datafractory.HomeDataFractory;
import finance.com.fp.presenter.HomeFragmentPreComl;
import finance.com.fp.presenter.inter.HomeFragmentPre;
import finance.com.fp.ui.CardActivity;
import finance.com.fp.ui.HomeSonActivity;
import finance.com.fp.ui.ImportActivity;
import finance.com.fp.ui.LoanActivity;
import finance.com.fp.ui.PlannerActivity;
import finance.com.fp.ui.inter.HomeFramentView;
import finance.com.fp.utlis.RecycleViewDivider;
import finance.com.fp.utlis.ToastUtil;
import sang.com.xdialog.PickerDialog;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/12/27 16:41
 */
public class HomeFragment extends BasisFragment implements View.OnClickListener, HomeFramentView {


    private PullUPRecycleView recyclerView;

    private ImageButton card, lending, forheard, title_card, ltitle_ending, title_forheard,msg,msg_small,img_scan;

    private HomeFragmentPre pre;
    private RefrushAdapter adapter;

    @Override
    public View initViews(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_main, null);
        recyclerView = (PullUPRecycleView) view.findViewById(R.id.rc_home);
        card = (ImageButton) view.findViewById(R.id.img_main_card);
        lending = (ImageButton) view.findViewById(R.id.img_main_net);
        forheard = (ImageButton) view.findViewById(R.id.img_main_imp);
        title_card = (ImageButton) view.findViewById(R.id.img_main_card_icon);
        ltitle_ending = (ImageButton) view.findViewById(R.id.img_main_net_icon);
        title_forheard = (ImageButton) view.findViewById(R.id.img_main_imp_icon);
        msg = (ImageButton) view.findViewById(R.id.img_main_msg);
        msg_small = (ImageButton) view.findViewById(R.id.img_main_msg_small);
        img_scan= (ImageButton) view.findViewById(R.id.img_main_scan);
        return view;
    }


    @Override
    public void initListener() {
        super.initListener();
        card.setOnClickListener(this);
        lending.setOnClickListener(this);
        forheard.setOnClickListener(this);
        title_card.setOnClickListener(this);
        ltitle_ending.setOnClickListener(this);
        title_forheard.setOnClickListener(this);
        msg.setOnClickListener(this);
        msg_small.setOnClickListener(this);
        img_scan.setOnClickListener(this);
    }


    @Override
    protected void initData() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayout.VERTICAL));
        pre = new HomeFragmentPreComl(this);
        adapter=pre.initAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setRefrushListener(new DefaultRefrushListener(){
            @Override
            public void onLoading() {
                pre.getFinceData(3);
            }
        });
        recyclerView.setLoading();

    }

    @Override
    public void onClick(View v) {
        Class c = null;
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.img_main_card:
            case R.id.img_main_card_icon:
                c = CardActivity.class;
                break;
            case R.id.img_main_net:
            case R.id.img_main_net_icon:
                c = LoanActivity.class;
                break;
            case R.id.img_main_imp:
            case R.id.img_main_imp_icon:
                c = ImportActivity.class;
                break;
            case R.id.img_main_msg:
            case R.id.img_main_msg_small:
                c=HomeSonActivity.class;
                TranInfor tranInfor = new TranInfor();
                tranInfor.activity_id = 0;
                tranInfor.item_id = 4;
                tranInfor.title = getString(R.string.msg_center);
                intent.putExtra(Config.infors,tranInfor);
                break;
            case R.id.img_main_scan:
                showDialog();
                break;

        }
        if (c == null) {

            ToastUtil.showTextToast(cnt, "该功能尚未开放");
        } else {
            intent.setClass(cnt, c);
            cnt.startActivity(intent);
        }

    }

    private void  showDialog(){
        PickerDialog dialog = new PickerDialog(getContext());
        dialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pre.unsubscribe();
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
                c=PlannerActivity.class;
                break;
            case 1:
                c=HomeSonActivity.class;
                tranInfor.item_id = HomeDataFractory.BALANCE_CALL;
                break;
            case 2:
                c=HomeSonActivity.class;
                tranInfor.item_id = HomeDataFractory.CREDIT;
                break;
            case 3:
                tranInfor.item_id = HomeDataFractory.UTILITY_TOLL;
                c=HomeSonActivity.class;
                break;
        }
        if (c==null){
            ToastUtil.showTextToast("该功能尚未开放");
        }else {
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
}
