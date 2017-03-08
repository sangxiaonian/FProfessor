package finance.com.fp.mode.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/2/22 16:39
 */
public class LoanSearchBean implements Parcelable {

    private String id;
    private String title;
    private String description;
    private String updatetime;
    private String money;
    private String fenqi;
    private String tiaojian;
    private String suoxu;
    private String zhushi;
    private String sq_url;
    private String ljsq_url;
    private String thumb;
    private String zhaiyao;

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

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getFenqi() {
        return fenqi;
    }

    public void setFenqi(String fenqi) {
        this.fenqi = fenqi;
    }

    public String getTiaojian() {
        return tiaojian;
    }

    public void setTiaojian(String tiaojian) {
        this.tiaojian = tiaojian;
    }

    public String getSuoxu() {
        return suoxu;
    }

    public void setSuoxu(String suoxu) {
        this.suoxu = suoxu;
    }

    public String getZhushi() {
        return zhushi;
    }

    public void setZhushi(String zhushi) {
        this.zhushi = zhushi;
    }

    public String getSq_url() {
        return sq_url;
    }

    public void setSq_url(String sq_url) {
        this.sq_url = sq_url;
    }

    public String getLjsq_url() {
        return ljsq_url;
    }

    public void setLjsq_url(String ljsq_url) {
        this.ljsq_url = ljsq_url;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getZhaiyao() {
        return zhaiyao;
    }

    public void setZhaiyao(String zhaiyao) {
        this.zhaiyao = zhaiyao;
    }

    @Override
    public String toString() {
        return "LoanSearchBean{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", updatetime='" + updatetime + '\'' +
                ", money='" + money + '\'' +
                ", fenqi='" + fenqi + '\'' +
                ", tiaojian='" + tiaojian + '\'' +
                ", suoxu='" + suoxu + '\'' +
                ", zhushi='" + zhushi + '\'' +
                ", sq_url='" + sq_url + '\'' +
                ", ljsq_url='" + ljsq_url + '\'' +
                ", thumb='" + thumb + '\'' +
                ", zhaiyao='" + zhaiyao + '\'' +
                '}';
    }
public LoanSearchBean(){};
    protected LoanSearchBean(Parcel in) {
        id = in.readString();
        title = in.readString();
        description = in.readString();
        updatetime = in.readString();
        money = in.readString();
        fenqi = in.readString();
        tiaojian = in.readString();
        suoxu = in.readString();
        zhushi = in.readString();
        sq_url = in.readString();
        ljsq_url = in.readString();
        thumb = in.readString();
        zhaiyao = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(updatetime);
        dest.writeString(money);
        dest.writeString(fenqi);
        dest.writeString(tiaojian);
        dest.writeString(suoxu);
        dest.writeString(zhushi);
        dest.writeString(sq_url);
        dest.writeString(ljsq_url);
        dest.writeString(thumb);
        dest.writeString(zhaiyao);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LoanSearchBean> CREATOR = new Creator<LoanSearchBean>() {
        @Override
        public LoanSearchBean createFromParcel(Parcel in) {
            return new LoanSearchBean(in);
        }

        @Override
        public LoanSearchBean[] newArray(int size) {
            return new LoanSearchBean[size];
        }
    };
}
