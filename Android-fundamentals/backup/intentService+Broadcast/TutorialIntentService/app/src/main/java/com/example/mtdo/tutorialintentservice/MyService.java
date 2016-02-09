package com.example.mtdo.tutorialintentservice;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

/**
 * Created by thangdo on 12/11/2015.
 */
public class MyService extends IntentService {
    public static String BROADCAST_ACTION = "com.example.android.threadsample.BROADCAST";
    public static final String EXTENDED_DATA_STATUS = "com.example.android.threadsample.STATUS";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyService() {
        super("my_intent_service");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(MyService.this, "Service start", Toast.LENGTH_LONG).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(MyService.this, "Service stop", Toast.LENGTH_LONG).show();
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Intent serviceIntent = new Intent(BROADCAST_ACTION).putExtra(EXTENDED_DATA_STATUS, "From MyService");
        LocalBroadcastManager.getInstance(this).sendBroadcast(serviceIntent);

        synchronized (this){
            try {
                wait(20000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
