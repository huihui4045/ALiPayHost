package com.alizhezi.taopiaopiao;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.alizhezi.pay.stander.PayInterfaceBroadcast;

public class OneReceiver extends BroadcastReceiver implements PayInterfaceBroadcast {

    @Override
    public void attach(Context context) {

        Toast.makeText(context,"註冊廣播成功",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String data = intent.getStringExtra("data");

        Toast.makeText(context,"收到廣播data:"+data,Toast.LENGTH_LONG).show();

    }
}
