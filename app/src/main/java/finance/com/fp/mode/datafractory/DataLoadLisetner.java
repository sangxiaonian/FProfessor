package finance.com.fp.mode.datafractory;

import java.util.List;

public interface DataLoadLisetner<T> {

    void loadOver(List<T> lists);
}
