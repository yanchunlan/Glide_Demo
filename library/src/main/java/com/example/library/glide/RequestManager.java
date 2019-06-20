package com.example.library.glide;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * author:  ycl
 * date:  2019/06/17 22:07
 * desc:
 * 已经开了线程，只需要把数据放入队列中，在线程中会从队列取出数据，并执行
 */
public class RequestManager {

    private static volatile RequestManager instance = null;

    // 柱塞队列
    private LinkedBlockingQueue<BitmapRequest> requestQueue = new LinkedBlockingQueue<>();

    private BitmapDispatcher[] bitmapDispatchers;

    public RequestManager() {
        start();
    }

    // 开启所有线程
    public void start() {
        stop();
        startAllDispatcher();
    }


    public static RequestManager getInstance() {
        if (instance == null) {
            synchronized (RequestManager.class) {
                if (instance == null) {
                    instance = new RequestManager();
                }
            }
        }
        return instance;
    }

    public void addBitmapRequest(BitmapRequest bitmapRequest) {
        if (bitmapRequest == null) {
            return;
        }
        if (!requestQueue.contains(bitmapRequest)) {
            requestQueue.add(bitmapRequest);
        }
    }


    public void startAllDispatcher() {
        //获取手机支持的单个应用最大的线程数
        int threadCount = Runtime.getRuntime().availableProcessors();
        // 此处根据不同的手机，可能是4，5，6，都可能存在
        bitmapDispatchers = new BitmapDispatcher[threadCount];
        for (int i = 0; i < threadCount; i++) {
            BitmapDispatcher bd = new BitmapDispatcher(requestQueue);
            bd.start();
            //要将每个dispatcher放到数组中，方便统一管理
            bitmapDispatchers[i] = bd;
        }
    }


    public void stop() {
        //中断所有线程
        if (bitmapDispatchers != null && bitmapDispatchers.length > 0) {
            for (BitmapDispatcher bd : bitmapDispatchers) {
                if (!bd.isInterrupted()) {
                    bd.interrupt();
                }
            }
        }
    }
}
