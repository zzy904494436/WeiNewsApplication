package com.qianfeng.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qianfeng.util.GvData1;
import com.qianfeng.util.GvData2;
import com.qianfeng.util.LoginDialog;
import com.qianfeng.util.SqliteManager;
import com.qianfeng.weinewsapplication.R;
import com.squareup.picasso.Picasso;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/10.
 */

public class SubscribeGvAdapter extends BaseAdapter{
    private List<GvData2.DataBean.ListBean> listBeen;
    private Context context;
    private int[] imgs = {R.mipmap.subscribe,R.mipmap.un_subscribe};
    private SqliteManager sqliteManager;
    private boolean[] flags;
    private ViewHolder holder;
    private boolean flag = true;
    private int isLogin;

    public SubscribeGvAdapter(List<GvData2.DataBean.ListBean> listBeen, Context context,int isLogin) {
        this.listBeen = listBeen;
        this.context = context;
        this.isLogin = isLogin;
        //
        flags = new boolean[listBeen.size()];
        sqliteManager = new SqliteManager();

    }

    @Override
    public int getCount() {
        return listBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return listBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.gridview_item,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        GvData2.DataBean.ListBean bean = listBeen.get(position);
        //
        if (isLogin==1){
            beginSelected(position);
        }
        //
        flag = true;
        holder.title.setText(bean.getTitle());
        holder.num.setText(bean.getSubscribers()+"人已阅读");
        Picasso.with(context).load(bean.getImg_src()).into(holder.pic);
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin==1){
                    choiceSelected(position);
                }else {
                        LoginDialog dialog = new LoginDialog();
                        dialog.loginDialog(context);
                        Toast.makeText(context, "还没登陆呢亲", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return convertView;
    }

    public void beginSelected(int index){
        List<GvData2.DataBean.ListBean> listBeen2 = new ArrayList<>();
        listBeen2 = sqliteManager.sqliteQuery2(context);
        for (int i = 0 ;i< listBeen2.size(); i++){
            if (listBeen2.get(i).getTitle().equals(listBeen.get(index).getTitle())){
                //如果数据库里有说明是选中状态
                holder.add.setImageResource(imgs[0]);
                return;
            }else {
                holder.add.setImageResource(imgs[1]);
            }
        }
    }

    public void choiceSelected(int index){
        List<GvData2.DataBean.ListBean> listBeen2 = new ArrayList<>();
        listBeen2 = sqliteManager.sqliteQuery2(context);
        for (int i = 0 ;i< listBeen2.size(); i++){

            if (listBeen2.get(i).getTitle().equals(listBeen.get(index).getTitle())){
                //如果数据库里有说明是选中状态
                Toast.makeText(context, "_(:з」∠)_您已经收藏过了", Toast.LENGTH_SHORT).show();
                /*holder.add.setImageResource(imgs[1]);
                sqliteManager.sqliteDel(context,index);*/
                flag = false;
                //notifyDataSetChanged();
            }
        }
        if (flag){
            holder.add.setImageResource(imgs[0]);
            ContentValues values = new ContentValues();
            values.put("url",listBeen.get(index).getId());
            values.put("urlpic",listBeen.get(index).getImg_src());
            values.put("title",listBeen.get(index).getTitle());
            values.put("num",listBeen.get(index).getSubscribers());
            sqliteManager.sqliteInsert(context,values);
            notifyDataSetChanged();
        }

    }

    private class ViewHolder{
        @ViewInject(R.id.gridview_Title)TextView title;
        @ViewInject(R.id.gridview_Num)TextView num;
        @ViewInject(R.id.gridview_Pic)ImageView pic;
        @ViewInject(R.id.gridview_Add)ImageView add;
        public ViewHolder(View view){
            x.view().inject(this,view);
        }
    }
}
