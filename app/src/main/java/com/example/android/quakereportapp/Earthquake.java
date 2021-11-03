package com.example.android.quakereportapp;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Earthquake {

    private double mMag;
    private String mPlace;
    private long mTimeInMilliseconds;
    private String mUrl;


    public Earthquake(double magnitude, String location, long timeInMilliseconds, String url) {
        mMag = magnitude;
        mPlace = location;
        mTimeInMilliseconds = timeInMilliseconds;
        mUrl = url;
    }


    public double getMag() {
        return mMag;
    }

    public String getPlace() {
        return mPlace;
    }

    public long getTimeInMilliseconds() { return mTimeInMilliseconds; }

    public String geturl(){return mUrl;}

}