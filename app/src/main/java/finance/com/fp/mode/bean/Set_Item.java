package finance.com.fp.mode.bean;

/**
 * Description：
 *
 * @Author：桑小年
 * Data：2017/1/3 17:22
 */
public class Set_Item {
    public int icon_id;
    public String title;
    public String   describe;
    public String   describe_sub;
    public int   type;
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
