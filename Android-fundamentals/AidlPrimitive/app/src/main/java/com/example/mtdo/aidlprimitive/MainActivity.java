package com.example.mtdo.aidlprimitive;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getName();
    private ISumService mISumService;
    private ClientListenerImpl listener;
    private Handler mHandler;
    private TextView textView;

    private class ClientListenerImpl extends IClientListener.Stub {

        @Override
        public void showResult(SumResult objResult) throws RemoteException {
            displayResult(objResult.toString());
        }
    }

    private void displayResult(final String strResult){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                textView.setText(strResult);
                //Toast.makeText(this, "Sent from service: " + strResult, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mISumService = ISumService.Stub.asInterface(service);
            sumService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mISumService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler();
        textView = (TextView) findViewById(R.id.tweet_view);
    }

    public void startSumService(View view){
        //Toast.makeText(this,"Start button click", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,AidlSumService.class);
        startService(intent);
        bindService(intent, mConnection, 0);
        bindService(intent, mConnection, 0);
        Toast.makeText(this, "Service bindded", Toast.LENGTH_SHORT).show();
    }

    public void stopSumService(View view){
        //Toast.makeText(this,"Stop button click", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,AidlSumService.class);
        stopService(intent);
    }

    public void useSumService(View view){
        sumService();
    }

    public void sumService(){
        listener = new ClientListenerImpl();
        try {
            mISumService.addListener(listener);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

//        long result = 0;
//        try {
//            result = mISumService.sum(3L);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//        Toast.makeText(this,"Result from SumService = " + result, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this,"Result from SumService = " + result, Toast.LENGTH_SHORT).show();
    }
}
