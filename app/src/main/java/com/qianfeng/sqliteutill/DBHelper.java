package com.qianfeng.sqliteutill;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/11/14.
 */

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "userinfo", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String insertSQL = "create table users if not exists(" +
                "_id Integer primary key autoincrement" +
                ",userIcon text" +
                ",userName text)";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
