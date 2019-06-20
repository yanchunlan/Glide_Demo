package com.example.library.glide;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import com.example.library.cache.DoubleLruCache;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * author:  ycl
 * date:  2019/06/17 21:55
 * desc:
 */
public class BitmapDispatcher extends Thread {

    private Handler handler = new Handler(Looper.getMainLooper());

    // 仅仅只是引用，用于从队列取出数据，真正放入数据在requestManager里面
    private LinkedBlockingQueue<BitmapRequest> requestQueue;

    private DoubleLruCache doubleLruCache ;

    public BitmapDispatcher(LinkedBlockingQueue<BitmapRequest> requestQueue) {
        this.requestQueue = requestQueue;
    }

    public BitmapDispatcher() {
        if (Glide.getContext() != null) {
            doubleLruCache = new DoubleLruCache(Glide.getContext());
        }
    }

    @Override
    public void run() {
        super.run();

        while (!isInterrupted()) {
            try {
                BitmapRequest br = requestQueue.take();

                // loading
                showLoadingImg(br);

                // load url bitmap
                Bitmap bitmap = findBitmap(br);

                // set imageView bitmap
                showImageView(br, bitmap);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void showImageView(final BitmapRequest br, final Bitmap bitmap) {
        if (bitmap!=null && br.getImageView() != null&&br.getUrlMd5().equals(br.getImageView().getTag())) {
            final ImageView iv = br.getImageView();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    iv.setImageBitmap(bitmap);
                    if(br.getRequestListener()!=null){
                        br.getRequestListener().onSuccess(bitmap);
                    }
                }
            });
        }
    }

    private Bitmap findBitmap(BitmapRequest br) {
        Bitmap bitmap = null;
        if (doubleLruCache != null) {
            bitmap = doubleLruCache.get(br);
        }
        if (bitmap == null) {
            bitmap = urlDownLoadImage(br.getUrl());
            if (bitmap != null&&doubleLruCache!=null) {
                doubleLruCache.put(br, bitmap);
            }
        }
        return bitmap;
    }

    private void showLoadingImg(BitmapRequest br) {
        if (br.getResId() > 0 && br.getImageView() != null) {
            final int resId = br.getResId();
            final ImageView iv = br.getImageView();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    iv.setImageResource(resId);
                }
            });
        }
    }


    private Bitmap urlDownLoadImage(String uri) {
        FileOutputStream fos = null;
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
            }
        }
        return bitmap;
    }
}
