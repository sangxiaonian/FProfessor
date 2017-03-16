package finance.com.fp.mode;

import java.util.List;

import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.datafractory.CardDataFractory;
import finance.com.fp.mode.inter.CardDataInter;
import rx.Observable;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/19 17:37
 */
public class CardDataComl implements CardDataInter{

    private CardDataFractory fractory;

    public CardDataComl(){
        fractory = CardDataFractory.getInstance();
    }

    @Override
    public List<Set_Item> getTools() {
        return fractory.getTools();
    }

    @Override
    public Observable<Set_Item> getGVbalances() {
        return fractory.getGVbalances();
    }


}
