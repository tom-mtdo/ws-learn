/**
 *
 * @author thang
 * Data preprocessor
 * 
 */

package com.thangdm.android.AutoDiary;

import java.io.File;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

import org.encog.neural.networks.BasicNetwork;
import org.encog.persist.EncogDirectoryPersistence;
import android.os.Environment;

public class DataPreProcessor {
	/*
	 * To change this template, choose Tools | Templates
	 * and open the template in the editor.
	 */
	
	// working (data) directory
	private File 		mPath 					= null;		
	private String		projectDirectory		= "autoDiary";
	
	private final int 	MAX_FILE_SIZE			= 20 * 1024 * 1024;	// ~20 MB
	
	// for storing sensor data
	private String 		dataFileName 			= "data.txt";					// name of file storing sensor data
	private File 		dataFile 				= null;							// file object
	private PrintWriter osData 					= null;							// output stream write to data file
	
	// for storing recognized activities
	private String 		activitiesFileName 		= "activities.txt"; 			// name of file storing recognized activities
	private File		activitiesFile			= null;							// file object 
	private PrintWriter osActivities			= null;							// output stream write to activities file
	private ActivityDataPack activityPack		= new ActivityDataPack();
	
	// for storing Gps data
	private String 		gpsFileName 			= "gpsData.txt"; 				// name of file storing gps data
	private File		gpsFile					= null;							// file object 
	private PrintWriter osGps					= null;							// output stream write to gpsData file
	
	// for data window
	private final int 	DATA_WINDOW_SIZE 		= 5; 
	private double[] 	dataWindow 				= new double[DATA_WINDOW_SIZE];		// data window of A = sprt(X^2 + Y^2 + Z^2) in bound [0, 20] then convert to [0,1] range
	private int 		currentIndex 			= 0;							// current data window index
	
	// for primActivities window
	private final int 	ACTWINDOW_SIZE			= 7;
	private final int 	ACTWINDOW_OVERLAP		= 2;							// for sliding window
	private String[] 	actWindow				= new String[ACTWINDOW_SIZE];	// window of recognized activities
	private int 		actIndex				= 0;							// current

	private final String[]	activities			= new String[] {"00", "01", "10", "11"};
	private int[]		actCount				= new int[] {0, 0, 0, 0};		// for calculating dominant activity
	
	// for loading neural network
	private String 		nnFileName 				= "WRDS.eg";  					// name of file containing trained Neural Network
	private File 		nnFile					= null;							// file object
	private BasicNetwork nnWRDS 				= null;							// loaded network
	
	//reduce gps sample rate
	private long lastGpsTimeLong 			= 0;
	
	
 

	// -------------------------------------------------------------------------
	// Just write to gpsData at the moment
	// -------------------------------------------------------------------------

	public void processGpsData(SensorDbAdapter sensorDb, GpsDataPack gpsDataPack){
		
		long time_long = gpsDataPack.getGpsTime();
		
		// reduce samples rates to 1 sample per 5 seconds
		if ( time_long - lastGpsTimeLong < 5000 ) {
			return; 
		}
		
		lastGpsTimeLong = time_long;
		
		// store in sqlite
		//sensorDb.storeData(gpsDataPack);
		// write to text file for backup
		writeToFile(gpsDataPack);
		
	}
	
