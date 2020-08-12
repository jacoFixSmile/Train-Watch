package com.example.nmbs;

import android.content.SharedPreferences;
import android.util.Log;

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
    public Stations(String json){
        Json=json;
        initStations();
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
}
