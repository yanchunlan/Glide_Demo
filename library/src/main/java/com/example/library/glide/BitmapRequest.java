package com.example.library.glide;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.example.library.utils.MD5Utils;

import java.lang.ref.SoftReference;

/**
 * author:  ycl
 * date:  2019/06/17 21:51
 * desc:
 */
public class BitmapRequest {

    private String url;

    private Context context;

    private SoftReference<ImageView> imageView;

    private int resId;

    private RequestListener requestListener;

    private String urlMd5;// 图片标识，防止错位

    public BitmapRequest(Context context) {
        this.context = context;
    }

    public BitmapRequest load(String url) {
        this.url = url;
        this.urlMd5 = MD5Utils.toMD5(url);
        return this;
    }

    public BitmapRequest loading(@DrawableRes int  resId) {
        this.resId = resId;
        return this;
    }

    public BitmapRequest listener(RequestListener listener){
        this.requestListener = listener;
        return this;
    }

    public void into(ImageView imageView){
        imageView.setTag(this.urlMd5);
        this.imageView = new SoftReference<>(imageView);
        RequestManager.getInstance().addBitmapRequest(this);
    }

    public String getUrl() {
        return url;
    }

    public Context getContext() {
        return context;
    }

    public  ImageView getImageView() {
        return imageView.get();
    }

    public int getResId() {
        return resId;
    }

    public RequestListener getRequestListener() {
        return requestListener;
    }

    public String getUrlMd5() {
        return urlMd5;
    }
}
