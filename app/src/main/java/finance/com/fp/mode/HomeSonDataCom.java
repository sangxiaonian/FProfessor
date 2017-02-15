package finance.com.fp.mode;

import finance.com.fp.mode.datafractory.CardDataFractory;
import finance.com.fp.mode.datafractory.HomeDataFractory;
import finance.com.fp.mode.datafractory.ImprotFactory;
import finance.com.fp.mode.inter.HomeSonDataInter;
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
    public Observable getData(int activity_id, int item_id) {
        Observable observable =null;
        switch (activity_id){
            case 0:
                observable= fractory.creatObservable(item_id);
                break;
            case 1:
                observable=dataFractory.creatObservable(item_id);
                break;
            case 2:
                observable = improtFactory.creatObservable(item_id);
                break;

            default:
                break;

        }

        return observable;
    }


}
