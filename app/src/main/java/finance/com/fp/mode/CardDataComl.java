package finance.com.fp.mode;

import java.util.List;

import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.datafractory.CardDataFractory;
import finance.com.fp.mode.inter.CardDataInter;

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
    public List<Set_Item> getGVbalances() {
        return fractory.getGVbalances();
    }

    @Override
    public List<Set_Item> getCards() {
        return fractory.getCards();
    }
}
