package com.example.rtalukder.hw1;

/**
 * Created by Cody on 9/18/2016.
 */

public class putData {
    private long timestamp;
    private String type;
    private double x;
    private double y;
    private double z;

    public putData(long timestamp, String type, double x, double y, double z) {
        this.timestamp = timestamp;
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    public void setType(String type) {
        this.type = type;
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

    public String toString()
    {
        return "t=" + timestamp + ", type= " + type + ", x=" + x + ", y=" + y + ", z=" + z;
    }
}
