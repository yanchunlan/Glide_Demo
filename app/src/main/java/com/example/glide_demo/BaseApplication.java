package com.example.glide_demo;

import android.app.Application;

import com.example.library.glide.Glide;

/**
 * author:  ycl
 * date:  2019/06/20 11:16
 * desc:
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Glide 注册内部需要context参数
        Glide.init(this);
    }
}
