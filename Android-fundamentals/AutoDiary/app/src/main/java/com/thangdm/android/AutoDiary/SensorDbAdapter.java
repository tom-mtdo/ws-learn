/*
 * ref: Notepadv1 example, android developer guide.
 */
		

package com.thangdm.android.AutoDiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SensorDbAdapter {
	// create table
	
	// accelerometer table --------------------------------------------------------
	//private static final String ACCELEROMETER_DATA_TABLE_CREATE = 
	//		"create table " + Declare.ACCELEROMETER_DATA_TABLE_NAME + " (" + Declare.KEY_ROWID + " integer primary key autoincrement, "
	//		+ Declare.KEY_ACCEX + " double, + " + Declare.KEY_ACCEY + " double, " + Declare.KEY_ACCEZ + " double, " 
	//		+ Declare.KEY_SENSOR_TIME_STAMP + " unsigned big int, " + Declare.KEY_CPU_TIME_STAMP + " unsigned big int,"
	//		+ Declare.KEY_SENT + " int);";		// mark data is sent to server or not: 0: not sent yet, 1: is sent

	private static final String ACCELEROMETER_DATA_TABLE_CREATE = 
			"create table acceData (_id integer primary key autoincrement, " + 
			"acceX double, acceY double, acceZ double, " +  
			"sensorTimeStamp unsigned big int, cpuTimeStamp unsigned big int," + 
			"sent int);";		// mark data is sent to server or not: 0: not sent yet, 1: is sent
	
	// create index
	private static final String ACCELEROMETER_DATA_TABLE_INDEX = 
			"create index sensorTimeStamp_Ind on " + Declare.ACCELEROMETER_DATA_TABLE_NAME + " (" + Declare.KEY_SENSOR_TIME_STAMP + ");";

	//gps table --------------------------------------------------------
	private static final String GPS_DATA_TABLE_CREATE = 
			"create table gpsData (_id integer primary key autoincrement, " + 
			"gpsLat double, gpsLong double, gpsAccuracy float, " + 
			"gpsSpeed float, gpsTime unsigned big int, sent int);";		// mark data is sent to server or not: 0: not sent yet, 1: is sent
	
	private static final String GPS_DATA_TABLE_INDEX = 
			"create index gpsTime_Ind on " + Declare.GPS_DATA_TABLE_NAME + " (" + Declare.KEY_GPS_TIME + ");";
	
	// activities table --------------------------------------------------------
	private static final String PRIMITIVE_ACTIVITIES_TABLE_CREATE = 
			"create table primActivities (_id integer primary key autoincrement, " +
			"code text, sensorTimeStamp unsigned big int, sent int);";		// mark data is sent to server or not: 0: not sent yet, 1: is sent

	private static final String PRIMITIVE_ACTIVITIES_TABLE_INDEX = 
			"create index sensorTimeStamp_activities_Ind on " + Declare.PRIMITIVE_ACTIVITIES_TABLE_NAME + " (" + Declare.KEY_SENSOR_TIME_STAMP + ");";

	// sending status 
	private static final int INITIAL_SENT_VALUE = 0;

	private static final String TAG = "SensorDBHelper";
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	
	private static final String DATABASE_NAME = "data";
	private static final int DATABASE_VERSION = 2;
    private final Context mCtx;
	
	private static class DatabaseHelper extends SQLiteOpenHelper{
		
		DatabaseHelper(Context context){
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(ACCELEROMETER_DATA_TABLE_CREATE);
			db.execSQL(ACCELEROMETER_DATA_TABLE_INDEX);
			//db.delete(ACCELEROMETER_DATA_TABLE_NAME, null, null);	// delete old data
			
			db.execSQL(GPS_DATA_TABLE_CREATE);
			db.execSQL(GPS_DATA_TABLE_INDEX);
			
			db.execSQL(PRIMITIVE_ACTIVITIES_TABLE_CREATE);
			db.execSQL(PRIMITIVE_ACTIVITIES_TABLE_INDEX);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS acceData");
            onCreate(db);
		}
		
	}
	
    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     * 
     * @param ctx the Context within which to work
     */
    public SensorDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }
	
    /**
     * Open the sensor database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     * 
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
    public SensorDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }
    
    /**
     * Insert one accelerometer reading the column names provided. If the data is
     * successfully stored return the new rowId for that data set, otherwise return
     * a -1 to indicate failure.
     * 
     * @param title the title of the note
     * @param body the body of the note
     * @return rowId or -1 if failed
     * 
     */
    public long storeData(AccelerometerDataPack acceDataPack) {
    	
    	ContentValues initialValues = new ContentValues();
        initialValues.put(Declare.KEY_ACCEX, acceDataPack.getAcceX());
        initialValues.put(Declare.KEY_ACCEY, acceDataPack.getAcceY());
        initialValues.put(Declare.KEY_ACCEZ, acceDataPack.getAcceZ());
        initialValues.put(Declare.KEY_SENSOR_TIME_STAMP, acceDataPack.getSensorTimeStamp());
        initialValues.put(Declare.KEY_CPU_TIME_STAMP, acceDataPack.getCpuTimeStamp());
        initialValues.put(Declare.KEY_SENT, INITIAL_SENT_VALUE);

        return mDb.insert(Declare.ACCELEROMETER_DATA_TABLE_NAME, null, initialValues);
    }

    public long storeData(GpsDataPack gpsData) {
    	
    	//String test = "insert into gpsData (gpsLat, gpsLong, gpsAccuracy, gpsSpeed, gpsTime, sent) " + 
    	//		"values (11,22,33,44,55,0);";
    	//mDb.execSQL(test);
    	
    	ContentValues initialValues = new ContentValues();
        initialValues.put( Declare.KEY_GPS_LAT, gpsData.getGpsLat() );
        initialValues.put( Declare.KEY_GPS_LONG, gpsData.getGpsLong() );
        initialValues.put( Declare.KEY_GPS_ACCURACY, gpsData.getGpsAccuracy() );
        initialValues.put( Declare.KEY_GPS_SPEED, gpsData.getGpsSpeed() );
        initialValues.put( Declare.KEY_GPS_TIME, gpsData.getGpsTime() );
        initialValues.put( Declare.KEY_SENT, INITIAL_SENT_VALUE);

        return mDb.insert(Declare.GPS_DATA_TABLE_NAME, null, initialValues);
    }
    
    public long storeData(ActivityDataPack activity) {

    	ContentValues initialValues = new ContentValues();
        initialValues.put(Declare.KEY_CODE, activity.getActivityCode());
        initialValues.put(Declare.KEY_SENSOR_TIME_STAMP, activity.getSensorTimeStamp());
        initialValues.put(Declare.KEY_SENT, INITIAL_SENT_VALUE);

        return mDb.insert(Declare.PRIMITIVE_ACTIVITIES_TABLE_NAME, null, initialValues);
    }
    
    /**
     * Unmark the data which was prepared for being sent, sent column = 0 
     * reason to unmark: transmission error
     * @return number of record updated
     */
    public int restoreSendingData(String tableName) {
        ContentValues args = new ContentValues();
        args.put(Declare.KEY_SENT, 0);

        return mDb.update(tableName, args, Declare.KEY_SENT + " = 1", null);
    }

    /**
     * Mark the data is prepared for being sent, sent column = 1 
     * set sent field to 1 for all not sent data -- should limit the number of row later
     * @return true if the note was successfully updated, false otherwise
     */
    public int prepareSendingData(String tableName) {
        ContentValues args = new ContentValues();
        args.put(Declare.KEY_SENT, 1);
        String strWhere = "";
        
        if (tableName.trim().equalsIgnoreCase(Declare.ACCELEROMETER_DATA_TABLE_NAME)) {
	        strWhere = Declare.KEY_ROWID + " in (select " + Declare.KEY_ROWID + 
	        		   " from " + Declare.ACCELEROMETER_DATA_TABLE_NAME + 
	        		   " where " + Declare.KEY_SENT + " = 0 order by " + Declare.KEY_SENSOR_TIME_STAMP + 
	        		   " limit " + Declare.ROW_NUMBER_LIMIT + ")";        
	        //return mDb.update(tableName, args, KEY_SENT + "= 0", null);
        }
        
        if (tableName.trim().equalsIgnoreCase(Declare.GPS_DATA_TABLE_NAME)) {
        	//strWhere = Declare.KEY_ROWID + " in (select _id from gpsData where sent = 0 order by gpsTime limit 10)";
	        strWhere = Declare.KEY_ROWID + " in (select " + Declare.KEY_ROWID + 
	        		   " from " + Declare.GPS_DATA_TABLE_NAME + 
	        		   " where " + Declare.KEY_SENT + " = 0 order by " + Declare.KEY_GPS_TIME + 
	        		   " limit " + Declare.ROW_NUMBER_LIMIT + ")";        
        	
        }
        
        if (tableName.trim().equalsIgnoreCase(Declare.PRIMITIVE_ACTIVITIES_TABLE_NAME)) {
	        strWhere = Declare.KEY_ROWID + " in (select " + Declare.KEY_ROWID + 
	        		   " from " + Declare.PRIMITIVE_ACTIVITIES_TABLE_NAME + 
	        		   " where " + Declare.KEY_SENT + " = 0 order by " + Declare.KEY_SENSOR_TIME_STAMP + 
	        		   " limit " + Declare.ROW_NUMBER_LIMIT + ")";        
        	
        }

        return mDb.update(tableName, args, strWhere, null);
    }
    
    /**
     * Get the prepared data for sending, sent column = 1 
     * @return cursor
     */
    public Cursor fetchSendingData(String tableName) {
    	
    	Cursor mCursor = null;
    	
        if ( tableName.trim().equalsIgnoreCase(Declare.ACCELEROMETER_DATA_TABLE_NAME)) {
        	mCursor = mDb.query(true, Declare.ACCELEROMETER_DATA_TABLE_NAME, new String[] {Declare.KEY_ACCEX, Declare.KEY_ACCEY, Declare.KEY_ACCEZ, 
        			  Declare.KEY_SENSOR_TIME_STAMP, Declare.KEY_CPU_TIME_STAMP}, Declare.KEY_SENT + "= 1", 
                	  null, null, null, Declare.KEY_SENSOR_TIME_STAMP, Declare.ROW_NUMBER_LIMIT);
        }

        if ( tableName.trim().equalsIgnoreCase(Declare.GPS_DATA_TABLE_NAME)) {
        	mCursor = mDb.query(true, Declare.GPS_DATA_TABLE_NAME, new String[] {Declare.KEY_GPS_LAT, Declare.KEY_GPS_LONG, Declare.KEY_GPS_ACCURACY, 
        			  Declare.KEY_GPS_SPEED, Declare.KEY_GPS_TIME}, Declare.KEY_SENT + "= 1", 
                	  null, null, null, Declare.KEY_GPS_TIME, Declare.ROW_NUMBER_LIMIT);
        }
        
        if ( tableName.trim().equalsIgnoreCase(Declare.PRIMITIVE_ACTIVITIES_TABLE_NAME)) {
        	mCursor = mDb.query(true, Declare.PRIMITIVE_ACTIVITIES_TABLE_NAME, new String[] {Declare.KEY_CODE, Declare.KEY_SENSOR_TIME_STAMP}, 
        			  Declare.KEY_SENT + "= 1", 
                	  null, null, null, Declare.KEY_SENSOR_TIME_STAMP, Declare.ROW_NUMBER_LIMIT);
        }
        
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        
        return mCursor;
    }
    
    /**
     * delete sent data, sent column = 1 
     * set sent field to 1 for all not sent data -- should DELETE ALL SENT DATA
     * @return true if the note was successfully updated, false otherwise
     */
    public int postSendingData(String tableName) {
        return mDb.delete(tableName, Declare.KEY_SENT + "= 1", null);
    }
    
    /**
     * delete all data from all data table of db (acceData, gpsData, primActivities) 
     * @return total records deleted
     */
    public int clearDb() {
    	int count = 0;
        
    	count = mDb.delete(Declare.ACCELEROMETER_DATA_TABLE_NAME, null, null);
    	count = count + mDb.delete(Declare.GPS_DATA_TABLE_NAME, null, null);
    	count = count + mDb.delete(Declare.PRIMITIVE_ACTIVITIES_TABLE_NAME, null, null);
        
        return count;
    }

    
    /**
     * Count the number of row in db
     * @return number of row in the db
     */
    public long countDb() {
    	Cursor c;
    	long count = -1;
    	try
    	{
    	    c = mDb.rawQuery("SELECT COUNT(*) FROM " + Declare.GPS_DATA_TABLE_NAME, null);
    	    if ( c != null ){
    	    	c.moveToFirst();
    	    	count = c.getInt(0);
    	    }
    	    
    	} catch (Exception e){}
	    
    	return count;
    }
    
    public long countDb2() {
    	Cursor c;
    	long count = -1;
    	try
    	{
    	    c = mDb.rawQuery("SELECT COUNT(*) FROM " + Declare.ACCELEROMETER_DATA_TABLE_NAME, null);
    	    if ( c != null ){
    	    	c.moveToFirst();
    	    	count = c.getInt(0);
    	    }
    	    
    	} catch (Exception e){}
	    
    	return count;
    }
    
    public long countDb3() {
    	Cursor c;
    	long count = -1;
    	try
    	{
    	    c = mDb.rawQuery("SELECT COUNT(*) FROM " + Declare.PRIMITIVE_ACTIVITIES_TABLE_NAME, null);
    	    if ( c != null ){
    	    	c.moveToFirst();
    	    	count = c.getInt(0);
    	    }
    	    
    	} catch (Exception e){}
	    
    	return count;
    }
    
    /**
     * Return a Cursor over the list of all data in the database
     * 
     * @return Cursor over all notes
     */
    public Cursor fetchAllData(String tableName) {

        return mDb.query(tableName, null, null, null, null, null, null);
    }

    
}
