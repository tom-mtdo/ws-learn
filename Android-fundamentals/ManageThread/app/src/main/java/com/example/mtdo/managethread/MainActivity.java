package com.example.mtdo.managethread;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void upload(View view){
        Log.i(TAG, "Before for loop");
        for(int i=1; i<=3; i++){
            AccelerometerManager.startUpload(null);
        }
        Log.i(TAG, "After for loop");
        Log.i(TAG, AccelerometerManager.getInstance().getPoolInfo());
    }
}
