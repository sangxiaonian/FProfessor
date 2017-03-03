package finance.com.fp.mode.inter;

import java.util.List;

import finance.com.fp.mode.bean.Set_Item;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/20 14:19
 */
public interface LoanDataInter {

    List<Set_Item>  getTools();

    List<Set_Item>  getGVLoan();
    List<Set_Item>  getJi();
}
