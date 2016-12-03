package com.qianfeng.myapplication;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Administrator on 2016/11/9.
 */

public class MyApplication extends Application {
    public Boolean isLogined = false;
    public String userIcon = "";
    public String userName = "";
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true);
    }
}
