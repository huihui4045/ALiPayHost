package com.alizhezi.taopiaopiao;

import android.util.Log;

public class OneService extends BaseService {
    int i=0;

    @Override
    public void onCreate() {
        super.onCreate();

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (i<100){

                    Log.e(TAG, "run: "+(i++));


                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }
}
