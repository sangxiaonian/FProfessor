package finance.com.fp.ui.inter;


import em.sang.com.allrecycleview.listener.OnToolsItemClickListener;
import finance.com.fp.mode.bean.Set_Item;

public interface CardView extends OnToolsItemClickListener<Set_Item>{
    void onCardMoreClick(Set_Item item);

    void onQueryProClick(Set_Item item);
}
