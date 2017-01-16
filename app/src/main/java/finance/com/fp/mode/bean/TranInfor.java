package finance.com.fp.mode.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Description：界面见传递的数据
 *
 *
 * @Author：桑小年
 * @Data：2017/1/11 11:50
 */
public class TranInfor implements Parcelable{

    /**
     * RecycleView 的manager类型:
     * 0:LinearLayoutManager 1:GridLayoutManager 2:StaggeredGridLayoutManager
     */
    public int manager_type=0;


    /**
     * 如果是Gride或者瀑布流时候的列数
     */
    public int manager_row_num=1;

    /**
     * 滑动方向 1:纵向(默认),0:横向
     */
    public int manager_orientation=1;

    /**
     * 获取数据的页面Id
     * 0:homeFragment
     */
    public int activity_id;

    /**
     * 获取数据的页面Id
     * 0:页面条目ID tools
     */
    public int item_id;
    /**
     * 布局Id
     * 0:homeFragment tools
     */
    public int layoutId;

    /**
     * 页面名称
     */
    public String title="详细信息";



    public  TranInfor() {};


    protected TranInfor(Parcel in) {
        manager_type = in.readInt();
        manager_row_num = in.readInt();
        manager_orientation = in.readInt();
        activity_id = in.readInt();
        item_id = in.readInt();
        layoutId = in.readInt();
        title = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(manager_type);
        dest.writeInt(manager_row_num);
        dest.writeInt(manager_orientation);
        dest.writeInt(activity_id);
        dest.writeInt(item_id);
        dest.writeInt(layoutId);
        dest.writeString(title);
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
