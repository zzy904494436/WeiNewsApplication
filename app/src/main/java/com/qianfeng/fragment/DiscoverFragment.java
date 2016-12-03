package com.qianfeng.fragment;


import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qianfeng.adapter.DiscoverGvAdapter;
import com.qianfeng.adapter.GrideViewpagerAdapter;
import com.qianfeng.myapplication.MyApplication;
import com.qianfeng.myinterface.DiscoverFragmentInterFace;
import com.qianfeng.util.CommonData;
import com.qianfeng.util.DBHelper;
import com.qianfeng.util.GvData;
import com.qianfeng.util.GvData1;
import com.qianfeng.util.RankData;
import com.qianfeng.util.SqliteManager;
import com.qianfeng.weinewsapplication.AddListViewActivity;
import com.qianfeng.weinewsapplication.MoreSubscribeActivity;
import com.qianfeng.weinewsapplication.R;
import com.qianfeng.weinewsapplication.SearchWebActivity;
import com.qianfeng.weinewsapplication.WebActivity;
import com.squareup.picasso.Picasso;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverFragment extends Fragment implements ViewPager.OnPageChangeListener,DiscoverFragmentInterFace {


    private ImageView gridview_Add;
    private TextView tvMoreAdd;
    private ViewPager viewPager;
    private GridView gridView;
    private ImageView imageViewSearch;
    private RankData rankData;
    private List<RankData.DataBean> listDataBean;
    private List<RankData.DataBean> listData;
    private List<GvData1.DataBean> listCommonBean;
    private ImageView[] imgs;
    private GvData1 data;
    private DiscoverGvAdapter gvAdapter = new DiscoverGvAdapter(null,getContext(),0,false);

    //数据库操作
    private SqliteManager sqliteManager;
    private int isCollection = 1;
    //
    private MyApplication myApplication;
    //
    private AlertDialog alertDialog;

    //

    private String urlViewpager = "http://app.lerays.com/api/discovery/navi";
    private String urlGridView = "http://app.lerays.com/api/user/subscription/list";
    private String urlGridViewItem = "http://app.lerays.com/api/stream/tag/slist?nextsign=null&pubtime=null&tag_id=";

    private String[] titles = {"本月热点","本日热点","本周热点","本月热点"};
    private String[] urls = {"http://app.lerays.com/api/stream/rank/month"
            ,"http://app.lerays.com/api/stream/rank/day"
            ,"http://app.lerays.com/api/stream/rank/week"
            ,"http://app.lerays.com/api/stream/rank/month"};
    private String URL_BEGIN = "http://app.lerays.com/stream/view?";

    private int vpIndex = 0;
    public Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what==1){

                if (vpIndex<4){
                    vpIndex++;
                }else {
                    vpIndex=1;
                }
                viewPager.setCurrentItem(vpIndex,true);
                cycleViewPager();

            }
            return false;
        }
    });

    public DiscoverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover, container, false);

        myApplication = (MyApplication) getActivity().getApplication();
        gvAdapter.notifyDataSetChanged();
        viewPager = (ViewPager) view.findViewById(R.id.discover_ViewPager);
        gridView = (GridView) view.findViewById(R.id.discover_GridView);
        imageViewSearch = (ImageView) view.findViewById(R.id.discover_Search);
        //more subscribe
        tvMoreAdd = (TextView) view.findViewById(R.id.tvMoreAdd);
        //添加收藏
        gridview_Add = (ImageView) view.findViewById(R.id.gridview_Add);

        //
        listDataBean = new ArrayList<>();
        listCommonBean = new ArrayList<>();
        listData = new ArrayList<>();

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
        //数据库操作_(:з」∠)_
        sqliteManager = new SqliteManager();
        //
        urlUtil();
        //
        insertOrDel();
        //

        imageViewSearch_Click();
        //点击更多
        tvMoreAdd_Click();


        return view;
    }
    public void insertOrDel(){
        if (myApplication.isLogined){
            //已经登录 查询数据库判断是否显示收藏内容
            collectionManager();
        }else {
            //Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
            isCollection = 0;
            urlUtilGV();
        }
    }
    public void collectionManager(){
        if (sqliteManager.sqliteQuery(getContext()).size()>0){
            isCollection = 2;
            listCommonBean = sqliteManager.sqliteQuery(getContext());
            gvAdapter = new DiscoverGvAdapter(listCommonBean,getContext(),isCollection,false);
            gridView.setAdapter(gvAdapter);

            gridViewClick();
        }else {
            isCollection = 1;
            urlUtilGV();
        }
    }

    //点击去看更多subscribe
    public void tvMoreAdd_Click(){
        tvMoreAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToSubscribe = new Intent(getActivity(), MoreSubscribeActivity.class);
                getActivity().startActivityForResult(intentToSubscribe,1);
            }
        });
    }

    //subscribe单项的点击事件
    public void gridViewClick(){
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String gridViewItemId = listCommonBean.get(position).getId();
                Intent intent = new Intent(getActivity(),AddListViewActivity.class);
                intent.putExtra("id",gridViewItemId);
                gridViewItemId = urlGridViewItem+gridViewItemId;
                intent.putExtra("url",gridViewItemId);
                intent.putExtra("title",listCommonBean.get(position).getTitle());
                startActivity(intent);
            }
        });
    }
    //getIntentResult

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        //if (requestCode==2){
            Toast.makeText(getContext(), "_(:з」∠)收到", Toast.LENGTH_SHORT).show();
            listCommonBean = sqliteManager.sqliteQuery(getContext());
            gvAdapter.notifyDataSetChanged();
       // }

    }

    //监听搜索图标点击
    public void imageViewSearch_Click(){
        imageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchWebActivity.class);
                startActivity(intent);
            }
        });
    }

    //控制viewpager广告轮播时间间隔
    public void cycleViewPager(){
        handler.sendEmptyMessageDelayed(1,6000);
    }



    //subscribe gridview的网络操作
    public void urlUtilGV(){
        RequestParams requestParams = new RequestParams(urlGridView);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("www","-==-="+result);
                Gson gson1 = new Gson();
                data = gson1.fromJson(result,GvData1.class);
                listCommonBean = data.getData();
                if (myApplication.isLogined){
                    gvAdapter = new DiscoverGvAdapter(listCommonBean,getContext(),isCollection,false);
                }else {
                    gvAdapter = new DiscoverGvAdapter(listCommonBean,getContext(),isCollection,true);
                }
                gridView.setAdapter(gvAdapter);
                //
                gridViewClick();
                //
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

    @Override
    public void onResume() {
        super.onResume();
        insertOrDel();
    }

    //Viewpager广告网络操作
    public void  urlUtil(){
        RequestParams requestParams = new RequestParams(urlViewpager);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                alertDialog.dismiss();
                Gson gson = new Gson();
                rankData = gson.fromJson(result,RankData.class);
                listData = rankData.getData();
                listDataBean.add(listData.get(listData.size()-1));
                for (int i = 0;i<listData.size();i++){
                    listDataBean.add(listData.get(i));
                }
                listDataBean.add(listData.get(0));
                imgs = new ImageView[listDataBean.size()];
                for (int i = 0 ;i< listDataBean.size();i++){
                    ImageView imageView = new ImageView(getContext());
                    final int finalI = i;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(getContext(), AddListViewActivity.class);
                            intent.putExtra("url",urls[finalI]);
                            intent.putExtra("title",titles[finalI]);
                            intent.putExtra("id",-1+"");
                            startActivity(intent);
                        }
                    });
                    Picasso.with(getContext()).load(listDataBean.get(i).getImg_src()).into(imageView);
                    imgs[i] = imageView;
                }
                GrideViewpagerAdapter adapter = new GrideViewpagerAdapter(imgs);
                viewPager.setAdapter(adapter);
                viewPager.addOnPageChangeListener(DiscoverFragment.this);
                cycleViewPager();
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


    /*用于循环viewpager广告*/
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if ( listDataBean.size() > 1) { //多于1，才会循环跳转
            if ( position < 1) { //首位之前，跳转到末尾（N）
                position = 3;
                viewPager.setCurrentItem(position,true);
            } else if ( position > 3) { //末位之后，跳转到首位（1）
                viewPager.setCurrentItem(1,true); //false:不显示跳转过程的动画
                position = 1;
            }
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    ///////////////////接口回调刷新ui
    @Override
    public void preshFragment() {
        Toast.makeText(myApplication, "接口回调好用否", Toast.LENGTH_SHORT).show();
        gvAdapter.notifyDataSetChanged();listCommonBean = sqliteManager.sqliteQuery(getContext());
        gvAdapter.notifyDataSetChanged();
    }
}
