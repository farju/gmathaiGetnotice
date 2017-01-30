package com.mgn.get_noticed;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;


public class GetNoticedApplication extends Application {

    private static final String SHARED_PREFERENCES_NAME = "AppData";

    private static GetNoticedApplication sInstance;

    public static GetNoticedApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public SharedPreferences getSharedPreferences() {
        return getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

}
