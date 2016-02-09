package com.example.mtdo.tweetservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by thangdo on 20/11/2015.
 */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, TweetCollectorService.class));
        Log.i("Broadcast","Broadcast");
    }
}
