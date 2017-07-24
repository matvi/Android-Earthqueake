package com.davidmata.grupovidanta.listversion.GlobalPack;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.davidmata.grupovidanta.listversion.R;

import javax.microedition.khronos.opengles.GL;

/**
 * Created by davidmata on 11/11/2016.
 */

public class Global {

    private static String URL_EARTHQUEAKE = "http://earthquake.usgs.gov/fdsnws/event/1/query";
    private static final String QUERY_PARAMETER_FORMAT ="format";
    private static final String QUERY_PARAMETER_LIMIT ="limit";
    private static final String QUERY_PARAMETER_MINMAG ="minmag";
    private static final String QUERY_PARAMETER_ORDERBY ="orderby";


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public static String getURLEarthqueake(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String [] orderBy = context.getResources().getStringArray(R.array.pref_orderbyEntries);
        String orderbyValyue = sharedPreferences.getString(context.getString(R.string.pref_oderbyKey),"1");
        String orderbyString = orderBy[Integer.parseInt(orderbyValyue)-1];
        Uri baseURL = Uri.parse(URL_EARTHQUEAKE);
        Uri.Builder uriBuilder = baseURL.buildUpon();
        uriBuilder.appendQueryParameter(QUERY_PARAMETER_FORMAT,"geojson");
        uriBuilder.appendQueryParameter(QUERY_PARAMETER_LIMIT,sharedPreferences.getString(context.getResources().getString(R.string.pref_limitKey),""));
        uriBuilder.appendQueryParameter(QUERY_PARAMETER_MINMAG,sharedPreferences.getString(context.getResources().getString(R.string.pref_magKey),""));
        uriBuilder.appendQueryParameter(QUERY_PARAMETER_ORDERBY,orderbyString);
        return uriBuilder.toString();
    }
}
