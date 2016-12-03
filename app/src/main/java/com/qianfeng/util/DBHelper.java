package com.qianfeng.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/11/16.
 */

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "anydata", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "create table if not exists subscribes(_id Integer primary key autoincrement" +
                ",urlpic text" +
                ",url text" +
                ",title text" +
                ",num Integer" +
                ")";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion<newVersion){
            db.execSQL("drop table subscribes");
            onCreate(db);
        }
    }
}
