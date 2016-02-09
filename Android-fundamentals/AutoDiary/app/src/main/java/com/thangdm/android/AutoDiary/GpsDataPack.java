package com.thangdm.android.AutoDiary;

public class GpsDataPack {
    // gps data
    private double 	gpsLat 			= 0;
    private double 	gpsLong 		= 0;
    private float 	gpsAccuracy 	= 0;
    private float 	gpsSpeed 		= 0;
    private long 	gpsTime 		= 0;

    // set functions    
    public void setGpsLat(double l) 	{ this.gpsLat = l; }
    public void setGpsLong(double l) 	{ this.gpsLong = l; }
    public void setGpsAccuracy(float a) { this.gpsAccuracy = a; }
    public void setGpsSpeed(float s)	{ this.gpsSpeed = s; }
    public void setGpsTime(long t)		{ this.gpsTime = t; }

    // get functions    
    public double getGpsLat()		{ return this.gpsLat; }
    public double getGpsLong()		{ return this.gpsLong; }
    public float getGpsAccuracy()	{ return this.gpsAccuracy; }
    public float getGpsSpeed()		{ return this.gpsSpeed; }
    public long getGpsTime()		{ return this.gpsTime; }

}
