package finance.com.fp.mode.bean;

import finance.com.fp.R;

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
     * 默认图片
     */
    public int placeholder= R.mipmap.loading;

    public int faildId=R.mipmap.load_fail;

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
    public String turl;
    public String bank_url;

    public Set_Item(){

    }

    public Set_Item(int icon_id, String title){
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

    @Override
    public String toString() {
        return "Set_Item{" +
                "icon_id=" + icon_id +
                ", placeholder=" + placeholder +
                ", faildId=" + faildId +
                ", title='" + title + '\'' +
                ", describe='" + describe + '\'' +
                ", describe_sub='" + describe_sub + '\'' +
                ", updatetime='" + updatetime + '\'' +
                ", content='" + content + '\'' +
                ", img_url='" + img_url + '\'' +
                ", type=" + type +
                ", isCheck=" + isCheck +
                ", turl='" + turl + '\'' +
                '}';
    }
}
