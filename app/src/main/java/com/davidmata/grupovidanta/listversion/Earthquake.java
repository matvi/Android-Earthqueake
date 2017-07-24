package com.davidmata.grupovidanta.listversion;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by davidmata on 11/10/2016.
 */

public class Earthquake {
    private Double mag;
    private String place,mapURL;
    private long timeInMilliseconds;

    public Earthquake(Double mag, String place, long time, String mapURL){
        this.mag =mag;
        this.place = place;
        this.timeInMilliseconds = time;
        this.mapURL = mapURL;
    }

    public Double getMag(){
        return this.mag;
    }

    public String getPlace(){
        return this.place;
    }
    public long getTimeInMilliseconds(){
        return this.timeInMilliseconds;
    }

    public String getMapURL(){
        return this.mapURL;
    }


}
