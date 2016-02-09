package com.thangdm.android.AutoDiary;

public class Declare {
	
	// global variables       ================================================================
	// web server name/address 
	public static String 	   web_server_host = "49.3.5.182";
	// Sending response
	public static final String SEND_RESPONSE_OK 	= "OK";
	public static final String SEND_RESPONSE_FALSE 	= "FALSE";
	
	// data base declaration =================================================================
	// limit number of rows to be sent
	public static final String ROW_NUMBER_LIMIT = "10";
	
	// share between tables
	public static final String KEY_ROWID = "_id";
	public static final String KEY_SENT = "sent";
	public static final String KEY_SENSOR_TIME_STAMP = "sensorTimeStamp";
	public static final String KEY_CPU_TIME_STAMP = "cpuTimeStamp";	
	
	// accelerometer data table --------------------------------------------------------------
	public static final String ACCELEROMETER_DATA_TABLE_NAME = "acceData";
	public static final String KEY_ACCEX = "acceX";
	public static final String KEY_ACCEY = "acceY";
	public static final String KEY_ACCEZ = "acceZ";

	// gps data table -----------------------------------------------------------------------
	public static final String GPS_DATA_TABLE_NAME 	= "gpsData";
	public static final String KEY_GPS_LAT 			= "gpsLat";
	public static final String KEY_GPS_LONG 		= "gpsLong";
	public static final String KEY_GPS_ACCURACY 	= "gpsAccuracy";
	public static final String KEY_GPS_SPEED 		= "gpsSpeed";
	public static final String KEY_GPS_TIME 		= "gpsTime";
	

	// primitive activities data table ------------------------------------------------------
	public static final String PRIMITIVE_ACTIVITIES_TABLE_NAME = "primActivities";
	public static final String KEY_CODE = "code";
	

	/** tables describing in SQLite database ================================================
	 * 
	 * accelerometer table:
	 * acceX 			double
	 * acceY			double
	 * acceZ			double
	 * sensorTimeStamp	unsigned big int
	 * cpuTimeStamp		unsigned big int
	 * sent				int
	 *
	 * gpsData table:
	 * gpsLat			double
	 * gpsLong			double
	 * gpsAccuracy		float
	 * gpsSpeed			float
	 * gpsTime			unsigned big int
	 * sent				int
	 *
	 * primActivities:
	 * code				text
	 * sensorTimeStamp	unsigned big int
	 * sent				int
	 * 
	 */
}
