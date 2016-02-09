/**
 * ref: Accelerometer PlayActivity example
 */

package com.thangdm.android.AutoDiary;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
//import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AutoDiary extends Activity implements SensorEventListener, LocationListener {
	
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
	
	// stop app .etc
	private final String APP_STATUS_RUNNING = "RUNNING";
	private final String APP_STATUS_INIT = "INIT";
	private final String APP_STATUS_EXITING = "EXITING";
	
	private String app_status = APP_STATUS_INIT;

	// waiting time before send data again
	private long SENDING_DATA_INTERVAL 	= 	1000; // millisecond = 1s
	
	// location (gps) update interval 
	private long LOCATION_UPDATE_INTERVAL 	= 	5000; // millisecond = 8s	
	private long LOCATION_UPDATE_DISTANCE 	= 	0; // M
	
	// thread for sending data
	//private Thread sendDataThread;
    
    //UI components
	private Button bt_start;
	private Button bt_stop;
	private Button bt_startDetect;
	private Button bt_startSend;
	private Button bt_clearDb;
	private Button bt_countDb;
	private EditText txt_content;
	private EditText txt_content2;

	
	
	// for test
	// private String test = "";

	public void initiate() {
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
        /*
        locationListener = new LocationListener(){

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
			
        };
        *
        */
        
        app_status = APP_STATUS_INIT;
	}
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // prepare for app
        //initiate();

        // user interface items
        bt_start = (Button) findViewById(R.id.bt_start);
        bt_stop = (Button) findViewById(R.id.bt_stop);
        bt_startDetect = (Button) findViewById(R.id.bt_startDetect);
        bt_startSend = (Button) findViewById(R.id.bt_startSend);
        bt_clearDb = (Button) findViewById(R.id.bt_clearDb);
        bt_countDb = (Button) findViewById(R.id.bt_countDb);
        
        txt_content = (EditText) findViewById(R.id.txt_content);
        txt_content2 = (EditText) findViewById(R.id.txt_content2);
        
        // set default web server host
        txt_content.setText(Declare.web_server_host);

        bt_countDb.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				bt_countDbOnClick();
			}
		});

        bt_clearDb.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				bt_clearDbOnClick();
			}
		});
        
        bt_startDetect.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				bt_startDetectOnClick();
			}
		});

        bt_startSend.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				bt_startSendOnClick();
			}
		});
        
        bt_start.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				bt_startOnClick();
			}
		});
        
        bt_stop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				bt_stopOnClick();
			}
		});

        app_status = APP_STATUS_RUNNING;
    }
    
    public void bt_countDbOnClick(){
    	
    	long countRecords1 = 0;
    	long countRecords2 = 0;
    	
    	// count db
    	countRecords1 = sensorDb.countDb();	// gps data table
    	countRecords2 = sensorDb.countDb3(); // prim activities table
    	
    	// inform number of record deleted
    	Toast.makeText(this, "Gps: " + countRecords1 + " records, act: " + countRecords2 + " records!", Toast.LENGTH_LONG).show();
    }

    public void bt_clearDbOnClick(){
    	int deletedRecords = 0;
    	
    	// clear Db
    	deletedRecords = sensorDb.clearDb();
    	
    	// inform number of record deleted
    	Toast.makeText(this, "Deleted: " + deletedRecords + " records!", Toast.LENGTH_LONG).show();
    }
    
	public void bt_stopOnClick() {
		//doBindService();		
		doStopService();
	}
	
	public void bt_startOnClick() {
		doStartService();
		//doBindService();
		
		/*
		// get input host name/address
		Declare.web_server_host = txt_content.getText().toString().trim();
		
		// register to get sensor data
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        
        // register to get gps data (use both GPS and network)
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_UPDATE_INTERVAL, LOCATION_UPDATE_DISTANCE, locationListener);
        //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_UPDATE_INTERVAL, LOCATION_UPDATE_DISTANCE, locationListener);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_UPDATE_INTERVAL, LOCATION_UPDATE_DISTANCE, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_UPDATE_INTERVAL, LOCATION_UPDATE_DISTANCE, this);
        
        // start sending data
        //sendDataThread.start();
         * */
	}

	public void bt_exitOnClick() {
		// destroy
		onDestroy();
	}
	
	public void bt_startSendOnClick(){
		// get input host name/address
		Declare.web_server_host = txt_content.getText().toString().trim();

        // start sending data
        //sendDataThread.start();
	}

	public void bt_startDetectOnClick(){
		// register to get sensor data
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        // register to get gps data (use both GPS and network)
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_UPDATE_INTERVAL, LOCATION_UPDATE_DISTANCE, locationListener);
        //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_UPDATE_INTERVAL, LOCATION_UPDATE_DISTANCE, locationListener);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_UPDATE_INTERVAL, LOCATION_UPDATE_DISTANCE, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_UPDATE_INTERVAL, LOCATION_UPDATE_DISTANCE, this);
        
	}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //doUnbindService();
        /*
    	synchronized (this){
    		app_status = APP_STATUS_EXITING;
    	}
    	
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
    	*
    	*/
    }

    // for service -------------------------------------------------------------
    private SenseService mBoundService;
    private ComponentName mStartedService;
    private boolean mIsBound;
    private boolean mIsStarted;
    
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service.  Because we have bound to a explicit
            // service that we know is running in our own process, we can
            // cast its IBinder to a concrete class and directly access it.
            mBoundService = ((SenseService.LocalBinder)service).getService();

            // Tell the user about this for our demo.
            //Toast.makeText(Binder.this, R.string.local_service_connected, Toast.LENGTH_SHORT).show();
                    
            Toast.makeText(AutoDiary.this, R.string.local_service_connected,
                            Toast.LENGTH_SHORT).show();                    
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            // Because it is running in our same process, we should never
            // see this happen.
            mBoundService = null;
            Toast.makeText(AutoDiary.this, R.string.local_service_disconnected,
                    Toast.LENGTH_SHORT).show();
        }
    };

    void doStartService() {
	    Intent intent = new Intent(AutoDiary.this, SenseService.class);
	    mStartedService = startService(intent);
	    mIsStarted = true;
    }
    
    void doBindService() {
        // Establish a connection with the service.  We use an explicit
        // class name because we want a specific service implementation that
        // we know will be running in our own process (and thus won't be
        // supporting component replacement by other applications).
        bindService(new Intent(AutoDiary.this, 
                SenseService.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doStopService() {
        //if (mIsBound) {
    	    Intent intent = new Intent(AutoDiary.this, SenseService.class);
    	    stopService(intent);
    	    mIsBound = false;
    	    mIsStarted = false;
        //}
    }
    
    void doUnbindService() {
        if (mIsBound) {
            // Detach our existing connection.
            unbindService(mConnection);
            mIsBound = false;
        }
    }
    
    // end for service ------------------------------------------------------------------------
    
    public void _doInBackgroundSendingData(){
		FileUploadClient uploadClient = new FileUploadClient();
    	// keep sending data until exit app
		int dataCount = 0;
		
    	while ( !app_status.equalsIgnoreCase(APP_STATUS_EXITING) ){
    		dataCount = uploadClient.sendData(sensorDb);

	    	try {
				Thread.sleep(SENDING_DATA_INTERVAL);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    }
    
    public void _showInUI(String mContent){
    	txt_content.setText(mContent);
    }
    
    public void showContent(String mContent){
    	txt_content2.setText(mContent);
    }

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
			showContent(strDecode);
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

}