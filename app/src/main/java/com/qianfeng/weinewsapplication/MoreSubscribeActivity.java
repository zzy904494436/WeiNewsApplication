package com.qianfeng.weinewsapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qianfeng.adapter.DiscoverGvAdapter;
import com.qianfeng.adapter.SubscribeGvAdapter;
import com.qianfeng.fragment.DiscoverFragment;
import com.qianfeng.myapplication.MyApplication;
import com.qianfeng.util.GvData1;
import com.qianfeng.util.GvData2;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class MoreSubscribeActivity extends AppCompatActivity {

    private GvData2 data;
    private SubscribeGvAdapter gvAdapter;
    private List<GvData2.DataBean.ListBean> listCommonBean;
    private String urlGridView = "http://app.lerays.com/api/subscription/list";
    private String urlGridViewItem = "http://app.lerays.com/api/stream/tag/slist?nextsign=null&pubtime=null&tag_id=";
    private MyApplication myApplication;


    @ViewInject(R.id.moreSubscribe_GridView)GridView moreSubscribe_GridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_subscribe);

        listCommonBean = new ArrayList<>();

        x.view().inject(this);
        myApplication = (MyApplication) getApplication();
        //
        urlUtilGV();

    }

    public void urlUtilGV(){
        RequestParams requestParams = new RequestParams(urlGridView);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("www","-==-="+result);
                Gson gson1 = new Gson();
                data = gson1.fromJson(result,GvData2.class);
                listCommonBean = data.getData().getList();
                if (myApplication.isLogined){
                    gvAdapter = new SubscribeGvAdapter(listCommonBean,MoreSubscribeActivity.this,1);
                }else {
                    gvAdapter = new SubscribeGvAdapter(listCommonBean,MoreSubscribeActivity.this,2);
                }
                moreSubscribe_GridView.setAdapter(gvAdapter);
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
    public void gridViewClick(){
        moreSubscribe_GridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String gridViewItemId = listCommonBean.get(position).getId();
                Intent intent = new Intent(MoreSubscribeActivity.this,AddListViewActivity.class);
                intent.putExtra("id",gridViewItemId);
                gridViewItemId = urlGridViewItem+gridViewItemId;
                intent.putExtra("url",gridViewItemId);
                intent.putExtra("title",listCommonBean.get(position).getTitle());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (myApplication.isLogined){
            gvAdapter = new SubscribeGvAdapter(listCommonBean,MoreSubscribeActivity.this,1);
        }else {
            gvAdapter = new SubscribeGvAdapter(listCommonBean,MoreSubscribeActivity.this,2);
        }
        moreSubscribe_GridView.setAdapter(gvAdapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("flag",1);
        startActivity(intent);

        Toast.makeText(myApplication, "返回", Toast.LENGTH_SHORT).show();
        //this.finish();
        return super.onKeyDown(keyCode, event);
    }
}
