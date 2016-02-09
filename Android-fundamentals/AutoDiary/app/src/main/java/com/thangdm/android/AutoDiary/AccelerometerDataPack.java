package com.thangdm.android.AutoDiary;

public class AccelerometerDataPack {

	// accelerometer data
	private float 	acceX			= 0;
    private float 	acceY			= 0;
    private float 	acceZ			= 0;
    private long 	sensorTimeStamp	= 0;
    private long 	cpuTimeStamp	= 0;
    
    // set functions
    public void setAcceX(float x){ this.acceX = x; }
    public void setAcceY(float y){ this.acceY = y; }
    public void setAcceZ(float z){ this.acceZ = z; }
    public void setSensorTimeStamp(long t){ this.sensorTimeStamp = t; }
    public void setCpuTimeStamp(long t){ this.cpuTimeStamp = t; }
    
    
    // get functions
    public float getAcceX(){ return this.acceX; }
    public float getAcceY(){ return this.acceY; }
    public float getAcceZ(){ return this.acceZ; }
    public long getSensorTimeStamp(){ return this.sensorTimeStamp; }
    public long getCpuTimeStamp(){ return this.cpuTimeStamp; }

}
