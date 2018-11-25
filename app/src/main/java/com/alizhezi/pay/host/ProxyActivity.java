package com.alizhezi.pay.host;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import com.alizhezi.pay.stander.PayInterfaceActivity;

import java.lang.reflect.Constructor;

public class ProxyActivity extends Activity {

    private String className;

    private PayInterfaceActivity payInterfaceActivity;

    private String TAG="ProxyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        className=getIntent().getStringExtra("className");

        try {
            Class<?> clz = getClassLoader().loadClass(className);

            Constructor<?> constructor = clz.getConstructor(new Class[]{});

            Object object = constructor.newInstance(new Object[]{});


            payInterfaceActivity= (PayInterfaceActivity) object;

            payInterfaceActivity.attach(this);

            Bundle bundle=new Bundle();

            payInterfaceActivity.onCreate(bundle);



        } catch (Exception e) {
            e.printStackTrace();

            Log.e(TAG,"error:"+e.getMessage());
        }
    }

    @Override
    public void startActivity(Intent intent) {

        String className1=intent.getStringExtra("className");
        Intent intent1 = new Intent(this, ProxyActivity.class);
        intent1.putExtra("className", className1);
        super.startActivity(intent1);

    }

    @Override
    public ComponentName startService(Intent service) {
        String serviceName = service.getStringExtra("serviceName");
        Intent intent1 = new Intent(this, ProxyService.class);
        intent1.putExtra("serviceName", serviceName);
        return super.startService(intent1);
    }

    private ProxyReceiver proxyReceiver;

    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {

        proxyReceiver=new ProxyReceiver(receiver.getClass().getName(),this);


        return super.registerReceiver(proxyReceiver, filter);
    }

    @Override
    public void unregisterReceiver(BroadcastReceiver receiver) {


        super.unregisterReceiver(proxyReceiver);
    }

    @Override
    protected void onStart() {
        super.onStart();

        payInterfaceActivity.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        payInterfaceActivity.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();

        payInterfaceActivity.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();

        payInterfaceActivity.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        payInterfaceActivity.onDestroy();
    }

    @Override
    public ClassLoader getClassLoader() {
        return PluginManager.getInstance().getmDexClassLoader();
    }

    @Override
    public Resources getResources() {
        return PluginManager.getInstance().getmResources();
    }


}
