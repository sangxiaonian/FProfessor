package finance.com.fp.mode;

import java.util.List;

import finance.com.fp.mode.bean.LoanSearchBean;
import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.datafractory.HomeDataFractory;
import finance.com.fp.mode.datafractory.HttpFactory;
import finance.com.fp.mode.inter.HomeFragmentData;
import rx.Observable;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/19 15:08
 */
public class HomeFragmentDataCom implements HomeFragmentData {
    private HomeDataFractory fractory;
    public HomeFragmentDataCom(){
        fractory = HomeDataFractory.getInstance();
    }


    @Override
    public List<Set_Item> getTools() {
        return fractory.getTools();
    }

    @Override
    public Observable<Set_Item> getfinancialhole(int page) {

        return HttpFactory.getFinance(String.valueOf(page),"10");
    }

    @Override
    public Observable<Set_Item> getPartialDoor(int page) {
        return  HttpFactory.getPartialDoor(String.valueOf(page),"10");
    }

    @Override
    public Observable<LoanSearchBean> getLoanSearch() {
        return HttpFactory.getLoanSearch(0,0).take(4);
    }


}
