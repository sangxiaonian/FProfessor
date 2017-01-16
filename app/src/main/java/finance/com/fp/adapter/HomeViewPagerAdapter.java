package finance.com.fp.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/12/27 16:47
 */
public class HomeViewPagerAdapter extends FragmentPagerAdapter {

    private   Context context;
    private   List<Fragment> lists;

    public HomeViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    public HomeViewPagerAdapter(FragmentManager fm,Context context,List<Fragment> list){
        super(fm);
        this.lists=list;
        this.context=context;
    }

    @Override
    public int getCount() {
        return lists.size();
    }


    @Override
    public Fragment getItem(int position) {
        return lists.get(position);
    }


}
