package finance.com.fp.mode.inter;

import java.util.List;

import finance.com.fp.mode.bean.Set_Item;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/19 17:36
 */
public interface ImportDataInter {


    List<Set_Item> getImport();

    List<Set_Item> getBalances();
}
