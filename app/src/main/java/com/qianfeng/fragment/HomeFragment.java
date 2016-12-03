package com.qianfeng.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.qianfeng.adapter.CommonAdapter;
import com.qianfeng.adapter.CommonAdapter2;
import com.qianfeng.util.DataUtil;
import com.qianfeng.weinewsapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
@ContentView(R.layout.fragment_home)
public class HomeFragment extends Fragment {

    private boolean injected = false;
    private String urlTopTab = "http://app.lerays.com/api/stream/category/navi";
    private List<Map<String,Object>> listTitles;
    private List<Fragment> listFragments;
    private CommonFragment commonFragment;
    private CommonAdapter commonAdapter;
    private List<String> listUrl;
    private AlertDialog alertDialog;

    @ViewInject(R.id.viewPagerInCommonFragment)ViewPager viewPager;
    @ViewInject(R.id.tabForTitle)TabLayout tabForTitle;
    @ViewInject(R.id.tabForIcon)TabLayout tabForIcon;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        injected = true;

        return x.view().inject(this,inflater,container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!injected){
            x.view().inject(this,this.getView());
        }
        //
        alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle("正在加载")
                .setIcon(R.mipmap.happy_icon)
                .setNegativeButton("放弃抵抗，关闭程序", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                })
                .create();
        alertDialog.show();
        //
        listTitles = new ArrayList<>();
        listFragments = new ArrayList<>();
        listUrl = new ArrayList<>();
        urlUtil();

    }
    /*获取所有title*/
    public void urlUtil(){
        RequestParams requestParams = new RequestParams(urlTopTab);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                alertDialog.dismiss();
                try {
                    JSONObject obj1 = new JSONObject(result);
                    JSONArray arr1 = obj1.optJSONArray("data");
                    for (int i = 0;i < arr1.length();i ++){
                        JSONObject obj2 = arr1.optJSONObject(i);
                        Map<String,Object> map = new HashMap<String, Object>();
                        String title = obj2.optString("title");
                        int id = (int) obj2.opt("Id");
                        map.put("title",title);
                        map.put("id",id);
                        listTitles.add(map);
                    }
                    Log.i("listTitles :","得到的结果为："+listTitles.toString());
                    setBinding();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }
    /*绑定操作 + 萌萌的小图片*/
    public void setBinding(){
        //
        Log.i("===========",""+listTitles.size()+"___"+listFragments.size());
        listTitles.remove(0);
        for (int i = 0;i < listTitles.size();i ++){
            Map<String,Object> map = listTitles.get(i);
            commonFragment = new CommonFragment();
            Bundle bundle = new Bundle();
            if (Integer.parseInt(map.get("id").toString())==0){
                bundle.putString("url",DataUtil.URL_1);
                bundle.putString("id",0+"");
            }else {
                String id = map.get("id").toString();
                String urlFragment = DataUtil.URL_2_BEGIN+id+DataUtil.URL_2_END;
                bundle.putString("url",urlFragment);
                bundle.putString("id",id);
            }
            commonFragment.setArguments(bundle);
            listFragments.add(commonFragment);
        }
        Log.i("===========",""+listTitles.size()+"___"+listFragments.size());
        //以下没卵用
        for (int i = 0; i < listFragments.size(); i++) {
            TabLayout.Tab tab=tabForIcon.newTab();
            View view= LayoutInflater.from(getContext()).inflate(R.layout.empty_layout,null);
            // LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
            tab.setCustomView(view);
            tabForIcon.addTab(tab);
        }
        tabForIcon.setupWithViewPager(viewPager);

        //
        tabForTitle.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabForIcon.setTabMode(TabLayout.MODE_SCROLLABLE);
        Log.i("===========",""+listTitles.size()+"___"+listFragments.size());
        commonAdapter = new CommonAdapter(getChildFragmentManager(),listTitles,listFragments);
        viewPager.setAdapter(commonAdapter);
        tabForTitle.setupWithViewPager(viewPager);
        //
        tabForIcon.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.setIcon(R.mipmap.top_tab_bottom);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setIcon(null);
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabForIcon.getTabAt(0).setIcon(R.mipmap.top_tab_bottom);
    }


}
