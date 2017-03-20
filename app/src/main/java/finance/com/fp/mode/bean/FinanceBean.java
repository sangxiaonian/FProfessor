package finance.com.fp.mode.bean;

/**
 * Description：金融口子
 *
 * @Author：桑小年
 * @Data：2017/2/10 14:29
 */
public class FinanceBean {



    private String id;
    private String title;
    private String thumb;
    private String description;
    private String updatetime;
    private String content;
    private String turl;
    private String f_url;
    private String bank_url;

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    private String reply;


    public String getF_url() {
        return f_url;
    }

    public void setF_url(String f_url) {
        this.f_url = f_url;
    }

    public String getTurl(){
        return turl;
    }

    public void setTurl (String turl){
        this.turl=turl;
    }

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

    public String getBank_url() {
        return bank_url;
    }

    public void setBank_url(String bank_url) {
        this.bank_url = bank_url;
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
                ", turl='" + turl + '\'' +
                ", f_url='" + f_url + '\'' +
                ", bank_url='" + bank_url + '\'' +
                ", reply='" + reply + '\'' +
                '}';
    }
}
