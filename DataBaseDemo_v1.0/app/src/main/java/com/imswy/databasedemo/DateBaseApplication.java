package com.imswy.databasedemo;

import android.content.Context;

import org.litepal.LitePalApplication;

public class DateBaseApplication extends LitePalApplication {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getInstance() {
        return mContext;
    }

}
