package finance.com.fp.mode.bean;

/**
 * Description：金融口子
 *
 * @Author：桑小年
 * @Data：2017/2/10 14:29
 */
public class FinanceBean {


    /**
     * id : 7
     * title : 分期嗷嗷叫
     * thumb : http://localhost/phpcms/uploadfile/2017/0117/20170117041613242.png
     * description : 嗷嗷叫的大分期
     * updatetime : 1484644058
     * content : 分期就是66666666666666666666<br />
     害怕人<br />
     666<br />
     <a href="http://www.baidu.com">www.baidu.com</a><br />

     */

    private String id;
    private String title;
    private String thumb;
    private String description;
    private String updatetime;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "FinanceBean{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", thumb='" + thumb + '\'' +
                ", description='" + description + '\'' +
                ", updatetime='" + updatetime + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
