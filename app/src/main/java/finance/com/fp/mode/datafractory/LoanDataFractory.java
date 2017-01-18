package finance.com.fp.mode.datafractory;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import finance.com.fp.R;
import finance.com.fp.mode.bean.Set_Item;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Description：办卡功能数据
 *
 * @Author：桑小年
 * @Data：2017/1/11 14:49
 */
public class LoanDataFractory extends BaseFractory {
    public LoanDataFractory(Context context) {
        super(context);
    }

    public static LoanDataFractory fractory;

    public static LoanDataFractory getInstance(Context context) {
        if (fractory == null) {
            synchronized (LoanDataFractory.class) {
                if (fractory == null) {
                    fractory = new LoanDataFractory(context);
                }
            }
        }
        return fractory;
    }

    @Override
    public List<Set_Item> creatDatas(int item_id) {
        List<Set_Item> list = null;

        return list;
    }

    List<Set_Item> lists;

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


}
