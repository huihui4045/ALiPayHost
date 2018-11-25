package com.alizhezi.pay.host;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.alizhezi.pay.stander.PayInterfaceBroadcast;

import java.lang.reflect.Constructor;

public class ProxyReceiver extends BroadcastReceiver {


    private  PayInterfaceBroadcast payInterfaceBroadcast;
    private String className;

    public ProxyReceiver(String className,Context context) {
        this.className = className;

        try {
            Class<?> clz = PluginManager.getInstance().getmDexClassLoader().loadClass(className);


            Constructor<?> constructor = clz.getDeclaredConstructor(new Class[]{});

            Object instance = constructor.newInstance(new Object[]{});

             payInterfaceBroadcast = (PayInterfaceBroadcast) instance;

            payInterfaceBroadcast.attach(context);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        payInterfaceBroadcast.onReceive(context,intent);

    }
}
