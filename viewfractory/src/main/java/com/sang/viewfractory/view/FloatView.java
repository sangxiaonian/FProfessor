package com.sang.viewfractory.view;


import android.content.Context;
import android.graphics.Color;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sang.viewfractory.R;


/**
 * 折叠view,模仿微信朋友圈,可折叠消息
 */
public class FloatView extends LinearLayout {

    private TextView tv;
    private TextView img;
    private String showContent="全文";
    private String hiden="收起";
    private int color;

    private int lines=4;
    private OnStateChangeListener listener;

    public FloatView(Context context) {
        super(context);
        initView(context, null, 0);
    }

    public FloatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0);
    }

    public FloatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private boolean isExpand;

    public interface OnStateChangeListener{
        void onStateChange(boolean isExpand);
    }

    public void setOnStateChangeListener(OnStateChangeListener listener){
        this.listener=listener;
    }


    private void initView(Context context, AttributeSet attrs, int defStyleAttr){
        setOrientation(VERTICAL);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tv = new TextView(context);
        tv.setLayoutParams(params);
        img=new TextView(context);
        img.setClickable(true);
        img.setText(showContent);
        img.setPadding(0,0,0,0);
        img.setBackground(getResources().getDrawable(R.drawable.select_tv_bg));
        img.setTextColor(Color.parseColor("#abcdef"));
        LayoutParams imgParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        img.setLayoutParams(imgParams);
        addView(tv);
        addView(img);
        img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isExpand){
                    int lineCount= tv.getLineCount();
                    int height = tv.getLineHeight();
                    changeHeight(Math.max(mheight,lineCount*height));
                    img.setText(hiden);
                }else {
                    img.setText(showContent);
                    int height = tv.getLineHeight();
                    changeHeight(height*lines);
                }
                if (listener!=null){
                    listener.onStateChange(isExpand);
                }
                isExpand=!isExpand;
            }
        });


    }


    /**
     * 设置显示内容
     * @param text
     */
    public void setText(String text) {
        tv.setText(text);
        changeTv();
    }

    private float mheight;

    private void changeTv() {
        tv.post(new Runnable() {
            @Override
            public void run() {
                int lineCount= tv.getLineCount();
                int height = tv.getLineHeight();
                FloatView.this.mheight=tv.getHeight();
                if (lineCount>lines){
                    isExpand=false;
                    changeHeight(height*lines);
                    img.setVisibility(VISIBLE);
                }else {
                    isExpand=true;
                    img.setVisibility(GONE);
                }

            }
        });
    }


    private void changeHeight(float h){
        ViewGroup.LayoutParams params = tv.getLayoutParams();
        params.height=(int) h;
        tv.setLayoutParams(params);
    }


    public void setText(Spanned text) {
        tv.setText(text);
        changeTv();
    }
}
