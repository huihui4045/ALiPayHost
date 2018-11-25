package com.alizhezi.pay.host;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static String  pluginApkName="plugin.apk";

    private String TAG=this.getClass().getSimpleName();

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);

        PluginManager.getInstance().extractAssets(this,pluginApkName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        registerReceiver(mReceiver,new IntentFilter("com.alizhezi.pay.host.DEMO"));

    }


    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, " 我是宿主，收到你的消息,握手完成!", Toast.LENGTH_SHORT).show();
        }
    };

    public void loadPlugin(View view){

        PluginManager.getInstance().loadPath(this,pluginApkName);
    }

    public void start(View view){

        Intent intent=new Intent(this,ProxyActivity.class);

        String name = PluginManager.getInstance().getPackageInfo().activities[0].name;

        Log.e(TAG,"name:"+name);
        intent.putExtra("className", name);


        startActivity(intent);


    }

    public void  sendBroadcastToPlugin(View view){



        sendBroadcast(new Intent("com.alizhezi.taopiaopiao.StaticReceiver"));

    }
}
