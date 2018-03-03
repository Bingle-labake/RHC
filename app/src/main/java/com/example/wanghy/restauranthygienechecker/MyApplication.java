package com.example.wanghy.restauranthygienechecker;
import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;


public class MyApplication extends Application {
    public MyApplication() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //Fresco初始化
        Fresco.initialize(getApplicationContext());

    }

}
