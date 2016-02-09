package com.example.mtdo.managethread;

/**
 * Created by thangdo on 28/12/2015.
 */
public class AccelerometerModel {
    double x;
    double y;
    double z;

    @Override
    public String toString() {
        return "AccelerometerModel{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
}
