package com.example.mtdo.aidlprimitive;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class AidlSumService extends Service {
    private final String TAG = this.getClass().getName();
    private IClientListener clientListener;

    private SumResult objResult = new SumResult();

    private Timer timer;

    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            Log.i(TAG, "Timer task doing work");
            if (clientListener != null) {
                try {
                    objResult.setIntSumResult(5);
                    objResult.setStrSumResult("Object result sent from service");
                    clientListener.showResult(objResult);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else {
                Log.i(TAG, "client listener is null");
            }
        }
    };

    public AidlSumService() {
    }

    public class SumServiceImpl extends ISumService.Stub {

        @Override
        public long sum(long n) throws RemoteException {
            return (n + n);
            // return AidlSumService.this.serviceSum(n);
        }

        @Override
        public void addListener(IClientListener listener) throws RemoteException {
            clientListener = listener;
        }
    }

    public long serviceSum(long n){
        return (n * n);
    }

    @Override
    public IBinder onBind(Intent intent) {

        return new SumServiceImpl();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Service created");
        timer = new Timer("AidlTimer");
        timer.schedule(task,1000L, 10 * 1000L);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Service destroy");
    }
}
