package finance.com.fp.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import em.sang.com.allrecycleview.adapter.DefaultAdapter;
import em.sang.com.allrecycleview.cutline.RecycleViewDivider;
import em.sang.com.allrecycleview.holder.CustomHolder;
import em.sang.com.allrecycleview.holder.SimpleHolder;
import em.sang.com.allrecycleview.inter.DefaultAdapterViewLisenter;
import em.sang.com.allrecycleview.listener.OnToolsItemClickListener;
import finance.com.fp.BasisActivity;
import finance.com.fp.R;
import finance.com.fp.mode.bean.CarPerson;
import finance.com.fp.mode.bean.ChitPerson;
import finance.com.fp.mode.bean.CreditPerson;
import finance.com.fp.mode.bean.HousePerson;
import finance.com.fp.mode.bean.IDBean;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.bean.TranInfor;
import finance.com.fp.mode.datafractory.HttpFactory;
import finance.com.fp.mode.http.Config;
import finance.com.fp.mode.http.HttpClient;
import finance.com.fp.mode.http.HttpParams;
import finance.com.fp.ui.holder.IDHolder;
import finance.com.fp.utlis.Utils;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import sang.com.xdialog.DialogFactory;
import sang.com.xdialog.XDialog;
import sang.com.xdialog.inter.OnEntryClickListener;

/**
 * 账号信息
 */
public class IDActivity extends BasisActivity implements OnToolsItemClickListener<Set_Item>, OnEntryClickListener<String>, Observer<String> {

    private RecyclerView rc;
    private List<Set_Item> lists;
    private List<String> titles;
    private XDialog dialog;

    private DefaultAdapter adapter;
    private DialogFactory dialogFactory;
    private final int REQUESTCODE = 2;
    private HttpParams params;

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
        dialog = dialogFactory.creatDiaolg(this, DialogFactory.LOAD_DIALOG);
        params = HttpParams.getInstance();
        HttpFactory.getID(Utils.getSp(this,Config.login_name))
                .filter(new Func1<IDBean, Boolean>() {
                    @Override
                    public Boolean call(IDBean idBeanHttpBean) {
                        return idBeanHttpBean.getTitle() != null;
                    }
                })
                .map(new Func1<IDBean, IDBean.TitleBean>() {
                    @Override
                    public IDBean.TitleBean call(IDBean idBean) {
                        return idBean.getTitle();
                    }
                })
                .subscribe(new Subscriber<IDBean.TitleBean>() {


                    @Override
                    public void onStart() {
                        super.onStart();
                        dialog.show();
                    }

                    @Override
                    public void onCompleted() {
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();

                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(IDBean.TitleBean idBean) {
                        String[] array = getResources().getStringArray(R.array.id_items);
                        for (int i = 0; i < array.length; i++) {
                            changeData(idBean, i);
                        }

                    }

                });

    }

    public void click(View view) {
        dialog.show();
        HttpClient.getClient(Config.base_url).subPerson(params.removeNull()).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(this);


    }


