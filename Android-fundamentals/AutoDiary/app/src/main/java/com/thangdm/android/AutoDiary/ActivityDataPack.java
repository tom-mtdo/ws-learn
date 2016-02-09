package com.thangdm.android.AutoDiary;

public class ActivityDataPack {
    // gps data
    private String 	code 		= "";
    private long 	sensorTimeStamp 	= 0;

    // set functions    
    public void setActivityCode(String code) { 
    	if (code != null) {
    		this.code = code.trim(); 
    	}
    }
    public void setSensorTimeStamp(long time){	this.sensorTimeStamp = time; }

    // get functions    
    public String getActivityCode() { return this.code; }
    public long getSensorTimeStamp() { return this.sensorTimeStamp; }

}
