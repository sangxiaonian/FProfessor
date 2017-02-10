package finance.com.fp.mode.bean;

/**
 * Description：
 *
 * @Author：桑小年
 * Data：2017/1/3 17:22
 */
public class Set_Item {
    /**
     * 图片ID
     */
    public int icon_id;
    /**
     * 标题
     */
    public String title;
    /**
     * 描述
     */
    public String   describe;
    /**
     * 副描述
     */
    public String   describe_sub;
    /**
     * 更新时间
     */
    public String updatetime;
    /**
     * 文章内容
     */
    public String content;

    public String img_url;

    public int   type;
    public boolean isCheck;
    public Set_Item(){

    }

    public Set_Item(int icon_id,String title){
        this.icon_id=icon_id;
        this.title=title;
    }

    public Set_Item(int icon_id,String title, String describe) {
        this.icon_id = icon_id;
        this.title = title;
        this.describe = describe;
    }
    public Set_Item(int icon_id,String title, String describe, String describe_sub) {
        this.icon_id = icon_id;
        this.title = title;
        this.describe = describe;
        this.describe_sub = describe_sub;
    }


}
