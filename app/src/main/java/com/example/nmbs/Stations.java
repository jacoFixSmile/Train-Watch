package com.example.nmbs;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import static android.content.Context.MODE_PRIVATE;

public class Stations {
    public String Json;
    public ArrayList<String> StationNaamen=new ArrayList<String>();
    public JSONArray Stations;
    private RequestQueue queue;
    private Context context;
    public Boolean gelukt;
    public Stations(Context context){
        this.context=context;
        gelukt=false;
        new NukeSSLCerts().nuke(); // vage klasse die ervoor zorgt dat volly de src vertrouwt
        queue = Volley.newRequestQueue(context.getApplicationContext());
        VolleyRequestStations();
    }
    private void initStations(){

        try {
            Log.i("TAG", "initStations: ");
            JSONObject reader = new JSONObject(Json);
            Stations = reader.getJSONArray("station");
            Log.i("TAG", "Stations: "+Stations.getJSONObject(1));
            JSONObject object =Stations.getJSONObject(1);
            Log.i("TAG", "Station 1: "+object.getString("name"));
            initStationNaamen();

        } catch (JSONException e) {
            Log.i("TAG", "initStations: "+  e.getMessage());
            e.printStackTrace();
        }

    }
    private void initStationNaamen(){
        for(int i=1;i<Stations.length();i++){
            try {
                StationNaamen.add(Stations.getJSONObject(i).getString("name"));

            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
        Collections.sort(StationNaamen);
    }
    private void VolleyRequestStations(){
        String url = "https://api.irail.be/stations/?format=json&lang=en";
        final String[] returner = new String[1];
        // Request a string response from the provided URL.
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.i("Data", "onResponse: "+response.substring(0,500));
                        Json= response;
                        initStations();
                        gelukt=true;

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Stations niet gevonden",Toast.LENGTH_LONG);
                Log.i("TAG", "Data niet gevonden: "+error.getMessage());
            }
        });
        queue.add(stringRequest);

// Add the request to the RequestQueue.
    }
}
