package com.example.mtdo.managethread;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by thangdo on 28/12/2015.
 */
public class AccelerometerManager {

    private static AccelerometerManager sAccInstance = null;
    private static String poolInfo = "";

    private final LinkedBlockingDeque mUploadWorkQueue;
    private final ThreadPoolExecutor mUploadThreadPool;

    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private static final int KEEP_ALIVE_TIME = 1;
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

    private AccelerometerManager(){
        mUploadWorkQueue = new LinkedBlockingDeque<Runnable>();
        mUploadThreadPool = new ThreadPoolExecutor(
                NUMBER_OF_CORES,       // Initial pool size
                NUMBER_OF_CORES,       // Max pool size
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                mUploadWorkQueue);

    }

    static {
        sAccInstance = new AccelerometerManager();
    }

    static public void startUpload(AccelerometerModel accData){
        AccelerometerUploadRunnable mUploadRunnable = new AccelerometerUploadRunnable();
        sAccInstance.mUploadThreadPool.execute(mUploadRunnable);

    }

    public static AccelerometerManager getInstance(){
        return sAccInstance;
    }

    public String getPoolInfo(){
        String result = "Pool info: ";
        result += "\nInit core: " + NUMBER_OF_CORES;
        result += "\nInit max core: " + NUMBER_OF_CORES;
        result += "\nPool size: " + mUploadThreadPool.getPoolSize();

        return result;
    }
}
