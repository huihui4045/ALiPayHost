package com.alizhezi.taopiaopiao;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.alizhezi.pay.stander.PayInterfaceActivity;

public class BaseActivity extends Activity implements PayInterfaceActivity {

    protected Activity that;

    @Override
    public void attach(Activity proxyActivity) {

        this.that=proxyActivity;

    }

    @Override
    public void setContentView(View view) {
        if (that != null) {
            that.setContentView(view);
        }else {
            super.setContentView(view);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        that.setContentView(layoutResID);
    }

    @Override
    public ComponentName startService(Intent service) {
        Intent m = new Intent();
        m.putExtra("serviceName", service.getComponent().getClassName());
        return that.startService(m);
    }

    @Override
    public View findViewById(int id) {
        if(that!=null){
            return that.findViewById(id);
        }

            return  super.findViewById(id);


    }

    @Override
    public Intent getIntent() {
        if(that!=null){
            return that.getIntent();
        }
        return super.getIntent();
    }
    @Override
    public ClassLoader getClassLoader() {
        return that.getClassLoader();
    }


    @Override
    public void startActivity(Intent intent) {
//        ProxyActivity --->className
        Intent m = new Intent();
        m.putExtra("className", intent.getComponent().getClassName());
        that.startActivity(m);
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        if (that!=null){

            return that.registerReceiver(receiver,filter);
        }
        return super.registerReceiver(receiver, filter);
    }

    @Override
    public void unregisterReceiver(BroadcastReceiver receiver) {
        if (that!=null){

            that.unregisterReceiver(receiver);

        }else {

            super.unregisterReceiver(receiver);
        }
    }

    @Override
    public void sendBroadcast(Intent intent) {
        if (that!=null){

            that.sendBroadcast(intent);
        }else {

            super.sendBroadcast(intent);
        }
    }

    @NonNull
    @Override
    public LayoutInflater getLayoutInflater() {
        if(that!=null){

            return that.getLayoutInflater();
        }else {

            return super.getLayoutInflater();
        }
    }

    @Override
    public ApplicationInfo getApplicationInfo() {
        if(that!=null){

            return that.getApplicationInfo();
        }else {

            return super.getApplicationInfo();
        }
    }


    @Override
    public Window getWindow() {
        if (that!=null){

            return that.getWindow();
        }else {

            return  super.getWindow();
        }
    }


    @Override
    public WindowManager getWindowManager() {
        if (that!=null){

            return that.getWindowManager();
        }else {

            return  super.getWindowManager();
        }

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }
}
