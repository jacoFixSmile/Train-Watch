package com.example.nmbs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.wear.widget.WearableLinearLayoutManager;
import androidx.wear.widget.WearableRecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;

import java.util.ArrayList;

public class StationSelecter extends WearableActivity {
    private WearableRecyclerView recycler_launcher_view;
    public ArrayList<String> stations;
    private String Plaats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_selecter);
        recycler_launcher_view= findViewById(R.id.recycler_launcher_view);
        recycler_launcher_view.setEdgeItemsCenteringEnabled(true);
        recycler_launcher_view.setLayoutManager(new WearableLinearLayoutManager(this));
        Intent intent = getIntent();
        stations= intent.getStringArrayListExtra("StationNaamen");
        Plaats=intent.getStringExtra("Plaats");
        Log.i("Station", "1e station: "+stations.get(1));
        initiRecylerView();
        // zonder onderstaande code scorlt hij te snel maar het wel mooi gecentreerd
        CustomScrollingLayoutCallback customScrollingLayoutCallback =
                new CustomScrollingLayoutCallback();
        recycler_launcher_view.setLayoutManager(
                new WearableLinearLayoutManager(this, customScrollingLayoutCallback));


    }
    private void initiRecylerView(){
        StationsAdapter adapter= new StationsAdapter(stations,getBaseContext(),Plaats,this);
        recycler_launcher_view.setAdapter(adapter);
    }
}