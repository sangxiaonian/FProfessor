package finance.com.fp.mode.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Description：界面见传递的数据
 *
 * @Author：桑小年
 * @Data：2017/1/11 11:50
 */
public class TranInfor<T> implements Parcelable {


    /**
     * 获取数据的页面Id
     * 0:homeFragment 1.card 办卡 2.提额专区
     */
    public int activity_id;

    /**
     * 获取数据的页面Id
     * 0:页面条目ID tools
     */
    public int item_id;

    public int type;

    /**
     * 页面名称
     */
    public String title = "标题";
    /**
     *
     */
    public String describe = "详细信息";

    /**
     * 副描述
     */
    public String describe_sub;


    public List<T> datas;
    /**
     * 更新时间
     */

    public String updatetime;
    /**
     * 文章内容
     */
    public String content;
    public String img_url;

    public TranInfor() {
    }


    protected TranInfor(Parcel in) {
        activity_id = in.readInt();
        item_id = in.readInt();
        type = in.readInt();
        title = in.readString();
        describe = in.readString();
        describe_sub = in.readString();
        updatetime = in.readString();
        content = in.readString();
        img_url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(activity_id);
        dest.writeInt(item_id);
        dest.writeInt(type);
        dest.writeString(title);
        dest.writeString(describe);
        dest.writeString(describe_sub);
        dest.writeString(updatetime);
        dest.writeString(content);
        dest.writeString(img_url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TranInfor> CREATOR = new Creator<TranInfor>() {
        @Override
        public TranInfor createFromParcel(Parcel in) {
            return new TranInfor(in);
        }

        @Override
        public TranInfor[] newArray(int size) {
            return new TranInfor[size];
        }
    };
}
