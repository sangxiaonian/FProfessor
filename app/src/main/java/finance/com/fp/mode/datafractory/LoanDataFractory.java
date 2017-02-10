package finance.com.fp.mode.datafractory;

import java.util.ArrayList;
import java.util.List;

import finance.com.fp.R;
import finance.com.fp.mode.bean.Set_Item;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Description：办卡功能数据
 *
 * @Author：桑小年
 * @Data：2017/1/11 14:49
 */
public class LoanDataFractory  extends BaseFractory {


    public static LoanDataFractory fractory;

    public static LoanDataFractory getInstance() {
        if (fractory == null) {
            synchronized (LoanDataFractory.class) {
                if (fractory == null) {
                    fractory = new LoanDataFractory( );
                }
            }
        }
        return fractory;
    }









    /**
     * 网贷攻略
     *
     * @return
     */
    public List<Set_Item> getLoanStragety() {
        lists.clear();
        String[] titles = context.getResources().getStringArray(R.array.loan_strategy);
        for (String s : titles) {
            lists.add(new Set_Item(0, s));
        }
        return lists;
    }

    ArrayList<Set_Item> lists = new ArrayList<>();


    public List<Set_Item> getLoanStrategyData(final DataLoadLisetner<Set_Item> lisetner) {


        String[] titles = context.getResources().getStringArray(R.array.loan_grid);
        Observable.from(titles)
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, Set_Item>() {
                    @Override
                    public Set_Item call(String s) {
                        Set_Item item = new Set_Item();
                        item.title = s;
                        return item;
                    }
                })

                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Set_Item>() {
                    @Override
                    public void onCompleted() {
                        if (lisetner != null) {
                            lisetner.loadOver(lists);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Set_Item set_item) {
                        lists.add(set_item);
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        if (lists == null) {
                            lists = new ArrayList<Set_Item>();
                        } else {
                            lists.clear();
                        }
                    }
                });

        return lists;

    }

    public void getAllSearch(final DataLoadLisetner<Set_Item> lisetner) {
        String[] titles = context.getResources().getStringArray(R.array.search_items);
        Observable.from(titles)
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, Set_Item>() {
                    @Override
                    public Set_Item call(String s) {
                        Set_Item item = new Set_Item();
                        item.title = s;
                        return item;
                    }
                })

                .observeOn(AndroidSchedulers.mainThread())
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        if (lists.size() > 0) {
                            lists.get(0).isCheck = true;
                        }
                    }
                })
                .subscribe(new Subscriber<Set_Item>() {
                    @Override
                    public void onCompleted() {
                        if (lisetner != null) {
                            lisetner.loadOver(lists);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Set_Item set_item) {
                        lists.add(set_item);
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        if (lists == null) {
                            lists = new ArrayList<Set_Item>();
                        } else {
                            lists.clear();
                        }
                    }
                });

    }


    /**
     * 网袋专区页面三个条目
     *
     * @return
     */
    public List<Set_Item> getHotLoan() {
        List<Set_Item> lists = new ArrayList<>();
        int[] icons = {R.mipmap.icon_peace
                , R.mipmap.icon_peace
                , R.mipmap.icon_peace};
        String[] data = context.getResources().getStringArray(R.array.loan_item);
        for (int i = 0; i < icons.length; i++) {
            lists.add(new Set_Item(icons[i], "现金白卡-快速贷", data[i]));
        }

        return lists;
    }

    public List<Set_Item> getTools() {
        ArrayList<Set_Item> tools = new ArrayList<>();
        int[] icons = {R.mipmap.icon_strategy,
                R.mipmap.icon_learningpianner, R.mipmap.icon_netcreditsearch};
        String[] titles = context.getResources().getStringArray(R.array.loan_tools);
        for (int i = 0; i < titles.length; i++) {
            Set_Item item = new Set_Item(icons[i], titles[i]);
            tools.add(item);
        }
        return tools;
    }

    public List<Set_Item> getGVLoan() {
        ArrayList<Set_Item> tools = new ArrayList<>();
        int[] icons = {R.mipmap.icon_creditcardloanst,
                R.mipmap.icon_thebillisborrowed,
                R.mipmap.icon_creditcardalso,
                R.mipmap.icon_sesamepointsborrow,
                R.mipmap.icon_workingtoborrow,
                R.mipmap.icon_idcardtoborrow,
                R.mipmap.icon_college,
                R.mipmap.icon_consumercredit,
                R.mipmap.icon_wechatqq,
                R.mipmap.icon_allcategories};
        String[] titles = context.getResources().getStringArray(R.array.loan_grid);

        for (int i = 0; i < 10; i++) {
            Set_Item item = new Set_Item(icons[i], titles[i]);

            tools.add(item);
        }

        return tools;
    }
}
