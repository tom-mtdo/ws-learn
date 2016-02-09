package com.example.mtdo.tweetservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private Handler handler;
    private TextView tweetView;
    private TweetCollectorApi api;
    private CollectorListenerImpl collectorListener;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "Service connection established");

            // that's how we get the client side of the IPC connection
            api = TweetCollectorApi.Stub.asInterface(service);
            useTweetService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "Service connection closed");
        }
    };

    private void useTweetService(){
        collectorListener = new CollectorListenerImpl();

        try {
            api.addListener(collectorListener);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to add listener", e);
        }

        updateTweetView();
    }

    private class CollectorListenerImpl extends TweetCollectorListener.Stub {
        @Override
        public void handleTweetsUpdated() throws RemoteException {
            updateTweetView();
        }
    };
//
//    private TweetCollectorListener.Stub collectorListener = new TweetCollectorListener.Stub() {
//        @Override
//        public void handleTweetsUpdated() throws RemoteException {
//            updateTweetView();
//        }
//    };

    private void updateTweetView() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    TweetSearchResult result = api.getLatestSearchResult();

                    if (result.getTweets().isEmpty()) {
                        tweetView.setText("Sorry, no tweets yet");
                    } else {
                        StringBuilder builder = new StringBuilder();
                        for (Tweet tweet : result.getTweets()) {
                            builder.append(String.format("<br><b>%s</b>: %s<br>",
                                    tweet.getAuthor(),
                                    tweet.getText()));
                        }

                        tweetView.setText(Html.fromHtml(builder.toString()));
                    }
                } catch (Throwable t) {
                    Log.e(TAG, "Error while updating the UI with tweets", t);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Log.d(TAG, "Service name: " + TweetCollectorService.class.getName());
        //Toast.makeText(this, TweetCollectorService.class.getName(), Toast.LENGTH_SHORT).show();

        handler = new Handler();
        tweetView = (TextView) findViewById(R.id.tweet_view);

        Intent intent = new Intent(this, TweetCollectorService.class);
        startService(intent);

        bindService(intent, serviceConnection, 0);

        Log.i(TAG, "Activity created");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            api.removeListener(collectorListener);
            unbindService(serviceConnection);
        } catch (Throwable t) {
            // catch any issues, typical for destroy routines
            // even if we failed to destroy something, we need to continue destroying
            Log.w(TAG, "Failed to unbind from the service", t);
        }

        Log.i(TAG, "Activity destroyed");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
