package finance.com.fp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import finance.com.fp.BasisActivity;
import finance.com.fp.R;
import finance.com.fp.mode.http.Config;
import finance.com.fp.utlis.Utils;

public class GuideActivity extends BasisActivity {

    private ViewPager vp;
    private Button bt;
    private List<Integer> datas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        vp= (ViewPager) findViewById(R.id.vp);
        datas=new ArrayList<>();
        datas.add(R.mipmap.vp1);
        datas.add(R.mipmap.vp4);
        datas.add(R.mipmap.vp3);
        datas.add(R.mipmap.vp2);

        vp.setAdapter(new MyAdapter());
        bt= (Button) findViewById(R.id.bt);
        bt.setVisibility(View.GONE);
        vp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position==datas.size()-1){
                    bt.setVisibility(View.VISIBLE);
                }else {
                    bt.setVisibility(View.GONE);
                }
            }
        });

    }

    public void click(View view){
        Utils.setSp( this, Config.isFirst,"true");
        finish();
        startActivity(new Intent(this,MainActivity.class));
    }

    public class MyAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = new ImageView(GuideActivity.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(params);
            Glide.with(GuideActivity.this).load(datas.get(position))
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .crossFade().into(view);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);

        }
    }
}
