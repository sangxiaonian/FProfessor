package finance.com.fp.ui.inter;


import em.sang.com.allrecycleview.listener.OnToolsItemClickListener;
import finance.com.fp.mode.bean.Set_Item;

public interface LoanView extends OnToolsItemClickListener {

    void onListItemClick(int position, Set_Item item);
}
