package finance.com.fp.mode.inter;

import java.util.List;

import finance.com.fp.mode.bean.Set_Item;
import finance.com.fp.mode.datafractory.ImprotFactory;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/20 15:19
 */
public class ImportDataComl implements ImportDataInter {

    private ImprotFactory factory;

    public ImportDataComl(){
        factory=ImprotFactory.getInstance();
    }

    @Override
    public List<Set_Item> getImport() {
        return factory.getImport();
    }

    @Override
    public List<Set_Item> getBalances() {
        return factory.getBalances();
    }
}
