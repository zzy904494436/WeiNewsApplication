package com.qianfeng.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianfeng.util.GvData1;
import com.qianfeng.weinewsapplication.R;
import com.squareup.picasso.Picasso;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/10.
 */

public class SqliteAdapter extends BaseAdapter{
    private List<Map<String,String>> listBeen;
    private Context context;

    public SqliteAdapter(List<Map<String,String>> listBeen, Context context) {
        this.listBeen = listBeen;
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.gridview_item,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        Map<String,String> map = listBeen.get(position);
        holder.title.setText(map.get("title"));
        holder.num.setText(map.get("num")+"人已阅读");
        Picasso.with(context).load(map.get("urlpic")).into(holder.pic);
        return convertView;
    }

    private class ViewHolder{
        @ViewInject(R.id.gridview_Title)TextView title;
        @ViewInject(R.id.gridview_Num)TextView num;
        @ViewInject(R.id.gridview_Pic)ImageView pic;
        public ViewHolder(View view){
            x.view().inject(this,view);
        }
    }
}
