package com.thangdm.android.AutoDiary;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
//import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.database.Cursor;
import android.database.SQLException;


public class FileUploadClient {
	
	public String uploadData(String tableName, Cursor mCursor) throws Exception{
       	
		HttpClient httpclient = new DefaultHttpClient();
        String responseBody = null;
        
        try {
            //HttpGet httpget = new HttpGet("http://homepage.cs.latrobe.edu.au/mtdo/servlet/FileUploadServlet");
        	if (mCursor != null) {
            	
        		mCursor.moveToFirst();
        		int rowCount = 1;
        		List<NameValuePair> qparams = new ArrayList<NameValuePair>();

        		// which table sent
        		//qparams.add(new BasicNameValuePair("tableName", Declare.ACCELEROMETER_DATA_TABLE_NAME));
        		//qparams.add(new BasicNameValuePair("tableName", tableName));
        		String mData = "";
        		
            	// prepare post data, only post first ten rows, if more just skip the left data
                //while ( (!mCursor.isAfterLast()) && (rowCount <= 10) ){
        		
        		// prepare parameter pairs holding data to post
        		// if accelerometer table
        		if (tableName.equalsIgnoreCase(Declare.ACCELEROMETER_DATA_TABLE_NAME)) {
        			
        			// sent table name
        			qparams.add(new BasicNameValuePair("tableName", Declare.ACCELEROMETER_DATA_TABLE_NAME));
        			
	                while ( (!mCursor.isAfterLast()) && (rowCount <= Integer.parseInt(Declare.ROW_NUMBER_LIMIT)) ){                	
	                	
	            		mData = "";
	            		// acceX, acceY, acceZ, sensorTimeStamp, cpuTimeStamp
	            		mData += mCursor.getDouble(0) + "\t" + mCursor.getDouble(1) + "\t" + mCursor.getDouble(2) + "\t" 
	            				+ mCursor.getLong(3) + "\t" + mCursor.getLong(4);
	            		qparams.add(new BasicNameValuePair("row" + rowCount, mData));
	            		
	            		rowCount++;
	            		mCursor.moveToNext();
	            	}
        		}
                
        		// if gpsData table
        		if (tableName.equalsIgnoreCase(Declare.GPS_DATA_TABLE_NAME)) {
        			
        			// sent table name
        			qparams.add(new BasicNameValuePair("tableName", Declare.GPS_DATA_TABLE_NAME));

	                while ( (!mCursor.isAfterLast()) && (rowCount <= Integer.parseInt(Declare.ROW_NUMBER_LIMIT)) ){                	
	                	
	            		mData = "";
	            		// gpsLat, gpsLong, gpsAccuracy, gpsSpeed, gpsTime
	            		mData += mCursor.getDouble(0) + "\t" + mCursor.getDouble(1) + "\t" + mCursor.getFloat(2) + "\t" 
	            				+ mCursor.getFloat(3) + "\t" + mCursor.getLong(4);
	            		qparams.add(new BasicNameValuePair("row" + rowCount, mData));
	            		
	            		rowCount++;
	            		mCursor.moveToNext();
	            	}
        		}

        		// if primActivities table
        		if (tableName.equalsIgnoreCase(Declare.PRIMITIVE_ACTIVITIES_TABLE_NAME)) {

        			// sent table name
        			qparams.add(new BasicNameValuePair("tableName", Declare.PRIMITIVE_ACTIVITIES_TABLE_NAME));

	                while ( (!mCursor.isAfterLast()) && (rowCount <= Integer.parseInt(Declare.ROW_NUMBER_LIMIT)) ){                	
	                	
	            		mData = "";
	            		// code, sensorTimeStamp
	            		mData += mCursor.getString(0) + "\t" + mCursor.getLong(1);
	            		qparams.add(new BasicNameValuePair("row" + rowCount, mData));
	            		
	            		rowCount++;
	            		mCursor.moveToNext();
	            	}
        		}
        		
        		// send data
                //URI uri = URIUtils.createURI("http", "homepage.cs.latrobe.edu.au", -1, "/mtdo/servlet/FileUploadServlet",
                //URI uri = URIUtils.createURI("http", "49.3.5.182", 8080, "/autodiary/autodiary", 
                URI uri = URIUtils.createURI("http", Declare.web_server_host, 8080, "/autodiary/autodiary",                		
                URLEncodedUtils.format(qparams, "UTF-8"), null);
	            //HttpGet httpget = new HttpGet(uri);
	            HttpPost httpPost = new HttpPost(uri);
	           
	            //System.out.println("executing request " + httpget.getURI());
	            System.out.println("executing request " + httpPost.getURI());
	
	            // Create a response handler
	            ResponseHandler<String> responseHandler = new BasicResponseHandler();
	            //responseBody = httpclient.execute(httpget, responseHandler);
	            responseBody = httpclient.execute(httpPost, responseHandler);            
	            //System.out.println("----------------------------------------");
	            //System.out.println(responseBody);
	            //System.out.println("----------------------------------------");
        	}
        	
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
        return responseBody;
    }
	
    public String uploadData() throws Exception{
       	HttpClient httpclient = new DefaultHttpClient();
        String responseBody = null;
        try {
            //HttpGet httpget = new HttpGet("http://homepage.cs.latrobe.edu.au/mtdo/servlet/FileUploadServlet");

            List<NameValuePair> qparams = new ArrayList<NameValuePair>();
            qparams.add(new BasicNameValuePair("q", "data Upload client"));
            qparams.add(new BasicNameValuePair("oq", null));
            //URI uri = URIUtils.createURI("http", "homepage.cs.latrobe.edu.au", -1, "/mtdo/servlet/FileUploadServlet", 
            URI uri = URIUtils.createURI("http", "49.3.5.182", 8080, "/autodiary/autodiary",
                URLEncodedUtils.format(qparams, "UTF-8"), null);
            //HttpGet httpget = new HttpGet(uri);
            HttpPost httpPost = new HttpPost(uri);
           
            //System.out.println("executing request " + httpget.getURI());
            System.out.println("executing request " + httpPost.getURI());

            // Create a response handler
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            //responseBody = httpclient.execute(httpget, responseHandler);
            responseBody = httpclient.execute(httpPost, responseHandler);            
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            System.out.println("----------------------------------------");
            

        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
        return responseBody;
    }
    
    /**
     * 
     *  for testing only
     * 
     * @throws SQLException if note could not be found/retrieved
     */
    public String sendDataStr(SensorDbAdapter mSensorDb) throws SQLException {
    	Cursor mCursor = null;
    	String response1 = "null";
    	String response2 = "null";
    	
    	// send accelerometer data --------------------------------------------------------------
    	/* not need?
    	// prepare sending data
    	int dataCount = mSensorDb.prepareSendingData(Declare.ACCELEROMETER_DATA_TABLE_NAME);
    	
    	// if there is data
    	if (dataCount > 1) {
	    	// get the sending data
	    	mCursor = mSensorDb.fetchSendingData(Declare.ACCELEROMETER_DATA_TABLE_NAME);
	        
	        // send data to web server
	        if (mCursor != null) {
	        	mCursor.moveToFirst();
	        	
	        	try {
					String response = uploadData(Declare.ACCELEROMETER_DATA_TABLE_NAME, mCursor);
	        		
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        
	        // delete sent data
			dataCount = mSensorDb.postSendingData(Declare.ACCELEROMETER_DATA_TABLE_NAME);
    	}
		*/
    	
    	// send primitive activities ---------------------------------------------------
    	// prepare sending data
    	int dataCount2 = mSensorDb.prepareSendingData(Declare.PRIMITIVE_ACTIVITIES_TABLE_NAME);
    	// if there is data
    	if (dataCount2 > 1) {
	    	// get the sending data
	    	mCursor = mSensorDb.fetchSendingData(Declare.PRIMITIVE_ACTIVITIES_TABLE_NAME);
	        
	        // send data to web server
	        if (mCursor != null) {
	        	mCursor.moveToFirst();
	        	
	        	try {
					response2 = uploadData(Declare.PRIMITIVE_ACTIVITIES_TABLE_NAME, mCursor);
	        		
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        
	        // if sent OK
	        if(response2.equalsIgnoreCase(Declare.SEND_RESPONSE_OK)){
		        // delete sent data
				dataCount2 = mSensorDb.postSendingData(Declare.PRIMITIVE_ACTIVITIES_TABLE_NAME);
			
			// if sent error
	        }else{
	        	dataCount2 = 0;
		        // restorePreparedData
		        mSensorDb.restoreSendingData(Declare.PRIMITIVE_ACTIVITIES_TABLE_NAME);
	        }
    	}

    	// send gps data ---------------------------------------------------
    	// prepare sending data
    	//int dataCount = mSensorDb.prepareSendingData(ACCELEROMETER_DATA_TABLE_NAME);
    	int dataCount1 = mSensorDb.prepareSendingData(Declare.GPS_DATA_TABLE_NAME);
    	// if there is no data
    	if (dataCount1 > 1) {
	    	// get the sending data
	    	mCursor = mSensorDb.fetchSendingData(Declare.GPS_DATA_TABLE_NAME);
	        
	        // send data to web server
	        if (mCursor != null) {
	        	mCursor.moveToFirst();
	        	
	        	try {
					response1 = uploadData(Declare.GPS_DATA_TABLE_NAME, mCursor);
	        		
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
			
	        // if sent OK
	        if(response1.equalsIgnoreCase(Declare.SEND_RESPONSE_OK)){
		        // delete sent data
				dataCount1 = mSensorDb.postSendingData(Declare.GPS_DATA_TABLE_NAME);
			
			// if sent error
	        }else{
	        	dataCount1 = 0;
		        // restorePreparedData
		        mSensorDb.restoreSendingData(Declare.GPS_DATA_TABLE_NAME);
	        }

    	}

    	//return dataCount + dataCount1 + dataCount2; 
    	return response1 + response2;
    	
		// all the data will be written in a log, for transfer, reference later if need
		// as stream data, if data is not sent then still delete.
		// if we want transmit all data, no lost, then put return mSensorDb.postSendingData(ACCELEROMETER_DATA_TABLE_NAME); 
		// in try catch together with uploadData(mCursor); so if transfer error -> not delete data
		// and in mSensorDb.prepareSendingData() should check is there any row with sent = 1 (ready for transfer) or not
		// if there is any, sent it first
		// if there is not, prepare 10 rows to transfer as normal 
    }
    
    /**
     * Test try to query stored data
     * Return a Cursor positioned at the set that matches the given sent value
     * If found
     * @throws SQLException if note could not be found/retrieved
     */
    public int sendData(SensorDbAdapter mSensorDb) throws SQLException {
    	Cursor mCursor = null;
    	String response1 = "";
    	String response2 = "";
    	
    	
    	// send accelerometer data --------------------------------------------------------------
    	/* not need?
    	// prepare sending data
    	int dataCount = mSensorDb.prepareSendingData(Declare.ACCELEROMETER_DATA_TABLE_NAME);
    	
    	// if there is data
    	if (dataCount > 1) {
	    	// get the sending data
	    	mCursor = mSensorDb.fetchSendingData(Declare.ACCELEROMETER_DATA_TABLE_NAME);
	        
	        // send data to web server
	        if (mCursor != null) {
	        	mCursor.moveToFirst();
	        	
	        	try {
					String response = uploadData(Declare.ACCELEROMETER_DATA_TABLE_NAME, mCursor);
	        		
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        
	        // delete sent data
			dataCount = mSensorDb.postSendingData(Declare.ACCELEROMETER_DATA_TABLE_NAME);
    	}
		*/
    	
    	// send primitive activities ---------------------------------------------------
    	// prepare sending data
    	int dataCount1 = mSensorDb.prepareSendingData(Declare.PRIMITIVE_ACTIVITIES_TABLE_NAME);
    	// if there is data
    	if (dataCount1 > 1) {
	    	// get the sending data
	    	mCursor = mSensorDb.fetchSendingData(Declare.PRIMITIVE_ACTIVITIES_TABLE_NAME);
	        
	        // send data to web server
	        if (mCursor != null) {
	        	mCursor.moveToFirst();
	        	
	        	try {
					response1 = uploadData(Declare.PRIMITIVE_ACTIVITIES_TABLE_NAME, mCursor);
					response1 = response1.trim(); // remove new line character
	        		
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        
	        // delete sent data
			//dataCount1 = mSensorDb.postSendingData(Declare.PRIMITIVE_ACTIVITIES_TABLE_NAME);

			
	        // if sent OK
	        if(response1.equalsIgnoreCase(Declare.SEND_RESPONSE_OK)){
		        // delete sent data
				dataCount1 = mSensorDb.postSendingData(Declare.PRIMITIVE_ACTIVITIES_TABLE_NAME);
			
			// if sent error
	        }else{
	        	dataCount1 = 0;
		        // restorePreparedData
		        mSensorDb.restoreSendingData(Declare.PRIMITIVE_ACTIVITIES_TABLE_NAME);
	        }
    	}

    	// send gps data ---------------------------------------------------
    	// prepare sending data
    	//int dataCount = mSensorDb.prepareSendingData(ACCELEROMETER_DATA_TABLE_NAME);
    	int dataCount2 = mSensorDb.prepareSendingData(Declare.GPS_DATA_TABLE_NAME);
    	// if there is no data
    	if (dataCount2 > 1) {
	    	// get the sending data
	    	mCursor = mSensorDb.fetchSendingData(Declare.GPS_DATA_TABLE_NAME);
	        
	        // send data to web server
	        if (mCursor != null) {
	        	mCursor.moveToFirst();
	        	
	        	try {
					response2 = uploadData(Declare.GPS_DATA_TABLE_NAME, mCursor);
					response2 = response2.trim();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }

	        // delete sent data
			dataCount2 = mSensorDb.postSendingData(Declare.GPS_DATA_TABLE_NAME);
	        // WHY????????????????????????????????????????????????????????????????? not delete sent data
			// error???? no
			
	        /*
	        // if sent OK
	        if(response2.equalsIgnoreCase(Declare.SEND_RESPONSE_OK)){
		        // delete sent data
				dataCount2 = mSensorDb.postSendingData(Declare.GPS_DATA_TABLE_NAME);
			
			// if sent error
	        }else{
	        	dataCount2 = 0;
		        // restorePreparedData
		        mSensorDb.restoreSendingData(Declare.GPS_DATA_TABLE_NAME);
	        }
	        */

    	}

    	//return dataCount + dataCount1 + dataCount2; 
    	return dataCount1 + dataCount2;
    	
		// all the data will be written in a log, for transfer, reference later if need
		// as stream data, if data is not sent then still delete.
		// if we want transmit all data, no lost, then put return mSensorDb.postSendingData(ACCELEROMETER_DATA_TABLE_NAME); 
		// in try catch together with uploadData(mCursor); so if transfer error -> not delete data
		// and in mSensorDb.prepareSendingData() should check is there any row with sent = 1 (ready for transfer) or not
		// if there is any, sent it first
		// if there is not, prepare 10 rows to transfer as normal 
    }
}


