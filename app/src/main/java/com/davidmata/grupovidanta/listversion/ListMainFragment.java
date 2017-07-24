package com.davidmata.grupovidanta.listversion;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.davidmata.grupovidanta.listversion.GlobalPack.Global;
import com.davidmata.grupovidanta.listversion.controller.EarthquakeController;
import com.davidmata.grupovidanta.listversion.implementation.EarthquakeFromString;
import com.davidmata.grupovidanta.listversion.implementation.EarthquakeLoaderJson;
import com.davidmata.grupovidanta.listversion.implementation.EarthquakesJson;

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


/**
 * A simple {@link Fragment} subclass.
 */
public class ListMainFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Earthquake>> {

    private static final int EARTHQUAKE_LOADER_ID = 1;
    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;
    private ProgressBar loading_spinner;
    public ListMainFragment() {
        // Required empty public constructor
    }
    ListView listView;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewRoot = inflater.inflate(R.layout.fragment_mainlist, container, false);
        mEmptyStateTextView = (TextView)viewRoot.findViewById(R.id.empty_view);
        loading_spinner = (ProgressBar)viewRoot.findViewById(R.id.loading_spinner);
        //final ArrayList<Earthquake> earthquake =  (ArrayList<Earthquake>) QueryUtils.extractEarthquakes();
        //final ArrayList<Earthquake> earthquake =  (ArrayList<Earthquake>) new EarthquakeController(new EarthquakeFromDB()).getListOfEarthquakes();
       // final ArrayList<Earthquake> earthquake =  (ArrayList<Earthquake>) new EarthquakeController(new EarthquakesJson()).getListOfEarthquakes();
        //I tried to use dependency injection
       // final ArrayList<Earthquake> earthquake = null;// (ArrayList<Earthquake>) new EarthquakeController(new EarthquakeLoaderJson(getContext())).getListOfEarthquakes();

        listView = (ListView)viewRoot.findViewById(R.id.list_view);
        listView.setEmptyView(mEmptyStateTextView);
        /*EarthquakeAdapter noteAdapter = new EarthquakeAdapter(getActivity(), earthquake);
        listView.setAdapter(noteAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //final String mensaje = notas.get(position).getMag();
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(earthquake.get(0).getMapURL()));
                Log.w("ListView", "Se despliega la siguente URL " + earthquake.get(0).getMapURL());
                startActivity(intent);

            }
        });*/
       getLoaderManager().initLoader(EARTHQUAKE_LOADER_ID,null,this);
        return viewRoot;
    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, Bundle args) {
        Log.w("ListVersion","onCreateLoader");
        loading_spinner.setVisibility(View.VISIBLE);

        return new EarthquakeLoaderJson(getContext());

    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> data) {
        Log.w("ListVersion","onLoadFinished");
        loading_spinner.setVisibility(View.INVISIBLE);
        if(Global.isNetworkAvailable(getContext())){
            mEmptyStateTextView.setText(R.string.no_earthqueke);
        }else{
            mEmptyStateTextView.setText(R.string.no_networkAvailable);
        }

        final ArrayList<Earthquake> earthquakeArrayList = (ArrayList<Earthquake>)data;
        EarthquakeAdapter earthquakeAdapter = new EarthquakeAdapter(getActivity(),earthquakeArrayList);
        listView.setAdapter(earthquakeAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //final String mensaje = notas.get(position).getMag();
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(earthquakeArrayList.get(0).getMapURL()));
                Log.w("ListView", "Se despliega la siguente URL " + earthquakeArrayList.get(0).getMapURL());
                startActivity(intent);


            }
        });
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        Log.w("ListVersion","onLoaderReset");
    }




    /*private void jsonExample(){
        String candyJson = "{\"candies\":[{\"name\":\"Jelly Beans\",\"count\":10}]}";
        try{
            JSONObject root = new JSONObject(candyJson);
            JSONArray candiesArray = root.getJSONArray("candies");
            JSONObject firsCandie = candiesArray.getJSONObject(0);
            Log.w("ListView",firsCandie.getString("name"));
            Log.w("ListView",String.valueOf(firsCandie.getInt("count")));

        }catch (JSONException e){
            Log.w("ListView", "eeror" + e.getMessage());
        }

    }*/

}
