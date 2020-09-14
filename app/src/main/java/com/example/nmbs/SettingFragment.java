package com.example.nmbs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;

public class SettingFragment  extends android.app.Fragment {

    private Switch StationSwich;
    private Button ResetApp;
    private View view;
    public static final String SHARED_PREFS = "shardPrefs";


    public SettingFragment() {
        // Required empty public constructor
    }

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        Context context = view.getContext();
        StationSwich =(Switch) view.findViewById(R.id.StationKiezeSwitch);
        ResetApp = (Button) view.findViewById(R.id.ResetAppButton);
        CheckSettings();
        StationSwich.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SetKiezerChecked();
            }
        });
        ResetApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 14-9-2020 verwijder alle shared prefferences
                System.exit(0);
            }
        });

        return view;
    }

    private void SetKiezerChecked(){
        SharedPreferences sharedPreferences=  view.getContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("Station Kiezer");
        editor.putBoolean("Station Kiezer",StationSwich.isChecked());
        editor.commit();
        CheckSettings();


    }
    private void CheckSettings(){
        SharedPreferences sharedPreferences= getContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Map<String,?> keys = sharedPreferences.getAll();
        Log.i("SettingFragment","Started");
        ArrayList<Traject> stations= new ArrayList<>();
        for(Map.Entry<String,?> entry : keys.entrySet()){
            if(entry.getKey()=="Station Kiezer"&&entry.getValue().toString()=="true"){
                Log.d("station text filter",entry.getKey() + ": " +entry.getValue().toString());
                StationSwich.setChecked(true);
                }
            }

        }
}

