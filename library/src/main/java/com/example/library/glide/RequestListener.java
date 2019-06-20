package com.example.library.glide;

import android.graphics.Bitmap;

/**
 * author:  ycl
 * date:  2019/06/17 21:51
 * desc:
 */
public interface RequestListener {

    boolean onSuccess(Bitmap bitmap);

    boolean onFailure();

}
