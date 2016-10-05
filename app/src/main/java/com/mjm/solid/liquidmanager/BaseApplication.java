package com.mjm.solid.liquidmanager;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

public class BaseApplication extends Application {
    private static BaseApplication mApplication;
    private static int mMainTid;
    private static Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        
        mApplication = this;
        mMainTid = android.os.Process.myTid();
        handler = new Handler();
    }

    public static Context getmApplication() {
        return mApplication;
    }

    public static int getMainTid() {
        return mMainTid;
    }

    public static Handler getHandler() {
        return handler;
    }


}
