package com.davidmata.grupovidanta.listversion.implementation;

import android.os.AsyncTask;
import android.util.Log;

import com.davidmata.grupovidanta.listversion.Earthquake;
import com.davidmata.grupovidanta.listversion.interfaces.EarthquakeInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by davidmata on 08/11/2016.
 */

public class EarthquakesJson extends AsyncTask<String,Void,List<Earthquake>>implements EarthquakeInterface {
    private static String URL_TOP_10_MOST_RECENT_EARTHQUEAKE_ABOVE_6 = "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    @Override
    public List<Earthquake> getEarthqueakes() {
        try {
            return  this.execute(URL_TOP_10_MOST_RECENT_EARTHQUEAKE_ABOVE_6).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected List<Earthquake> doInBackground(String... params) {
        URL url;
        HttpURLConnection httpURLConnection = null;
        StringBuilder json = new StringBuilder();
        String line="";
        try {
            url = new URL(URL_TOP_10_MOST_RECENT_EARTHQUEAKE_ABOVE_6);
            httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            while ((line=bufferedReader.readLine())!= null){
                json.append(line);
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        } finally {
            if(httpURLConnection!=null){
                httpURLConnection.disconnect();
            }

        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<Earthquake> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject root = new JSONObject(json.toString());
            JSONArray features = root.getJSONArray("features");

            for (int i = 0; i < features.length(); i++) {
                JSONObject earthquake = features.getJSONObject(i);
                JSONObject properties = earthquake.getJSONObject("properties");
                double mag = properties.getDouble("mag");
                String place = properties.getString("place");
                long time = properties.getLong("time");
                String mapURL = properties.getString("url");
                earthquakes.add(new Earthquake(mag, place, time, mapURL));
            }


        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

    @Override
    protected void onPostExecute(List<Earthquake> earthquakes) {
        super.onPostExecute(earthquakes);
    }
}
