package finance.com.fp.mode.bean;

/**
 * Description：消息中心
 *
 * @Author：桑小年
 * @Data：2017/2/21 16:42
 */
public class MsgContentBean {


    /**
     * id : 4
     * title : 消息3344
     * description : 消息3344
     * updatetime : 1487147749
     * content : 消息3344
     */

    public String id;
    public String title;
    public String description;
    public String updatetime;
    public String content;


    @Override
    public String toString() {
        return "MsgContentBean{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", updatetime='" + updatetime + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
