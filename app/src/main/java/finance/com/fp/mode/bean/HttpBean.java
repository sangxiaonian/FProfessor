package finance.com.fp.mode.bean;

import java.util.List;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/2/15 11:43
 */
public class HttpBean<T> {

    private List<T> title;

    public List<T> getTitle() {
        return title;
    }

    public void setTitle(List<T> title) {
        this.title = title;
    }


}
