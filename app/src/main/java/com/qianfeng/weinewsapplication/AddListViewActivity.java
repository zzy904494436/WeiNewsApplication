package com.qianfeng.weinewsapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.qianfeng.adapter.AddListViewAdapter;
import com.qianfeng.util.HotData;
import com.qianfeng.util.SubscribeData;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class AddListViewActivity extends AppCompatActivity {
    @ViewInject(R.id.addTextView)TextView addTextView;
    @ViewInject(R.id.addListView)PullToRefreshListView addListView;
    private String URL_BEGIN = "http://app.lerays.com/stream/view?acm=none-50-151995-";
    private String url1 = "http://app.lerays.com/api/stream/tag/slist?nextsign=";
           // "null&pubtime=null&tag_id=";
    private String url2 = "&pubtime=";
    private String url3 = "&tag_id=";
    private String url = "";

    private SubscribeData hotData;
    private String nextSign = "";
    private String nextTimer = "";
    private int indexListView = 0;
    private String urlpresh;
    private List<SubscribeData.DataBean.ListBean> listBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list_view);

        x.view().inject(this);
        //
        final Intent intent =getIntent();
        url = intent.getStringExtra("url");
        addTextView.setText(intent.getStringExtra("title"));
        //
        listBean = new ArrayList<>();
        //
        if (intent.getStringExtra("id").equals(-1)){
            addListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        }else {
            addListView.setMode(PullToRefreshBase.Mode.BOTH);
        }

        addListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                urlUtil();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                nextSign = hotData.getData().getNextsign();
                nextTimer = hotData.getData().getNexttime()+"";
                Log.i("_(:з」∠)_ 下一页：","=--------"+url);
                indexListView = listBean.size();
                urlpresh = url1+nextSign+url2+nextTimer+url3+intent.getStringExtra("id");
                urlUtilRefresh();
            }
        });
        //

        urlUtil();
    }
    public void urlUtilRefresh(){
        RequestParams requestParams = new RequestParams(urlpresh);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                hotData = gson.fromJson(result, SubscribeData.class);
                listBean.addAll(hotData.getData().getList());
                AddListViewAdapter addListViewAdapter = new AddListViewAdapter(listBean,AddListViewActivity.this);
                addListView.setAdapter(addListViewAdapter);
                clickListview();
                addListView.getRefreshableView().setSelection(indexListView);
                addListView.onRefreshComplete();

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
    public void clickListview(){
        addListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AddListViewActivity.this,WebActivity.class);
                String url_end = listBean.get(position).getAck_code();
                String url_id = listBean.get(position).getStream_id();
                intent.putExtra("title",listBean.get(position).getTitle());
                intent.putExtra("url",URL_BEGIN+url_end+
                        "&stream_id="+url_id+
                        "&_ack="+url_end+
                        "&_ack="+url_end+
                        "&stream_id="+url_id);
                intent.putExtra("pic",listBean.get(position).getImg_src());
                startActivity(intent);
            }
        });
    }
    public void urlUtil(){
        RequestParams requestParams = new RequestParams(url);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                hotData = gson.fromJson(result, SubscribeData.class);
                listBean = hotData.getData().getList();
                AddListViewAdapter addListViewAdapter = new AddListViewAdapter(listBean,AddListViewActivity.this);
                addListView.setAdapter(addListViewAdapter);
                clickListview();
                addListView.onRefreshComplete();
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
