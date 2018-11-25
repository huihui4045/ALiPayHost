package com.alizhezi.taopiaopiao;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

public class SecondActivity extends BaseActivity {

    private OneReceiver oneReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        oneReceiver=new OneReceiver();




        findViewById(R.id.sendBroadCast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent=new Intent();
                intent.putExtra("data","來自插件APP");

                intent.setAction("com.alizhezi.taopiaopiao.OneReceiver");

                sendBroadcast(intent);

            }
        });

        findViewById(R.id.bindBroadCast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IntentFilter intentFilter=new IntentFilter();

                intentFilter.addAction("com.alizhezi.taopiaopiao.OneReceiver");

                registerReceiver(oneReceiver,intentFilter);

            }
        });

        findViewById(R.id.unbindBroadCast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                unregisterReceiver(oneReceiver);

            }
        });

    }
}
