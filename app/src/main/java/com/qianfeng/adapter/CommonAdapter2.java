package com.qianfeng.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/9.
 */

public class CommonAdapter2 extends FragmentPagerAdapter {
    private List<Map<String,Object>> listTitles;
    private List<Fragment> listFragments;
    private List<String> titles;

    public CommonAdapter2(FragmentManager fm, List<Fragment> listFragments) {
        super(fm);
        this.listFragments = listFragments;
    }

    public CommonAdapter2(FragmentManager fm, List<Map<String, Object>> listTitles, List<Fragment> listFragments) {
        super(fm);
        this.listTitles = listTitles;
        this.listFragments = listFragments;
        //
        titles = new ArrayList<>();
        for (int i = 0;i<listTitles.size();i++){
            Map<String,Object> map = listTitles.get(i);
            titles.add(map.get("title").toString());
        }
    }

    public CommonAdapter2(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return listFragments.get(position);
    }

    @Override
    public int getCount() {
        return listFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return null;
    }
}
