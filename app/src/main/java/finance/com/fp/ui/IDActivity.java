package finance.com.fp.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import em.sang.com.allrecycleview.adapter.DefaultAdapter;
import em.sang.com.allrecycleview.holder.CustomHolder;
import em.sang.com.allrecycleview.holder.SimpleHolder;
import em.sang.com.allrecycleview.inter.DefaultAdapterViewLisenter;
import em.sang.com.allrecycleview.listener.OnToolsItemClickListener;
import finance.com.fp.BasisActivity;
import finance.com.fp.R;
import finance.com.fp.ui.holder.IDHolder;
import finance.com.fp.mode.bean.Config;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.bean.TranInfor;
import finance.com.fp.mode.datafractory.ImprotFactory;
import finance.com.fp.utlis.RecycleViewDivider;
import sang.com.xdialog.DialogFactory;
import sang.com.xdialog.XDialog;
import sang.com.xdialog.inter.OnEntryClickListener;

/**
 * 账号信息
 */
public class IDActivity extends BasisActivity implements OnToolsItemClickListener<Set_Item>,OnEntryClickListener<String> {

    private RecyclerView rc;
    private List<Set_Item> lists;
    private List<String> titles;
    private XDialog dialog;
    private DialogFactory factory;
    private DefaultAdapter adapter;
    private DialogFactory dialogFactory;
    private final int REQUESTCODE=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id);
        setColor(this, getResources().getColor(R.color.statucolor));
        initView();
        initData();
    }

    @Override
    public void initData() {
        super.initData();
        titles = new ArrayList<>();
        dialogFactory = DialogFactory.getInstance(this);


    }

    public void click(View view){
        TranInfor tranInfor = new TranInfor();
        Intent intent = new Intent(this,HomeSonActivity.class);
        tranInfor.activity_id = 2;
        tranInfor.item_id= ImprotFactory.LOAN_ONE_KEY_IPMORT;
        tranInfor.title = getString(R.string.import_at_once);
        intent.putExtra(Config.infors, tranInfor);
        startActivity(intent);

    }



    public void initView() {
        rc = (RecyclerView) findViewById(R.id.rc);
        initToolBar(getString(R.string.id_infor));
        rc.setLayoutManager(new LinearLayoutManager(this));
        rc.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));

        String[] array = getResources().getStringArray(R.array.id_items);
        lists = new ArrayList<>();


        for (int i = 0; i <array.length ; i++) {
            String s = array[i];
            Set_Item item = new Set_Item(0, s);
            if (i < 3 || i == 4){
                item.isCheck=true;
            }else {
                item.isCheck=false;
            }
            lists.add(item);
        }

        adapter = new DefaultAdapter<Set_Item>(this, lists, R.layout.item_card_more, new DefaultAdapterViewLisenter<Set_Item>() {
            @Override
            public CustomHolder getBodyHolder(Context context, List lists, final int itemID) {
                IDHolder<Set_Item> holder = new IDHolder<Set_Item>(context, lists, itemID);
                holder.setOnClickListener(IDActivity.this);
                return holder;
            }
        });

        adapter.addHead(new SimpleHolder(this, R.layout.item_id_heard));
        rc.setAdapter(adapter);
    }


    private int position;
    private Set_Item item;

    @Override
    public void onItemClick(int position, Set_Item item) {
        this.position = position;
        this.item = item;
        if (position == 3) {
            creatSex();
        } else if (position < 3 || position == 4) {
            creatAleart(position, item);
        }else {
            TranInfor infor = new TranInfor();
            infor.title=item.title;
            infor.item_id=position;
            Intent intent = new Intent(this,IDSonActivity.class);
            intent.putExtra(Config.infors,infor);
            startActivityForResult(intent,REQUESTCODE);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUESTCODE){
//            Logger.i(data.getStringExtra(Config.infors));
        }
    }

    private void creatAleart(int position, Set_Item item) {
        dialog = dialogFactory.creatDiaolg(this);
        dialog.setTitle(item.title);
        dialog.setDatas("请输入"+item.title);
        dialog.setOnClickListener(this);
        dialog.showStyle(XDialog.ALEART_EDITTEXT);
    }

    private void creatSex() {
        dialog = dialogFactory.creatDiaolg(this,DialogFactory.PIKER_DIALOG);
        titles.clear();
        titles.add("男");
        titles.add("女");
        dialog.setDatas(titles);
        dialog.setOnClickListener(this);
        dialog.showStyle(XDialog.BUTTON_UP);
    }




    @Override
    public void onClick(Dialog dialog, int which, String data) {
        item.describe =  data;
        adapter.notifyDataSetChanged();
        dialog.dismiss();
    }
}
