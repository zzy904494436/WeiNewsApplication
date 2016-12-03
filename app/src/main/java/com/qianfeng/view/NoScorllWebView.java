package com.qianfeng.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by Administrator on 2016/11/11.
 */

public class NoScorllWebView extends WebView {
    public NoScorllWebView(Context context) {
        super(context);
    }

    public NoScorllWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int height = MeasureSpec.getMode(heightMeasureSpec);

        super.onMeasure(widthMeasureSpec, height);
    }
}
