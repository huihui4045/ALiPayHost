package com.alizhezi.pay.host;

import android.app.Application;
import android.content.Context;

public class UPFApplication extends Application {

    private static Context sContext;

    public static Context getContext() {
        return sContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sContext = base;
    }
}
