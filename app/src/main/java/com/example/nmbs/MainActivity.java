package com.example.nmbs;

import android.app.FragmentManager;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.drawer.WearableNavigationDrawer;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;


import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.wear.widget.drawer.WearableActionDrawerView;
import androidx.wear.widget.drawer.WearableNavigationDrawerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.example.nmbs.AddTrajectFragment.SHARED_PREFS;

public class MainActivity extends WearableActivity {


    private WearableNavigationDrawerView mWearableNavigationDrawer;
    private  FragmentManager fragmentManager;
    private AddTrajectFragment addTraject;
    private TrajectenFragment trajectenFragment;
    private SettingFragment settingFragment;
    private AddFragmentAutoFill addFragmentAutoFill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enables Always-on
        setAmbientEnabled();
        // menu

        mWearableNavigationDrawer = findViewById(R.id.top_navigation_drawer);
        mWearableNavigationDrawer.setAdapter(new NavigationAdapter(this));
        mWearableNavigationDrawer.getController().peekDrawer();
        addTraject = new AddTrajectFragment();
        trajectenFragment= new TrajectenFragment();
        settingFragment = new SettingFragment();
        fragmentManager = getFragmentManager();
        addFragmentAutoFill = new AddFragmentAutoFill();
        fragmentManager.beginTransaction().replace(R.id.content_frame, trajectenFragment).commit();
        mWearableNavigationDrawer.addOnItemSelectedListener(new WearableNavigationDrawerView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(int i) {
                // swich naar passende fragment
                switch (i){
                    case 0:  fragmentManager.beginTransaction().replace(R.id.content_frame, trajectenFragment).commit(); break;
                    case 1: SetStationKiezerChecker(); break;
                    case 2: fragmentManager.beginTransaction().replace(R.id.content_frame,settingFragment).commit();break;
                }
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    private void SetStationKiezerChecker(){
        SharedPreferences sharedPreferences= getBaseContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Map<String,?> keys = sharedPreferences.getAll();
        Log.i("SettingFragment","Started");
        ArrayList<Traject> stations= new ArrayList<>();
        Boolean text=true;
        for(Map.Entry<String,?> entry : keys.entrySet()){
            Log.i("station text filter",entry.getKey() + ": " +entry.getValue().toString()+" "+(entry.getKey().equals("Station Kiezer"))+" "+(entry.getValue().toString()=="true"));
            if(entry.getKey().equals("Station Kiezer")&&entry.getValue().toString()=="true"){
                Log.i("station text filter",entry.getKey() + ": " +entry.getValue().toString());
                text=false;
            }
        }

        if(text){
            Log.i("station text filter", "SetStationKiezerChecker: launch addtraject");
            fragmentManager.beginTransaction().replace(R.id.content_frame, addTraject).commit();
        }else{
            Log.i("station text filter", "SetStationKiezerChecker: launch addfragmentautofill");
            fragmentManager.beginTransaction().replace(R.id.content_frame, addFragmentAutoFill).commit();
        }
    }






}
