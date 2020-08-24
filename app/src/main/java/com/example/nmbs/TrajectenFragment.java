package com.example.nmbs;

import android.content.SharedPreferences;
import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrajectenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrajectenFragment extends android.app.Fragment {

    private TextView text;
    public static final String SHARED_PREFS = "shardPrefs";
    public TrajectenFragment() {
        // Required empty public constructor
    }


    public static TrajectenFragment newInstance() {
        TrajectenFragment fragment = new TrajectenFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_stations, container, false);
        text = view.findViewById(R.id.mText);
        SharedPreferences sharedPreferences= getContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Map<String,?> keys = sharedPreferences.getAll();
        Log.i("TrajectFragment","Started");
        String stations="";
        for(Map.Entry<String,?> entry : keys.entrySet()){
            Log.d("map values",entry.getKey() + ": " +entry.getValue().toString());
            stations+=entry.getKey() + ": " +entry.getValue().toString();

        }
        text.setText(stations);
        return view;
    }
}