    public void initView() {
        rc = (RecyclerView) findViewById(R.id.rc);
        initToolBar(getString(R.string.id_infor));
        rc.setLayoutManager(new LinearLayoutManager(this));
        rc.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));

        String[] array = getResources().getStringArray(R.array.id_items);
        lists = new ArrayList<>();


        for (int i = 0; i < array.length; i++) {
            String s = array[i];
            Set_Item item = new Set_Item(0, s);
            if (i < 3 || i == 4) {
                item.isCheck = true;
            } else {
                item.isCheck = false;
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
        } else {
            TranInfor infor = new TranInfor();
            infor.title = item.title;
            infor.item_id = position;
            Intent intent = new Intent(this, IDSonActivity.class);
            intent.putExtra(Config.infors, infor);
            startActivityForResult(intent, position);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1) {
            String extra = data.getStringExtra(Config.infors);
            if (!TextUtils.isEmpty(extra)) {
                position = requestCode;
                item.describe = "已填写";
                adapter.notifyDataSetChanged();
                putDataByPosition(extra);
            }
        }
    }


    private void creatAleart(int position, Set_Item item) {
        this.position = position;
        dialog = dialogFactory.creatDiaolg(this);
        dialog.setTitle(item.title);
        dialog.setDatas("请输入" + item.title);
        dialog.setOnClickListener(this);
        dialog.showStyle(XDialog.ALEART_EDITTEXT);
    }

    private void creatSex() {
        dialog = dialogFactory.creatDiaolg(this, DialogFactory.PIKER_DIALOG);
        titles.clear();
        titles.add("男");
        titles.add("女");
        dialog.setDatas(titles);
        dialog.setOnClickListener(this);
        dialog.showStyle(XDialog.BUTTON_UP);
    }


    @Override
    public void onClick(Dialog dialog, int which, String data) {
        item.describe = data;
        adapter.notifyDataSetChanged();
        dialog.dismiss();
        putDataByPosition(data);
    }

    private void changeData(IDBean.TitleBean data, int position) {

        switch (position) {
            case 0:
                params.put("username", data.getTel());
                lists.get(position).describe = data.getTel();
                break;
            case 1:
                params.put("name", data.getName());
                lists.get(position).describe = data.getName();
                break;
            case 2:
                params.put("card", data.getCard());
                lists.get(position).describe = data.getCard();
                break;
            case 3:
                lists.get(position).describe = data.getSex();
                params.put("sex", data.getSex());
                break;
            case 4:
                params.put("city", data.getCity());
                lists.get(position).describe = data.getCity();
                break;
            case 5:

                params.put("credit_condition1", data.getCredit_condition1());
                params.put("credit_condition2", data.getCredit_condition2());
                params.put("credit_condition3", data.getCredit_condition3());
                params.put("credit_condition4", data.getCredit_condition4());
                params.put("credit_condition5", data.getCredit_condition5());
                params.put("credit_condition6", data.getCredit_condition6());
                if (!isEmpty(data.getCredit_condition1(), data.getCredit_condition2(), data.getCredit_condition3(), data.getCredit_condition4()
                        , data.getCredit_condition5(), data.getCredit_condition6())) {

                    lists.get(position).describe = "已填写";
                }
                break;
            case 6:
                params.put("house_condition1", data.getHouse_condition1());
                params.put("house_condition2", data.getHouse_condition2());
                params.put("house_condition3", data.getHouse_condition3());
                params.put("house_condition4", data.getHouse_condition4());
                params.put("house_condition5", data.getHouse_condition5());
                params.put("house_condition6", data.getHouse_condition6());
                if (!isEmpty(data.getHouse_condition1(), data.getHouse_condition2(), data.getHouse_condition3(), data.getHouse_condition4()
                        , data.getHouse_condition5(), data.getHouse_condition6())) {
                    lists.get(position).describe = "已填写";
                }
                break;
            case 7:
                params.put("car_condition1", data.getCar_condition1());
                params.put("car_condition2", data.getCar_condition2());
                params.put("car_condition3", data.getCar_condition3());
                params.put("car_condition4", data.getCar_condition4());
                params.put("car_condition5", data.getCar_condition5());
                if (!isEmpty(data.getCar_condition5(), data.getCar_condition4(), data.getCar_condition3(), data.getCar_condition2()
                        , data.getCar_condition1())) {
                    lists.get(position).describe = "已填写";
                }

                break;
            case 8:
                params.put("policy_condition1", data.getPolicy_condition1());
                params.put("policy_condition2", data.getPolicy_condition2());
                if (!isEmpty(data.getPolicy_condition1(), data.getPolicy_condition2())) {
                    lists.get(position).describe = "已填写";
                }
                break;

        }
    }

    private boolean isEmpty(String... s) {
        boolean isEmpty = true;
        for (int i = 0; i < s.length; i++) {
            if (!TextUtils.isEmpty(s[i])) {
                isEmpty = false;
                break;
            }
        }
        return isEmpty;
    }

    private void putDataByPosition(String data) {
        Gson gson = new Gson();
        switch (position) {
            case 0:
                params.put("username", data);
                break;
            case 1:
                params.put("name", data);
                break;
            case 2:
                params.put("card", data);
                break;
            case 3:
                params.put("sex", data);
                break;
            case 4:
                params.put("city", data);
                break;
            case 5:
                CreditPerson person = gson.fromJson(data, CreditPerson.class);
                params.put("credit_condition1", person.credit_condition1);
                params.put("credit_condition2", person.credit_condition2);
                params.put("credit_condition3", person.credit_condition3);
                params.put("credit_condition4", person.credit_condition4);
                params.put("credit_condition5", person.credit_condition5);
                params.put("credit_condition6", person.credit_condition6);
                params.put("credit_condition7", person.credit_condition7);
                break;
            case 6:
                HousePerson house = gson.fromJson(data, HousePerson.class);
                params.put("house_condition1", house.house_condition1);
                params.put("house_condition2", house.house_condition2);
                params.put("house_condition3", house.house_condition3);
                params.put("house_condition4", house.house_condition4);
                params.put("house_condition5", house.house_condition5);
                params.put("house_condition6", house.house_condition6);
                break;
            case 7:
                CarPerson car = gson.fromJson(data, CarPerson.class);
                params.put("car_condition1", car.car_condition1);
                params.put("car_condition2", car.car_condition2);
                params.put("car_condition3", car.car_condition3);
                params.put("car_condition4", car.car_condition4);
                params.put("car_condition5", car.car_condition5);

                break;
            case 8:
                ChitPerson chit = gson.fromJson(data, ChitPerson.class);
                params.put("policy_condition1", chit.policy_condition1);
                params.put("policy_condition2", chit.policy_condition2);
                break;

        }
    }


    @Override
    public void onCompleted() {
        dialog.dismiss();

        Intent intent = new Intent(this, Loan_Search_Activity.class);

        startActivity(intent);
    }

    @Override
    public void onError(Throwable e) {
        dialog.dismiss();
        e.printStackTrace();
        Intent intent = new Intent(this, Loan_Search_Activity.class);
        startActivity(intent);
    }

    @Override
    public void onNext(String s) {

    }
}
