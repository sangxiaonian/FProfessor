package finance.com.fp.ui.inter;


import em.sang.com.allrecycleview.listener.OnToolsItemClickListener;
import finance.com.fp.mode.bean.Set_Item;

public interface CardView extends OnToolsItemClickListener<Set_Item>{
    /**
     * 热门银行
     * @param item
     */
    void onCardMoreClick(Set_Item item);

    /**
     * 办卡进度查询
     * @param item
     */
    void onQueryProClick(Set_Item item);

    void onClickBanance(int position, Set_Item item, int size);
}
