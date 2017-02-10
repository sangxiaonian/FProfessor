package finance.com.fp.ui.inter;

import java.util.List;

import em.sang.com.allrecycleview.listener.OnToolsItemClickListener;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/2/9 14:55
 */
public interface IDSonView extends OnToolsItemClickListener {
    @Override
    void onItemClick(int position, Object item);

    void initTitle(String title);

    void showSelectDialog(List<String> isHas);

    void showPickerDialog(List<String> education);

    void showEditDialog(String title);
}
