package finance.com.fp.ui.inter;


import finance.com.fp.mode.bean.Set_Item;

public interface ImportView {
    void onItemClick(Set_Item item, int position);

    /**
     * 一键提额
     */
    void oneKeyImport();

}
