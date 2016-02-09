package com.example.mtdo.managethread;

import android.util.Log;

/**
 * Created by thangdo on 28/12/2015.
 */
public class AccelerometerUploadRunnable implements Runnable{

    private final String TAG = this.getClass().getName();

    @Override
    public void run() {
        // Moves the current Thread into the background
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        //mPhotoTask.setImageDecodeThread(Thread.currentThread());
        // sleep 10 s

        Log.i(TAG, "Thread is running, wait 30s");
        try {
            Thread.sleep(5 * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "Thread finished");
    }
}
