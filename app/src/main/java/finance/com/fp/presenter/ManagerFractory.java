package finance.com.fp.presenter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import finance.com.fp.R;
import finance.com.fp.mode.datafractory.CardDataFractory;
import finance.com.fp.mode.datafractory.HomeDataFractory;
import finance.com.fp.mode.datafractory.ImprotFactory;
import finance.com.fp.utlis.DividerGridItemDecoration;
import finance.com.fp.utlis.RecycleViewDivider;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/1/22 10:18
 */
public class ManagerFractory {
    public static ManagerFractory fractory;

    public ManagerFractory() {
    }

    ;

    public static ManagerFractory getFractory() {
        if (fractory == null) {
            synchronized (ManagerFractory.class) {
                if (fractory == null) {
                    fractory = new ManagerFractory();
                }
            }
        }
        return fractory;
    }


    /**
     * 获取分割线
     * @param context
     * @param activityID
     * @param item_id
     * @return
     */
    public RecyclerView.ItemDecoration getDivider(Context context, int activityID, int item_id) {
        RecyclerView.ItemDecoration itemDecoration = null;
        switch (activityID) {
            case 0://homeFragmen
                itemDecoration = getHomeDivider(context, item_id);
                break;
            case 1:
                itemDecoration = getCardDivider(context, item_id);
                break;
            case 2:
                itemDecoration = getImportDivider(context, item_id);
                break;
            default:
                itemDecoration = new DividerGridItemDecoration(context,R.drawable.divider_line);
                break;
        }
        return itemDecoration;
    }

    private RecyclerView.ItemDecoration getImportDivider(Context context, int item_id) {
        RecyclerView.ItemDecoration itemDecoration = null;
        switch (item_id) {
            case ImprotFactory.LOAN_STRAGE://提额攻略
            case ImprotFactory.LOAN_ONE_KEY_IPMORT://一键提额
            case ImprotFactory.LOAN_JING://一键提额
                itemDecoration = new RecycleViewDivider(context, LinearLayoutManager.VERTICAL,R.drawable.divider_line);
                break;

        }
        return itemDecoration;
    }

    private RecyclerView.ItemDecoration getCardDivider(Context context, int item_id) {
        RecyclerView.ItemDecoration itemDecoration = null;
        switch (item_id) {
            case CardDataFractory.HOT_APPLY://热审排行榜
                itemDecoration = new RecycleViewDivider(context, LinearLayoutManager.VERTICAL,R.drawable.divider_line);
                break;
            case CardDataFractory.ALL_BALANCE://全部银行
                itemDecoration = new DividerGridItemDecoration(context,R.drawable.divider_line_height);
                break;
            case CardDataFractory.APPLY_PROGRESS://盛情进度
                itemDecoration = new RecycleViewDivider(context, LinearLayoutManager.VERTICAL,R.drawable.divider_line);
                break;

        }
        return itemDecoration;
    }


    /**
     *  获取页面的分割线
     * @param context
     * @param activityID
     * @param item_id
     * @return
     */
    public RecyclerView.LayoutManager getLayoutManager(Context context, int activityID, int item_id) {
        RecyclerView.LayoutManager manager ;
        switch (activityID) {
            case 0:
                manager = getHomeFragmet(context, item_id);
                break;
            case 1:
                manager = getCardManager(context, item_id);
                break;
            case 2:
                manager=getImportManager(context,item_id);
                break;
            default:
                manager = new LinearLayoutManager(context);
                ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
                break;

        }
        return manager;
    }

    private RecyclerView.LayoutManager getImportManager(Context context, int item_id) {
        RecyclerView.LayoutManager manager = null;

        switch (item_id) {
            case ImprotFactory.LOAN_STRAGE://提额攻略
            case ImprotFactory.LOAN_ONE_KEY_IPMORT://一键提额
            case ImprotFactory.LOAN_JING://一键提额
                manager = new LinearLayoutManager(context);
                ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
                break;
        }
        return manager;

    }


    private RecyclerView.LayoutManager getCardManager(Context context, int item_id) {
        RecyclerView.LayoutManager manager = null;

        switch (item_id) {
            case     CardDataFractory.HOT_APPLY://热审排行榜
                manager = new LinearLayoutManager(context);
                ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
                break;
            case  CardDataFractory.ALL_BALANCE:
                manager = new GridLayoutManager(context, 3);
                ((GridLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
                break;
            case     CardDataFractory.APPLY_PROGRESS://进度查询
                manager = new LinearLayoutManager(context);
                ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
                break;


        }
        return manager;
    }


    private RecyclerView.LayoutManager getHomeFragmet(Context context, int item_id) {
        RecyclerView.LayoutManager manager = null;

        switch (item_id) {
            case   HomeDataFractory.BALANCE_CALL:
            case HomeDataFractory.CREDIT:
            case HomeDataFractory.MSG_CENTER:
                manager = new LinearLayoutManager(context);
                ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
                break;
            case HomeDataFractory.UTILITY_TOLL:
                manager = new GridLayoutManager(context, 3);
                ((GridLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
//                manager = new LinearLayoutManager(context);
//                ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
                break;
        }
        return manager;
    }


    private RecyclerView.ItemDecoration getHomeDivider(Context context, int item_id) {
        RecyclerView.ItemDecoration itemDecoration = null;
        switch (item_id) {
            case 1://银行电话
            case 2://征信查询
                itemDecoration = new RecycleViewDivider(context, LinearLayoutManager.VERTICAL,R.drawable.divider_line);
                break;
            case 3://使用工具
                itemDecoration = new DividerGridItemDecoration(context,R.drawable.divider_line);
                break;
            case 4://消息中心
                itemDecoration = new RecycleViewDivider(context, LinearLayoutManager.VERTICAL, R.drawable.divider_line_height);
                break;

        }
        return itemDecoration;
    }


}
