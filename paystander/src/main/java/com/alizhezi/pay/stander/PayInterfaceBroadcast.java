package com.alizhezi.pay.stander;

import android.content.Context;
import android.content.Intent;

public interface PayInterfaceBroadcast {

    void attach(Context context);

    void onReceive(Context context, Intent intent);
}
