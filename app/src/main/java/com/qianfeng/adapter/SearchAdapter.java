package com.qianfeng.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianfeng.util.SearchData;
import com.qianfeng.weinewsapplication.R;
import com.squareup.picasso.Picasso;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2016/11/14.
 */

public class SearchAdapter extends BaseAdapter {
    private List<SearchData.DataBean.ListBean> list;
    private Context context;

    public SearchAdapter(List<SearchData.DataBean.ListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.type5_layout,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SearchData.DataBean.ListBean listBean = list.get(position);
        viewHolder.type5_title.setText(Html.fromHtml(listBean.getTitle()));
        Picasso.with(context).load(listBean.getImg_src()).into(viewHolder.type5_Pic);
        return convertView;
    }
    private class ViewHolder{
        @ViewInject(R.id.type5_title)TextView type5_title;
        @ViewInject(R.id.type5_Pic)ImageView type5_Pic;
        public ViewHolder(View view){
            x.view().inject(this,view);
        }
    }
}