	// -------------------------------------------------------------------------
	// pre-process:
	// - detect primitive activities: walk, run, driving, still
	// - write the result with cpuTimeStamp of last sample in data window to file
	// -------------------------------------------------------------------------
	// calculate euclide distant A = sprt(X^2 + Y^2 + Z^2) in bound [0, 20]
	// package data to window of five A
	// calculate min, max and convert to range [0,1] by dividing to 20
	// load NN
	// set input
	// calculate network
	// get output
	// -------------------------------------------------------------------------
	//public String processData(DataPack dataPackfloat mAcceX, float mAcceY, float mAcceZ, long sensorTimeStamp){
	public String processAccelerometerData(SensorDbAdapter sensorDb, AccelerometerDataPack acceDataPack){

		// store to SQLite - not need?
		// sensorDb.storeData(acceDataPack);
		// store to text file for backup
		writeToFile(acceDataPack);
		
		// recognize activity:
		// calculate euclide distant A = sprt(X^2 + Y^2 + Z^2) in bound [0, 20] then convert to [0,1] range
		double a = Math.sqrt(Math.pow(acceDataPack.getAcceX(), 2) + Math.pow(acceDataPack.getAcceY(), 2) + Math.pow(acceDataPack.getAcceZ(), 2));
		if (a > 20 ){ a = 20;}
		a = a/20;
		
		// package data to window of five As
		dataWindow[currentIndex] = a;
		
		// activity recognized
        String strActivity = "";
        // result of window reasoning
        String dominantAct = "";	// just for test
        
		// if window is loaded all five samples then calculate NN 
		if(currentIndex == (DATA_WINDOW_SIZE - 1)){
			// construct network input = (min, max)
			double[] wMinMax = minMaxAverage(dataWindow);
			
			if (wMinMax == null){return "Recognized activity: null";}
			
			// set network input and calculate
	        double[] nnInput = new double[2];
	        nnInput[0] = wMinMax[0];			// min
	        nnInput[1] = wMinMax[1];			// max
	        double[] nnOutput = new double[2];
	        nnWRDS.compute(nnInput, nnOutput);

	        // get nn output
	        for (double d: nnOutput){
	        	strActivity += Math.round(d);
	        }

	        dominantAct = windowReasoning(strActivity.trim(),acceDataPack.getCpuTimeStamp(), sensorDb);
	        /*
	        activityPack.setActivityCode(strActivity.trim());
	        // suppose sensortimestamp is close to the system time when processing the sensor data
	        activityPack.setSensorTimeStamp(acceDataPack.getCpuTimeStamp());
	        
	        // store in sqlite 
	        sensorDb.storeData(activityPack);
	        // write activities to file
	        writeToFile(activityPack);
	        */
	        
	        // reset index
	        currentIndex = 0;
		
		}else{	// if window is nor fully loaded
			// increase index to load next samples
			currentIndex++;
			// no activity is recognized
			strActivity += "null";
			dominantAct += "null";
		}
		
		//return strActivity;
		return dominantAct;
	}
	

	
	/* -------------------------------------------------------------------------
	 *
	 * -------------------------------------------------------------------------
	 */
    public String windowReasoning(String actCode, long time, SensorDbAdapter sensorDb){
		// package recognized activities to window of 7
		actWindow[actIndex] = actCode;
		
		// activity recognized
        String act = "";
        
		// if window is loaded all 7 samples then calculate dominant activity 
		if(actIndex == (ACTWINDOW_SIZE - 1)){
			// calculate dominant activity
			act = dominantAct(time);
			
	        //activityPack.setActivityCode(strActivity.trim());
	        // suppose sensortimestamp is close to the system time when processing the sensor data
	        //activityPack.setSensorTimeStamp(time);

	        // store result
			storeResult(sensorDb);

			// slide window
			slideWindow();
		
		}else{	// if window is nor fully loaded
			// increase index to load next samples
			actIndex++;
			// no activity is recognized
			act += "null";
		}
		
		return act;
    }
    
    // caculate the dominant activity which appears most in the activities window
    public String dominantAct(long time){
    	int maxCount	= 0;	// appearance time of dominant activity
    	int maxIndex 	= 0;	// dominant activity index
    	
    	for (int i = 0; i < actWindow.length; i++){
    		for (int j = 0; j < activities.length; j++){
    			if (actWindow[i].trim().equalsIgnoreCase(activities[j].trim())){
    				actCount[j]++;
    				if (maxCount < actCount[j]){
    					maxCount = actCount[j];
    					maxIndex = j;
    				}
    				break;
    			}
    		}
    	}
    	
    	// set current activity to the dominant one
        activityPack.setActivityCode(activities[maxIndex]);
        // suppose sensortimestamp is close to the system time when processing the sensor data
        activityPack.setSensorTimeStamp(time);
        
        return activities[maxIndex];
    }
    
    public void slideWindow(){
    	for (int i = 0; i < ACTWINDOW_OVERLAP; i++){
    		actWindow[i] = actWindow[ACTWINDOW_SIZE - ACTWINDOW_OVERLAP + i]; 
    	}
		actIndex = ACTWINDOW_OVERLAP;
    }
    
    public void storeResult(SensorDbAdapter sensorDb){
        // store in sqlite 
        //sensorDb.storeData(activityPack);
        // write activities to file
        writeToFile(activityPack);
    }
	
	// -------------------------------------------------------------------------
	// return min, max, average of an input array
	// result[0] = min, result[1] = max, result[2] = average
	// -------------------------------------------------------------------------
    public double[] minMaxAverage(double[] arr){
        double[] result = null;
        
        // check input null
        if (arr.length < 1){return result;
        } else{ 
        	result = new double[3];
        }
        
        result[0]       = arr[0];	// init min
        result[1]       = arr[0];	// init max
        result[2]   	= arr[0];	// init average
        double sum      = arr[0];	// init sum
        
        for (int i = 1; i < arr.length; i++) {
            if (result[0] > arr[i]){ result[0] = arr[i];}
            if (result[1] < arr[i]){ result[1] = arr[i];}
            sum += arr[i];
        }
        
        result[2] = sum/(arr.length);

        return result;
    }
	
    // -------------------------------------------------------------------------
	// just write sensor data to file
    // -------------------------------------------------------------------------
	public void writeToFile(AccelerometerDataPack accePack){
		String mData = "";
		// acceX, acceY, acceZ, sensorTimeStamp, cpuTimeStamp
		mData += accePack.getAcceX() + "\t" + accePack.getAcceY() + "\t" + accePack.getAcceZ() + "\t" 
				+ accePack.getSensorTimeStamp() + "\t" + accePack.getCpuTimeStamp();
    	osData.println(mData);
    	osData.flush();
    	
    	// if file big then backup to new file
    	if ( dataFile.length() > MAX_FILE_SIZE ) {
    		backupDataFile();
    	}
	}
	
