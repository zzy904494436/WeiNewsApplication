package com.qianfeng.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/11/10.
 */

public class GrideViewpagerAdapter extends PagerAdapter {
    private ImageView[] imgs;

    public GrideViewpagerAdapter(ImageView[] imgs) {
        this.imgs = imgs;
    }

    @Override
    public int getCount() {
        return imgs.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(imgs[position]);
        return imgs[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(imgs[position]);
    }
}
