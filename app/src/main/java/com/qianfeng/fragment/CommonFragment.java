package com.qianfeng.fragment;


import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.qianfeng.adapter.ListChoiceAdapter;
import com.qianfeng.adapter.ListChoiceAdapter2;
import com.qianfeng.util.CommonData;
import com.qianfeng.weinewsapplication.MoreSubscribeActivity;
import com.qianfeng.weinewsapplication.R;
import com.qianfeng.weinewsapplication.WebActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */

public class CommonFragment extends Fragment {


    private Bundle bundle;
    private String url = "";
    private CommonData commonData;
    private List<CommonData.DataBean.ListBean> listBean;
    private ListChoiceAdapter adapter;
    private ListChoiceAdapter2 adapter2;
    private PullToRefreshListView listView;
    private String nextSign = "";
    private String nextTimer = "";
    private int indexListView = 0;
    private String URL_BEGIN = "http://app.lerays.com/stream/view?";


    public CommonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_common,container,false);
        listView = (PullToRefreshListView) view.findViewById(R.id.listViewCommon);
        listView.setMode(PullToRefreshBase.Mode.BOTH);

        //

        //
        listBean = new ArrayList<>();
        getFragmentUrl();
        urlUtil();
        //
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url_end = listBean.get(position-1).getQuery_string();
                Intent intent = new Intent(getContext(), WebActivity.class);

                intent.putExtra("url",URL_BEGIN+url_end+url_end);
                intent.putExtra("title",listBean.get(position-1).getTitle());
                intent.putExtra("pic",listBean.get(position-1).getImg_src());
                startActivity(intent);

            }
        });
        //上下拉刷新
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                urlUtil();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                nextSign = commonData.getData().getNextsign();
                nextTimer = commonData.getData().getNexttime()+"";
                if (bundle.get("id").toString().equals("0")){
                    url = "http://app.lerays.com/api/stream/rec/list?cate_sign="
                            +nextSign
                            +"&pubtime="
                            +nextTimer;
                }else
                {
                    url = "http://app.lerays.com/api/stream/list?cate_sign="
                            +nextSign
                            +"&cate_list="
                            +bundle.get("id")
                            +"&cate_type=cate&pubtime="
                            +nextTimer;
                }
                Log.i("_(:з」∠)_ 下一页：","=--------"+url);
                indexListView = listBean.size();

                urlUtilRefresh();
            }
        });

        return view;
     }

    public void getFragmentUrl(){
        bundle = new Bundle();
        bundle = getArguments();
        url = bundle.getString("url");
    }
    public void urlUtilRefresh(){
        RequestParams requestParams = new RequestParams(url);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                CommonData datafresh = gson.fromJson(result,CommonData.class);
                listBean.addAll(datafresh.getData().getList());
                if (Integer.parseInt(bundle.get("id").toString())==33){
                    adapter2 = new ListChoiceAdapter2(listBean,getActivity());
                    listView.setAdapter(adapter2);
                }else {
                    adapter = new ListChoiceAdapter(listBean,getActivity());
                    listView.setAdapter(adapter);
                }
                listView.getRefreshableView().setSelection(indexListView);
                listView.onRefreshComplete();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
    public void urlUtil(){
        RequestParams requestParams = new RequestParams(url);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                commonData = gson.fromJson(result,CommonData.class);
                listBean = commonData.getData().getList();
               // Log.i("listBean Info :","+++++++++++"+listBean.get(0).getTitle());
                //
                if (Integer.parseInt(bundle.get("id").toString())==33){
                    adapter2 = new ListChoiceAdapter2(listBean,getActivity());
                    listView.setAdapter(adapter2);
                }else {
                    adapter = new ListChoiceAdapter(listBean,getActivity());
                    listView.setAdapter(adapter);
                }
                listView.onRefreshComplete();


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

}