	public void backupDataFile(){
		synchronized (this){
    		// close datfile
    		osData.close();
    		// rename datafile to "data + time in second + .txt"
    		String copyFileName = dataFileName.substring( 0, dataFileName.indexOf(".txt") ) + (System.currentTimeMillis() + "").substring(0, 10) + ".txt";
    		File copyFile = new File(mPath, copyFileName );
    		dataFile.renameTo(copyFile);
    		// open new datafile
    		dataFile = new File(mPath, dataFileName);
    		try{
    	        // open output stream to write to files
    			osData 			= new PrintWriter(new FileOutputStream(dataFile, true));
    		}
            catch(FileNotFoundException e){
            	System.out.println("Error opening the file: " + dataFileName);
            }
		}
	}
	
    // -------------------------------------------------------------------------
	// just test content to file
    // -------------------------------------------------------------------------
	//public void writeToDataFile(String content){
    //	osData.println(content);
	//}
	
    // -------------------------------------------------------------------------
	// just write gps data to file
    // -------------------------------------------------------------------------
	public void writeToFile(GpsDataPack gpsPack){
		String mData = "";
		// lat, long, accuracy, speed, time
		mData += gpsPack.getGpsLat() + "\t" + gpsPack.getGpsLong() + "\t" + gpsPack.getGpsAccuracy() + "\t" 
				+ gpsPack.getGpsSpeed() + "\t" + gpsPack.getGpsTime();
    	osGps.println(mData);
	}

    // -------------------------------------------------------------------------
	// just write activity data pack to file
    // -------------------------------------------------------------------------
	public void writeToFile(ActivityDataPack activityPack){
		String mData = "";
		// code, sensorTimeStamp
		mData += activityPack.getActivityCode() + "\t" + activityPack.getSensorTimeStamp();
    	osActivities.println(mData);
	}

	// -------------------------------------------------------------------------
	// prepare file for working:
	// open file for:
	// 	- store raw sensor data
	//	- store recognized activities
	// load trained NN
	// -------------------------------------------------------------------------
	public boolean openDataFile(){
		
		boolean result = false;
		
		// check sdcard ready
		//String state = Environment.getExternalStorageState();
		//if (!Environment.MEDIA_MOUNTED.equals(state)) {
		    // We can not read and write the media
		    //return result;
		//}

		// get data directory
		mPath = Environment.getExternalStoragePublicDirectory(projectDirectory);

		// raw sensor file
		dataFile = new File(mPath, dataFileName);

		// recognized activities file
		activitiesFile = new File(mPath, activitiesFileName);
		
		// gpsData file
		gpsFile = new File(mPath, gpsFileName);
		
		// load NN
     	nnFile = new File(mPath, nnFileName);
     	nnWRDS = (BasicNetwork)EncogDirectoryPersistence.loadObject(nnFile);
		
		// Make sure the directory exists.
        mPath.mkdirs();

		try{
	        // open output stream to write to files
			osData 			= new PrintWriter(new FileOutputStream(dataFile			, true));
			osActivities 	= new PrintWriter(new FileOutputStream(activitiesFile	, true));
			osGps			= new PrintWriter(new FileOutputStream(gpsFile			, true));
			result = true;
		}
        catch(FileNotFoundException e){
        	System.out.println("Error opening the file: " + dataFileName);
        }

		return result;
	}

    
    // -------------------------------------------------------------------------
    // close all files
    // -------------------------------------------------------------------------
	public boolean closeDataFile(){

		boolean result = true;
        try{
        	osData.close();
        	osActivities.close();
        	osGps.close();
        }
        catch(Exception e){
        	System.out.println("Error: cannot close data file: " + dataFileName);
        	result = false;
        	return result;
            //System.exit(0);
        }
        return result;
	}
	
	//-------------------------------------------------------------------------------------------------
	// test to write to a text file
	/*
	public String testFile() {
		// TODO Auto-generated method stub
		String strResult = "Test result:";
		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
		    // We can read and write the media
		    mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
		    // We can only read the media
		    mExternalStorageAvailable = true;
		    mExternalStorageWriteable = false;
		} else {
		    // Something else is wrong. It may be one of many other states, but all we need
		    //  to know is we can neither read nor write
		    mExternalStorageAvailable = mExternalStorageWriteable = false;
		}
		
		// get path
		File fPath = Environment.getExternalStoragePublicDirectory("autoDiary");
		File mFile = new File(fPath, dataFileName);
		
		
		
		boolean mMkdir = false;
		
		try{
	        mMkdir = fPath.mkdirs();
	        osData = new PrintWriter(new FileOutputStream(mFile, true));
	        osData.println("Test");
	        osData.close();
		}
        catch(FileNotFoundException e){
        	System.out.println("Error opening the file: " + dataFileName);
        }
		
		strResult += "\t State: " + state;
		strResult += "\t Available: " + mExternalStorageAvailable + "\t Writeable: " + mExternalStorageWriteable;
		strResult += "\t Music Dir: " + fPath.getAbsolutePath();
		strResult += "\t Make Dir: " + mMkdir;
		
		return strResult;
	}
	*/

}
