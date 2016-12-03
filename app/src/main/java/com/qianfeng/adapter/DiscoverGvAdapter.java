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

import com.qianfeng.util.CommonData;
import com.qianfeng.util.GvData;
import com.qianfeng.util.GvData1;
import com.qianfeng.util.LoginDialog;
import com.qianfeng.util.SqliteManager;
import com.qianfeng.weinewsapplication.R;
import com.squareup.picasso.Picasso;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2016/11/10.
 */

public class DiscoverGvAdapter extends BaseAdapter{
    private List<GvData1.DataBean> listBeen;
    private Context context;
    private SqliteManager sqliteManager;
    private GvData1.DataBean bean;
    private boolean flag;
    private int isCollection;
    private int select = 0;
    private ViewHolder holder;
    private int[] imgs = {R.mipmap.subscribe,R.mipmap.un_subscribe};

    public DiscoverGvAdapter(List<GvData1.DataBean> listBeen, Context context,int isCollection,boolean flag) {
        this.listBeen = listBeen;
        this.context = context;
        this.isCollection = isCollection;
        this.flag =flag;

        sqliteManager = new SqliteManager();
        if (isCollection==2){
            listBeen = sqliteManager.sqliteQuery(context);
        }

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
        //
        if (isCollection==2){
            holder.add.setImageResource(imgs[0]);
        }
        //
        bean = listBeen.get(position);
        holder.title.setText(bean.getTitle());
        holder.num.setText(bean.getSubscribers()+"人已阅读");
        Picasso.with(context).load(bean.getImg_src()).into(holder.pic);
        //

        //
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag){
                    Toast.makeText(context, "_(:з」∠)_sss", Toast.LENGTH_SHORT).show();
                    LoginDialog dialog = new LoginDialog();
                    dialog.loginDialog(context);
                }else {
                    //clickChange(position);
                    changeUI(position);
                    //notifyDataSetChanged();
                }

            }
        });
        return convertView;
    }
    //是否登陆

    //
    public boolean flag2(int index){
        List<GvData1.DataBean> listBeen1 = sqliteManager.sqliteQuery(context);
        for (int i = 0;i < listBeen1.size();i++){
            if (listBeen1.get(i).getTitle().equals(listBeen.get(index).getTitle())){
                return false;
            }
        }
        return true;
    }
    //判断是否收藏
    public void changeUI(int index){

        if (isCollection==2){
            //点击取消收藏
            sqliteManager.sqliteDel(context,
                    Integer.parseInt(listBeen.get(index).getId()));
            //
            select=1;
            holder.add.setImageResource(imgs[select]);
            collectionManager();
        }else if (isCollection==1){
            if (!flag2(index)){
                //点击取消收藏
                sqliteManager.sqliteDel(context,
                        Integer.parseInt(listBeen.get(index).getId()));
                //
                Toast.makeText(context, "_(:з」∠)_", Toast.LENGTH_SHORT).show();
                select=1;
                holder.add.setImageResource(imgs[select]);
                collectionManager();
            }else {

                ContentValues values = new ContentValues();
                values.put("url",listBeen.get(index).getId());
                values.put("urlpic",listBeen.get(index).getImg_src());
                values.put("title",listBeen.get(index).getTitle());
                values.put("num",listBeen.get(index).getSubscribers());
                //点击收藏
                select=0;
                holder.add.setImageResource(imgs[select]);
                sqliteManager.sqliteInsert(context,values);
                collectionManager();
            }
        }else {
            if (flag){
                LoginDialog dialog = new LoginDialog();
                dialog.loginDialog(context);
            }
            Toast.makeText(context, "您还未登录，请先登录", Toast.LENGTH_SHORT).show();
        }
    }
    public void collectionManager(){
        if (sqliteManager.sqliteQuery(context).size()>0){
            listBeen = sqliteManager.sqliteQuery(context);
            isCollection = 2;
            notifyDataSetChanged();
        }else {
            isCollection = 1;
            notifyDataSetChanged();
            Toast.makeText(context, "_(:з」∠)_保留一个", Toast.LENGTH_SHORT).show();
        }
        notifyDataSetChanged();
    }
    //

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
