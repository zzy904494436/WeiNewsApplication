package com.qianfeng.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianfeng.util.HotData;
import com.qianfeng.util.SubscribeData;
import com.qianfeng.weinewsapplication.R;
import com.squareup.picasso.Picasso;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2016/11/14.
 */

public class AddListViewAdapter extends BaseAdapter {
    private List<SubscribeData.DataBean.ListBean> list;
    private Context context;

    public AddListViewAdapter(List<SubscribeData.DataBean.ListBean> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.type2_layout,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SubscribeData.DataBean.ListBean listBean = list.get(position);
        //viewHolder.type2_other1.setText(listBean.getTitle());
        int num = listBean.getVisit_num();
        String rNum = "";
        if (num>9999){
            rNum = num/10000+"万+";
        }
        else {
            rNum = num+"";
        }
        viewHolder.type2_other2.setText(rNum.toString());
        viewHolder.type2_title.setText(listBean.getTitle());
        Picasso.with(context).load(listBean.getImg_src()).into(viewHolder.type2_Pic);

        return convertView;
    }
    private class ViewHolder{
        @ViewInject(R.id.type2_title)TextView type2_title;
        @ViewInject(R.id.type2_other1)TextView type2_other1;
        @ViewInject(R.id.type2_other2)TextView type2_other2;
        @ViewInject(R.id.type2_Pic)ImageView type2_Pic;
        public ViewHolder(View view){
            x.view().inject(this,view);
        }

    }
}
