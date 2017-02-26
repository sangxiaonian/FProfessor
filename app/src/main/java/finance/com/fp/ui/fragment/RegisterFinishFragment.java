package finance.com.fp.ui.fragment;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import finance.com.fp.BasisFragment;
import finance.com.fp.R;
import finance.com.fp.ui.inter.FragmentListener;
import finance.com.fp.utlis.Utils;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/2/15 11:01
 */
public class RegisterFinishFragment extends BasisFragment implements View.OnClickListener {

    private Button bt_next;
    private FragmentListener listener;
    private TextView tv_content;


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
        tv_content = (TextView) rootView.findViewById(R.id.tv_content);
        if (listener.isRegister()){
            initToolBar(null);
        }else {
            tv_content.setText(getString(R.string.psd_change_succ));
            initToolBar(Utils.getResStr(R.string.psd_change));
        }

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


    public void initToolBar(String title) {
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onBackClikc();
                }
            });

        }

        TextView tv_title = (TextView) rootView.findViewById(R.id.title);
        if (title!=null){
            tv_title.setText(title);
        }
    }



}
