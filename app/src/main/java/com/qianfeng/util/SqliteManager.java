package com.qianfeng.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/17.
 */

public class SqliteManager {
    //添加
    public void sqliteInsert(Context context, ContentValues values){

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        long result = db.insert("subscribes",null,values);
        if (result>0){
            Toast.makeText(context, "添加收藏成功", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "添加收藏失败", Toast.LENGTH_SHORT).show();
        }
    }
    //查询
    public List<GvData1.DataBean> sqliteQuery(Context context){

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query("subscribes",null,null,null,null,null,null);
        List<GvData1.DataBean> listQuery = new ArrayList<>();
        while (cursor.moveToNext()){
            GvData1.DataBean mapQuery = new GvData1.DataBean();
            mapQuery.setImg_src(cursor.getString(cursor.getColumnIndex("urlpic")));
            mapQuery.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            mapQuery.setId(cursor.getString(cursor.getColumnIndex("url")));
            mapQuery.setSubscribers(cursor.getInt(cursor.getColumnIndex("num")));
            listQuery.add(mapQuery);
        }
        return listQuery;
    }
    //查询
    public List<GvData2.DataBean.ListBean> sqliteQuery2(Context context){

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query("subscribes",null,null,null,null,null,null);
        List<GvData2.DataBean.ListBean> listQuery = new ArrayList<>();
        while (cursor.moveToNext()){
            GvData2.DataBean.ListBean mapQuery = new GvData2.DataBean.ListBean();
            mapQuery.setImg_src(cursor.getString(cursor.getColumnIndex("urlpic")));
            mapQuery.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            mapQuery.setId(cursor.getString(cursor.getColumnIndex("url")));
            mapQuery.setSubscribers(cursor.getInt(cursor.getColumnIndex("num")));
            listQuery.add(mapQuery);
        }
        return listQuery;
    }
    //删除
    public void sqliteDel(Context context,int id){
        DBHelper dbhelper = new DBHelper(context);
        SQLiteDatabase db = dbhelper.getReadableDatabase();

        db.delete("subscribes","url=?",new String[]{id+""});
    }
}
