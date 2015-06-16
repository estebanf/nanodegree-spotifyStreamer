package com.estebanf.nanodegree.spotifystreamer;

import android.app.Application;
import android.content.Context;

/**
 * Created by estebanf on 6/16/15.
 */
public class App extends Application {
    private static App mApp;

    public static Context context() {
        return mApp.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }
}
