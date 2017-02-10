package finance.com.fp.mode;

import java.util.List;

import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.datafractory.LoanDataFractory;
import finance.com.fp.mode.inter.LoanDataInter;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/20 14:20
 */
public class LoanDataComl implements LoanDataInter {

    LoanDataFractory fractory;

    public LoanDataComl(){
        fractory=LoanDataFractory.getInstance();
    }

    @Override
    public List<Set_Item> getHotLoan() {
        return fractory.getHotLoan();
    }

    @Override
    public List<Set_Item> getTools() {
        return fractory.getTools();
    }

    @Override
    public List<Set_Item> getGVLoan() {
        return fractory.getGVLoan();
    }
}
