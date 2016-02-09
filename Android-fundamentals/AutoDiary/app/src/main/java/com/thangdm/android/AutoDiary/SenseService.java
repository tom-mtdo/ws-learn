package com.thangdm.android.AutoDiary;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class SenseService extends Service implements SensorEventListener, LocationListener{

	// package of accelerometer sensor data at a time t
	AccelerometerDataPack acceDataPack = new AccelerometerDataPack();
	
	// package of gps data at a time t
	GpsDataPack gpsDataPack = new GpsDataPack();

	//Data pre-processor
	DataPreProcessor mProcessor;

	// Accelerometer data components
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
    
    //Location
    LocationManager locationManager;
    //LocationListener locationListener;

	// response from server
	//private String mResponse = "Finish Upload Data";
	
	// SQLite database
	SensorDbAdapter sensorDb = null;
	
	// app status stop .etc
	private final String APP_STATUS_RUNNING = "RUNNING";
	private final String APP_STATUS_INIT = "INIT";
	private final String APP_STATUS_EXITING = "EXITING";
	
	private String app_status = APP_STATUS_INIT;

	// waiting time before send data again
	private long SENDING_DATA_INTERVAL 	= 	1000; // millisecond = 1s
	
	// location (gps) update interval 
	private long LOCATION_UPDATE_INTERVAL 	= 	5000; // millisecond = 5s	
	private long LOCATION_UPDATE_DISTANCE 	= 	5; // M
	
	// thread for sending data
	//private Thread sendDataThread;

	
	// for service handling -------------------------------------------------------------------
	private NotificationManager mNM;
	private Looper mServiceLooper;
	private ServiceHandler mServiceHandler;
	
	// Handler that receives messages from the thread
	private final class ServiceHandler extends Handler {
	    public ServiceHandler(Looper looper) {
	        super(looper);
	    }
	    @Override
	    public void handleMessage(Message msg) {
	        // Normally we would do some work here, like download a file.
	        // For our sample, we just sleep for 5 seconds.
	        //long endTime = System.currentTimeMillis() + 5*1000;
	        //while (System.currentTimeMillis() < endTime) {
	            synchronized (this) {
	                try {
	                	showNotification();	                	
	                    //wait(endTime - System.currentTimeMillis());
	                } catch (Exception e) {
	                }
	            }
	        //}
	        // Stop the service using the startId, so that we don't stop
	        // the service in the middle of handling another job
	        //stopSelf(msg.arg1);
	    }
	}

    // Unique Identification Number for the Notification.
    // We use it on Notification start, and to cancel it.
    private int NOTIFICATION = R.string.local_service_started;

    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class LocalBinder extends Binder {
    	SenseService getService() {
            return SenseService.this;
        }
    }

    @Override
    public void onCreate() {
        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
        //HandlerThread thread = new HandlerThread("ServiceStartArguments", Process..THREAD_PRIORITY_BACKGROUND);
    	HandlerThread thread = new HandlerThread("ServiceStartArguments");
        thread.start();
        
        // Get the HandlerThread's Looper and use it for our Handler 
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
        
        // for sensing ------------------------------------------------------------------
        // Prepare data file
        mProcessor = new DataPreProcessor();
        mProcessor.openDataFile();
        
        // Open SQLite database
        //sensorDb = new SensorDbAdapter(this);
        //sensorDb.open();
        
        //Get instance of sensor manager
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        //sendDataThread = new Thread(){
		//	@Override public void run(){
		//		_doInBackgroundSendingData();
		//	}
		//};
		
        //Location initiation
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        
        // start sensing ---------------------------------------------------------------------------
		// get input host name/address
		//Declare.web_server_host = txt_content.getText().toString().trim();
		
		// register to get sensor data
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        
        // register to get gps data (use both GPS and network)
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_UPDATE_INTERVAL, LOCATION_UPDATE_DISTANCE, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_UPDATE_INTERVAL, LOCATION_UPDATE_DISTANCE, this);
        
        // start sending data
        //sendDataThread.start();
        
        app_status = APP_STATUS_INIT;
        
        // end for sensing ------------------------------------------------------------------
        
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        // Display a notification about us starting.  We put an icon in the status bar.
        showNotification();
    }

    // prodedure for sensing --------------------------------------------------------------------
    // implement sensor manager listener
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
            return;
        /*
         * record the accelerometer data, the event's timestamp as well as
         * the current time.
         */
		acceDataPack.setAcceX(event.values[0]);
		acceDataPack.setAcceY(event.values[1]);
		acceDataPack.setAcceZ(event.values[2]);
		
		acceDataPack.setSensorTimeStamp(event.timestamp);
		acceDataPack.setCpuTimeStamp(System.currentTimeMillis());
		
		// pre-process data
		String strActivity = mProcessor.processAccelerometerData(sensorDb, acceDataPack);

		// decode recognized activities 00 = walk, 01 = run, 10 = driving, 11 = still
		String strDecode = "";
		if ( (strActivity != "") && (!strActivity.equalsIgnoreCase("null")) && (strActivity != null) ){
			String strCode = strActivity.substring(0, 2);
			if (strCode.equalsIgnoreCase("00")){strDecode = "Walking";}
			if (strCode.equalsIgnoreCase("01")){strDecode = "Runing";}
			if (strCode.equalsIgnoreCase("10")){strDecode = "Driving";}
			if (strCode.equalsIgnoreCase("11")){strDecode = "Staying still";}
			//showContent(strDecode);
			//R.string.local_service_label = strDecode;
			///////////////////////////////////////////////
			// change service label here for notification
			////////////////////////////////////////////////
		}
		//showContent(strDecode);
	}
	
	// implement location listener
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		// just store gps data, when process sensor data will pickup gps data 
		// as GPS is sample at lower rate.
		gpsDataPack.setGpsLat(location.getLatitude());
		gpsDataPack.setGpsLong(location.getLongitude());
		gpsDataPack.setGpsAccuracy(location.getAccuracy());
		//gpsDataPack.setGpsTime(location.getTime());
		gpsDataPack.setGpsTime(System.currentTimeMillis());
		if (location.hasSpeed()) {
			gpsDataPack.setGpsSpeed(location.getSpeed());	// meters/second
		}
		
		mProcessor.processGpsData(sensorDb, gpsDataPack);
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStatusChanged(String provider, int status,Bundle extras) {
		// TODO Auto-generated method stub
	}
	
    // end for sensing ---------------------------------------------------------------
	
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);
        
        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);
        
        app_status = APP_STATUS_RUNNING;
        
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        // Cancel the persistent notification.
        mNM.cancel(NOTIFICATION);

        // Tell the user we stopped.
        Toast.makeText(this, R.string.local_service_stopped, Toast.LENGTH_SHORT).show();
    	
    	// for end sensing ------------------------------------------------------------
    	synchronized (this){
    		app_status = APP_STATUS_EXITING;
    	}
    	
    	mProcessor.closeDataFile();
    	
    	// wait for sending data thread finish
    	//try {
		//	sendDataThread.join();
		//} catch (InterruptedException e) {
		//	// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
    	
    	try {
	    	mSensorManager.unregisterListener(this);
	    	locationManager.removeUpdates( this );
	    	locationManager.removeGpsStatusListener( (Listener)this );
	        mProcessor.closeDataFile();
	        if (sensorDb != null){
	        	sensorDb.close();
	        }
    	} catch (Exception e) {
    		// try to stop gps
	    	locationManager.removeUpdates( this );
	    	locationManager.removeGpsStatusListener( (Listener)this );
    	} finally{}
    	
    	// end for end singing --------------------------------------------------------
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    // This is the object that receives interactions from clients.  See
    // RemoteService for a more complete example.
    private final IBinder mBinder = new LocalBinder();

    /**
     * Show a notification while this service is running.
     */
    private void showNotification() {
        // In this sample, we'll use the same text for the ticker and the expanded notification
        CharSequence text = getText(R.string.local_service_started);

        // Set the icon, scrolling text and timestamp
        Notification notification = new Notification(R.drawable.icon, text,
                System.currentTimeMillis());

        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, AutoDiary.class), 0);

        // Set the info for the views that show in the notification panel.
        notification.setLatestEventInfo(this, getText(R.string.local_service_label),
                       text, contentIntent);

        // Send the notification.
        mNM.notify(NOTIFICATION, notification);
    }


}
