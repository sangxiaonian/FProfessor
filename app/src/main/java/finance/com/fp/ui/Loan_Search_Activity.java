package finance.com.fp.ui;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

import em.sang.com.allrecycleview.adapter.DefaultAdapter;
import em.sang.com.allrecycleview.holder.CustomHolder;
import em.sang.com.allrecycleview.inter.DefaultAdapterViewLisenter;
import finance.com.fp.BasisActivity;
import finance.com.fp.R;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.datafractory.DataLoadLisetner;
import finance.com.fp.mode.datafractory.LoanDataFractory;
import finance.com.fp.utlis.DividerGridItemDecoration;

public class Loan_Search_Activity extends BasisActivity {

    private CheckBox cb;
    private ImageView img;
    private LoanDataFractory fractory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan__search_);
        setColor(this, getResources().getColor(R.color.statucolor));
        initToolBar("网贷搜索");
        initView();

        initData();
    }

    @Override
    public void initView() {
        super.initView();
        cb = (CheckBox) findViewById(R.id.cb_search);
        img = (ImageView) findViewById(R.id.img_icon);
    }

    RecyclerView view;
    private List<Set_Item> searchs;
    DefaultAdapter adapter;

    @Override
    public void initData() {
        super.initData();
        searchs = new ArrayList<>();
        fractory = LoanDataFractory.getInstance();
        view = (RecyclerView) View.inflate(this, R.layout.view_recycleview, null);
        int dimension = (int) getResources().getDimension(R.dimen.app_margin_ends);
        view.setPadding(dimension,0,dimension, (int) (dimension*2.0/3));
        view.setBackground(new ColorDrawable(getResources().getColor(R.color.statucolor)));
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        view.addItemDecoration(new DividerGridItemDecoration(this,R.drawable.divider_line_rectangle));
        view.setLayoutManager(manager);
        adapter = new DefaultAdapter<Set_Item>(Loan_Search_Activity.this, searchs, R.layout.view_radiobutton, new DefaultAdapterViewLisenter<Set_Item>() {
            @Override
            public CustomHolder getBodyHolder(Context context, List<Set_Item> lists, int itemID) {
                return new CustomHolder<Set_Item>(context, lists, itemID) {
                    @Override
                    public void initView(final int position, final List<Set_Item> datas, Context context) {
                        super.initView(position, datas, context);
                        final Set_Item item = datas.get(position);
                        final RadioButton button = (RadioButton) this.itemView;
                        (button).setText(item.title);
                        button.setChecked(item.isCheck);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                button.setChecked(true);
                                for (int i = 0; i < datas.size(); i++) {
                                    if (position == i) {
                                        datas.get(i).isCheck = true;
                                    } else {
                                        datas.get(i).isCheck = false;
                                    }
                                }
                                updata(item);


                            }
                        });

                    }
                };
            }
        });
        view.setAdapter(adapter);
        fractory.getAllSearch(new DataLoadLisetner<Set_Item>() {
            @Override
            public void loadOver(List<Set_Item> lists) {
                searchs.clear();
                searchs.addAll(lists);
                adapter.notifyDataSetChanged();
            }
        });


        img.setEnabled(false);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    img.setEnabled(true);
                    showPopuWindow();
                } else {
                    img.setEnabled(false);
                    if (pop != null && pop.isShowing()) {
                        pop.dismiss();
                    }
                }
            }
        });


    }

    private void updata(Set_Item item) {
        adapter.notifyDataSetChanged();
        pop.dismiss();
        cb.setChecked(false);
        cb.setText(item.title);
    }

    private PopupWindow pop;

    private void showPopuWindow() {

        pop = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, false);
        pop.showAsDropDown(cb,0, (int) getResources().getDimension(R.dimen.home_item_time_margin));
    }

}
