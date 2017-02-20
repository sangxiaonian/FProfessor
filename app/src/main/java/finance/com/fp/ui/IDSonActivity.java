package finance.com.fp.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.List;

import finance.com.fp.BasisActivity;
import finance.com.fp.R;
import finance.com.fp.mode.bean.Config;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.presenter.IDSonPreComl;
import finance.com.fp.presenter.inter.IDSonPreInter;
import finance.com.fp.ui.inter.IDSonView;
import finance.com.fp.utlis.RecycleViewDivider;
import sang.com.xdialog.DialogFactory;
import sang.com.xdialog.XDialog;
import sang.com.xdialog.inter.OnEntryClickListener;

public class IDSonActivity extends BasisActivity implements IDSonView, OnEntryClickListener<String> {

    private RecyclerView rc;

    private IDSonPreInter pre;
    private DialogFactory factory;
    private XDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        initView();
        initData();

    }

    @Override
    public void initView() {
        super.initView();
        rc = (RecyclerView) findViewById(R.id.rc);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rc.setLayoutManager(manager);
        rc.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));

    }

    @Override
    public void initData() {
        super.initData();
        factory = DialogFactory.getInstance(this);
        pre = new IDSonPreComl(this);
        rc.setAdapter(pre.getAdapter(this, R.layout.item_card_more));
        pre.getTrans(this, Config.infors);
    }

    @Override
    public void onItemClick(int position, Object item) {
        pre.click(position, (Set_Item) item);
    }

    @Override
    public void initTitle(String title) {
        initToolBar(title);
        setColor(this, getResources().getColor(R.color.statucolor));
        toolbar.inflateMenu(R.menu.menu_next);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(IDSonActivity.this, IDActivity.class);
                String passString = "火星来的消息:Hello,我是火星的Jack，非常高兴你能来火星";
                intent.putExtra(Config.infors, passString);
                setResult(1, intent);
                finish();
                return true;
            }
        });
    }

    @Override
    public void showSelectDialog(final List<String> isHas) {
        dialog = factory.creatDiaolg(this, DialogFactory.SELECT_DIALOG);
        dialog.setOnClickListener(this);
        dialog.show(isHas);
    }

    @Override
    public void showPickerDialog(List<String> education) {
        dialog = factory.creatDiaolg(this, DialogFactory.PIKER_DIALOG);
        dialog.setDatas(education);
        dialog.setOnClickListener(this);
        dialog.showStyle(XDialog.BUTTON_UP);
    }

    @Override
    public void showEditDialog(String title) {
        dialog = factory.creatDiaolg(this, DialogFactory.ALEART_DIALOG);
        dialog.setTitle(title);
        dialog.setDatas("请输入" + title);
        dialog.setOnClickListener(this);
        dialog.showStyle(XDialog.ALEART_EDITTEXT);
    }


    @Override
    public void onClick(Dialog dialog, int which, String data) {
        pre.changeItem(data);
        dialog.dismiss();
    }
}
