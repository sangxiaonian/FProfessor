package finance.com.fp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/12/27 16:40
 */
public class BasisFragment extends Fragment {
    protected View rootView;
    protected AppCompatActivity activity;
    public Context cnt;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = initViews(inflater, container);
        initData();
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        initListener();
    }

    public void initListener() {
    }

    protected void initData() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.cnt = context;
    }

    public View initViews(LayoutInflater inflater, ViewGroup container) {
        return null;
    }




}
