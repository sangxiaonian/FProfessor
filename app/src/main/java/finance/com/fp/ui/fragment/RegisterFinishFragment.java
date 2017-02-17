package finance.com.fp.ui.fragment;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import finance.com.fp.BasisFragment;
import finance.com.fp.R;
import finance.com.fp.ui.inter.FragmentListener;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/2/15 11:01
 */
public class RegisterFinishFragment extends BasisFragment implements View.OnClickListener {

    private Button bt_next;
    private FragmentListener listener;


    @Override
    public View initViews(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_finish, null);
        bt_next = (Button) view.findViewById(R.id.bt_login);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        bt_next.setOnClickListener(this);
        initToolBar();
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onNextClick();
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (FragmentListener) context;
    }


    public void initToolBar() {
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onBackClikc();
                }
            });

        }
    }



}
