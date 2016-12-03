package com.qianfeng.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianfeng.util.CommonData;
import com.qianfeng.weinewsapplication.R;
import com.squareup.picasso.Picasso;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */

public class ListChoiceAdapter2 extends BaseAdapter {
    private List<CommonData.DataBean.ListBean> listBeen;
    private Context context;

    public ListChoiceAdapter2(List<CommonData.DataBean.ListBean> listBeen, Context context) {
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
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return 1;
        }
        else {
            return 0;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder1 viewHolder1 = null;
        ViewHolder2 viewHolder2 = null;
        int type = getItemViewType(position);
        if (convertView == null){
            switch (type){
                case 0:
                    convertView = LayoutInflater.from(context).inflate(R.layout.type3_layout,null);
                    viewHolder1 = new ViewHolder1(convertView);
                    convertView.setTag(viewHolder1);
                    break;
                case 1:
                    convertView = LayoutInflater.from(context).inflate(R.layout.type4_layout,null);
                    viewHolder2 = new ViewHolder2(convertView);
                    convertView.setTag(viewHolder2);
                    break;
            }
        }else {
            switch (type){
                case 0:
                    viewHolder1 = (ViewHolder1)convertView.getTag();
                    break;
                case 1:
                    viewHolder2 = (ViewHolder2) convertView.getTag();
                    break;
            }
        }
        CommonData.DataBean.ListBean bean = listBeen.get(position);
        switch (type){
            case 0:
                viewHolder1.type3Title.setText(bean.getTitle());
               // viewHolder1.type3Others.setText(bean.getVisit_num());
                Picasso.with(context).load(bean.getImg_src()).into(viewHolder1.type3Pic);
                break;
            case 1:
                viewHolder2.type4Title.setText(bean.getTitle());
                //viewHolder2.type4Others.setText(bean.getVisit_num());
                Picasso.with(context).load(bean.getImg_src()).into(viewHolder2.type4Pic);

                break;
        }
        return convertView;
    }

    private class ViewHolder1{
        @ViewInject(R.id.type3_Pic)ImageView type3Pic;
        @ViewInject(R.id.type3_Title)TextView type3Title;
        @ViewInject(R.id.type3_Others)TextView type3Others;

        public ViewHolder1(View view){
            x.view().inject(this,view);
        }
    }
    private class ViewHolder2{
        @ViewInject(R.id.type4_Pic)ImageView type4Pic;
        @ViewInject(R.id.type4_Title)TextView type4Title;
        @ViewInject(R.id.type4_Others)TextView type4Others;

        public ViewHolder2(View view){
            x.view().inject(this,view);
        }
    }

}
