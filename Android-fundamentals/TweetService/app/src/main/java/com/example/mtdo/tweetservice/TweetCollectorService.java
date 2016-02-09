package com.example.mtdo.tweetservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TweetCollectorService extends Service {
    // Log tag
    private static final String TAG = TweetCollectorService.class.getName();
    private final Object latestSearchResultLock = new Object();
    private TweetSearchResult latestSearchResult;

    private List<TweetCollectorListener> listeners = new ArrayList<TweetCollectorListener>();

    private Timer timer;

    private TweetCollectorApi.Stub apiEndpoint = new TweetCollectorApi.Stub() {

        @Override
        public TweetSearchResult getLatestSearchResult() throws RemoteException {
            synchronized (latestSearchResultLock) {
                return latestSearchResult;
            }
        }

        @Override
        public void addListener(TweetCollectorListener listener) throws RemoteException {
            synchronized (listeners) {
                listeners.add(listener);
            }
        }

        @Override
        public void removeListener(TweetCollectorListener listener) throws RemoteException {
            synchronized (listeners) {
                listeners.remove(listener);
            }
        }

    };

    private TimerTask updateTask = new TimerTask() {
        @Override
        public void run() {
            Log.i(TAG, "Timer task doing work");

            try {
                TweetSearcher searcher = new TweetSearcher();
                TweetSearchResult newSearchResult = searcher.search();
                Log.d(TAG, "Retrieved " + newSearchResult.getTweets().size() + " tweets");

                synchronized (latestSearchResultLock) {
                    latestSearchResult = newSearchResult;
                }

                synchronized (listeners) {
                    for (TweetCollectorListener listener : listeners) {
                        try {
                            /*
                            ********************
                             */
                            Log.i(TAG, "after calling listener handle...");
                            listener.handleTweetsUpdated();
                            Log.i(TAG, "after calling listener handle...");
                            /*
                            ********************
                             */

                        } catch (RemoteException e) {
                            Log.w(TAG, "Failed to notify listener " + listener, e);
                        }
                    }
                }
            } catch (Throwable t) {
                Log.e(TAG, "Failed to get tweet results", t);
            }
        }
    };

    public void getData(){
        Log.i(TAG, "Getting data");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        if (TweetCollectorService.class.getName().equals(intent.getAction())){
            Log.d(TAG, "Bound by intent " + intent);
            return apiEndpoint;
//        } else {
//            return null;
//        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Service creating");

        timer = new Timer("TweetCollectorTimer");
        timer.schedule(updateTask, 1000L, 10 * 1000L);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Service destroying");
        timer.cancel();
        timer = null;
    }

}
