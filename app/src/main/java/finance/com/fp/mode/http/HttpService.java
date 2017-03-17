package finance.com.fp.mode.http;


import java.util.Map;

import finance.com.fp.mode.bean.FinanceBean;
import finance.com.fp.mode.bean.FriendBean;
import finance.com.fp.mode.bean.HttpBean;
import finance.com.fp.mode.bean.IDBean;
import finance.com.fp.mode.bean.LoanSearchBean;
import finance.com.fp.mode.bean.MsgContentBean;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

public interface HttpService {


    @FormUrlEncoded
    @POST("index.php?m=content&c=doserver&a=get_app_content")
    Observable<HttpBean<FinanceBean>> getReslut(@Field("page") String page, @Field("strip") String strip);


    /**
     * 消息中心
     *
     * @param page  页数
     * @param strip 条目
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?m=content&c=doserver&a=get_news_fb")
    Observable<HttpBean<MsgContentBean>> getMessageContent(@Field("page") String page, @Field("strip") String strip);

    /**
     * 获取轮播图片
     *
     * @param id Id说明：首页轮播 id=29，办卡轮播 id=30，网贷轮播id=31， 提额轮播 id=32
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?m=content&c=doserver&a=get_app_img")
    Observable<HttpBean<FinanceBean>> getCarousel(@Field("id") String id);


    /**
     * 获最信息
     *
     * @param id    Id说明：     金融口子id=10，办卡攻略 id=20，网贷攻略id=21， 提额攻略 id=33
     *              精养卡 id=34、捞偏门id=22
     * @param page  页数
     * @param strip 条目
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?m=content&c=doserver&a=get_app_content")
    Observable<HttpBean<FinanceBean>> getContent(@Field("id") String id, @Field("page") String page, @Field("strip") String strip);

    /**
     * 本期力荐卡
     *
     * @return
     */
    @GET("index.php?m=content&c=doserver&a=get_recommend")
    Observable<HttpBean<FinanceBean>> getRecommend();

    /**
     * 学习规划师
     *
     * @return
     * @param msg
     */
    @FormUrlEncoded
    @POST("index.php?m=content&c=doserver&a=get_study")
    Observable<HttpBean<FinanceBean>> getPlanner(@Field("msg")String msg,@Field("username") String phone);

    /**
     * 获最搜索信息
     *
     * @param id
     * @param page  页数
     * @param strip 条目
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?m=content&c=doserver&a=get_loan")
    Observable<HttpBean<LoanSearchBean>> getLoanSearch(@Field("id") int id, @Field("page") int page, @Field("strip") int strip);


    /**
     * 获取验证码
     *
     * @return
     */
    @FormUrlEncoded
    @POST("rest")
    Observable<String> getDynamic(@FieldMap Map<String, String> params);


    /**
     * 注册
     *
     * @param username 用户名
     * @param member   注册码
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?m=content&c=doserver&a=app_member_zc")
    Observable<String> register(@Field("username") String username, @Field("member") String member);

    /**
     * 设置密码
     *
     * @param username 用户名
     * @param member   注册码
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?m=content&c=doserver&a=app_member_pass")
    Observable<String> setPassWord(@Field("username") String username, @Field("pass") String member);
    /**
     * 设置密码
     *
     * @param username 用户名
     * @param member   注册码
     * @param dynamic 是否是动态登陆
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?m=content&c=doserver&a=app_member_login")
    Observable<String> login(@Field("username") String username, @Field("pass") String member, @Field("dynamic")boolean dynamic);

    /**
     * 办卡攻略
     * @param id id
     * @param page 页数
     * @param strip 条目
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?m=content&c=doserver&a=get_app_content_bk")
    Observable<HttpBean<FinanceBean>> getCardStrategy(@Field("id") int id, @Field("page") int page, @Field("strip") int strip);

 /**
     * 办卡攻略

     * @param page 页数
     * @param strip 条目
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?m=content&c=doserver&a=get_app_content_bk")
    Observable<HttpBean<FinanceBean>> getJingCard( @Field("page") String page, @Field("strip") String strip);

    /**
     * 快速微贷和热身排行榜
     * @param id
     * @param page
     * @param strip
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?m=content&c=doserver&a=get_app_fast")
    Observable<HttpBean<FinanceBean>> getHotApply(@Field("id") int id, @Field("page") int page, @Field("strip") int strip);

    /**
     * 快速微贷和热身排行榜
     * @param page
     * @param strip
     * @return
     */
    @FormUrlEncoded
    @POST("index.php?m=content&c=doserver&a=get_people")
    Observable<HttpBean<FriendBean>> getFriend(@Field("page") int page, @Field("strip") int strip);


    @FormUrlEncoded
    @POST("index.php?m=content&c=doserver&a=opinion")
    Observable<String> submit(@Field("opinion") String sp, @Field("username")String phone);


    @FormUrlEncoded
    @POST("index.php?m=content&c=doserver&a=app_member_add")
    Observable<String> subPerson(@FieldMap Map<String,String> params); @FormUrlEncoded

    /**
     * 个人资料
     */
    @POST("index.php?m=content&c=doserver&a=app_member_select")
    Observable<IDBean> getPerson(@Field("username") String username);

     /**
     * 个人资料
     */
    @POST("index.php?m=content&c=doserver&a=bank")
    Observable<HttpBean<FinanceBean>>  getAllBalance();


}
