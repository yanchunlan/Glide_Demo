package com.example.library.glide;

import android.content.Context;
import android.util.Log;

/**
 * author:  ycl
 * date:  2019/06/20 11:03
 * desc:
 */
public class Glide {

    public static BitmapRequest with(Context context){
        return new BitmapRequest(context);
    }

    private static Context context;

    public static void init(Context ctx) {
        context = ctx;
    }

    public static Context getContext() {
        return context;
    }
}
