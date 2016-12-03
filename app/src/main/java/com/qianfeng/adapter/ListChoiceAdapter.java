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

public class ListChoiceAdapter extends BaseAdapter {
    private List<CommonData.DataBean.ListBean> listBeen;
    private Context context;

    public ListChoiceAdapter(List<CommonData.DataBean.ListBean> listBeen, Context context) {
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
    public int getItemViewType(int position) {
        if (position%5==0){
            return 0;
        }else {
            return 1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder1 viewHolder1 = null;
        ViewHolder2 viewHolder2 = null;
        int type = getItemViewType(position);
        if (convertView==null){
            switch (type){
                case 0:
                    convertView = LayoutInflater.from(context).inflate(R.layout.type1_layout,null);
                    viewHolder1 = new ViewHolder1(convertView);
                    convertView.setTag(viewHolder1);
                    break;
                case 1:
                    convertView = LayoutInflater.from(context).inflate(R.layout.type2_layout,null);
                    viewHolder2 = new ViewHolder2(convertView);
                    convertView.setTag(viewHolder2);
                    break;
            }
        }else {
            switch (type){
                case 0:
                    viewHolder1 = (ViewHolder1) convertView.getTag();
                    break;
                case 1:
                    viewHolder2 = (ViewHolder2) convertView.getTag();
                    break;
            }
        }
        CommonData.DataBean.ListBean listBean1 = listBeen.get(position);
        Log.i("-=-=-IMAGE;","IS:"+listBean1.getImg_src());
        switch (type){
            case 0:
                viewHolder1.type1Title.setText(listBean1.getTitle());
                Picasso.with(context).load(listBean1.getImg_src()).into(viewHolder1.type1Pic);
                break;
            case 1:
                viewHolder2.type2Title.setText(listBean1.getTitle());
                viewHolder2.type2Other1.setText(listBean1.getCate_title());
                int num = listBean1.getVisit_num();
                String rNum = "";
                if (num>9999){
                    rNum = num/10000+"万+";
                }
                else {
                    rNum = num+"";
                }
                viewHolder2.type2Other2.setText(rNum.toString());
                Picasso.with(context).load(listBean1.getImg_src()).into(viewHolder2.type2Pic);
                break;
        }
        return convertView;
    }

    private class ViewHolder1{
        @ViewInject(R.id.type1Pic)ImageView type1Pic;
        @ViewInject(R.id.type1Title)TextView type1Title;
        public ViewHolder1(View view){
            x.view().inject(this,view);
        }
    }
    private class ViewHolder2{
        @ViewInject(R.id.type2_Pic)ImageView type2Pic;
        @ViewInject(R.id.type2_title)TextView type2Title;
        @ViewInject(R.id.type2_other1)TextView type2Other1;
        @ViewInject(R.id.type2_other2)TextView type2Other2;
        public ViewHolder2(View view){
            x.view().inject(this,view);
        }
    }
}
