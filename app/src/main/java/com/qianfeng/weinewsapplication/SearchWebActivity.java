package com.qianfeng.weinewsapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qianfeng.adapter.SearchAdapter;
import com.qianfeng.fragment.CommonFragment;
import com.qianfeng.util.SearchData;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class SearchWebActivity extends AppCompatActivity {
    @ViewInject(R.id.searchInfo)EditText searchInfo;
    @ViewInject(R.id.searchItem2)RelativeLayout searchItem2;
    @ViewInject(R.id.searchListView)ListView searchListView;
    @ViewInject(R.id.searchChaiQuan)TextView searchChaiQuan;
    @ViewInject(R.id.searchMao)TextView searchMao;
    @ViewInject(R.id.searchMengChong)TextView searchMengChong;


    private SearchData searchData;
    private String urlSearch = "http://app.lerays.com/api/stream/search/es?pageno=1&w=";
    private String URL_BEGIN = "http://app.lerays.com/stream/view?";
    private String url = "";
    private List<SearchData.DataBean.ListBean> listBean;
    private SearchAdapter searchAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_web);

        x.view().inject(this);
        listBean = new ArrayList<>();

        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchWebActivity.this,WebActivity.class);
                String url_end = listBean.get(position).getQuery_string();
                intent.putExtra("url",URL_BEGIN+url_end+url_end);
                intent.putExtra("title",listBean.get(position).getTitle());
                intent.putExtra("pic",listBean.get(position).getImg_src());
                startActivity(intent);
            }
        });


    }
    @Event(R.id.searchChaiQuan)
    private void searchChaiQuan_Click(View view){
        searchInfo.setText("");
        searchInfo.setText(searchChaiQuan.getText().toString());
    }
    @Event(R.id.searchMao)
    private void searchMao_Click(View view){
        searchInfo.setText("");
        searchInfo.setText(searchMao.getText().toString());
    }
    @Event(R.id.searchMengChong)
    private void searchMengChong_Click(View view){
        searchInfo.setText("");
        searchInfo.setText(searchMengChong.getText().toString());

    }
    @Event(R.id.searchGo)
    private void searchGo_Click(View view){
        String info = searchInfo.getText().toString();
        searchItem2.setVisibility(View.GONE);
        searchListView.setVisibility(View.VISIBLE);
        url = urlSearch+info;
        urlSearchUtil();

    }
    public void urlSearchUtil(){
        RequestParams requestParams = new RequestParams(url);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                searchData = gson.fromJson(result,SearchData.class);
                listBean = searchData.getData().getList();
                searchAdapter = new SearchAdapter(listBean,SearchWebActivity.this);
                searchListView.setAdapter(searchAdapter);
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
