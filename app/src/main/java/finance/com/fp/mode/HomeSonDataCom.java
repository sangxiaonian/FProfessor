package finance.com.fp.mode;

import java.util.ArrayList;
import java.util.List;

import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.datafractory.CardDataFractory;
import finance.com.fp.mode.datafractory.HomeDataFractory;
import finance.com.fp.mode.datafractory.ImprotFactory;
import finance.com.fp.mode.inter.HomeSonDataInter;
import finance.com.fp.ui.inter.HomeSonView;
import rx.Observable;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/20 16:36
 */
public class HomeSonDataCom implements HomeSonDataInter {

    public HomeDataFractory fractory;
    public CardDataFractory dataFractory;
    public ImprotFactory improtFactory;


    public HomeSonDataCom() {
        fractory = HomeDataFractory.getInstance();
        dataFractory = CardDataFractory.getInstance();
        improtFactory = ImprotFactory.getInstance();
    }

    @Override
    public List<Set_Item> getAllBalances() {
        return fractory.getBalanceCall();
    }

    @Override
    public List<Set_Item> getCredit() {
        return fractory.getCredit();
    }

    @Override
    public List<Set_Item> getUtility() {
        return fractory.getUtility();
    }

    @Override
    public List<Set_Item> getDataByID(int activityID, int item_id) {
        List<Set_Item> list = null;
        switch (activityID) {
            case 0://homeFragment
                list = fractory.creatDatas(item_id);
                break;
            case 1:
                list = dataFractory.creatDatas(item_id);
                break;
            case 2:
                list = improtFactory.creatDatas(item_id);
                break;

            default:
                list=new ArrayList<>();
        }
        return list;
    }

    @Override
    public int getItemID(int activityID, int item_id) {
        int id = 0;
        switch (activityID) {
            case 0://homeFragment
                id = fractory.getHomeLayoutID(item_id);
                break;
            case 1://使用工具
                id = dataFractory.getCardLayoutID(item_id);
                break;
            case 2:
                id=improtFactory.getImportID(item_id);
                break;
        }
        return id;
    }

    @Override
    public Observable getData(int activity_id, int item_id, HomeSonView view) {
        Observable observable =null;
        switch (activity_id){
            case 0:
                observable= fractory.creatObservable(item_id);
                break;
            default:
                break;

        }

        return observable;
    }


}
