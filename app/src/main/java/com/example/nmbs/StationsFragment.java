package com.example.nmbs;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StationsFragment extends Fragment {

    private TextView text;
    public static final String SHARED_PREFS = "shardPrefs";
    public StationsFragment() {
        // Required empty public constructor
    }
    public static StationsFragment newInstance(String param1) {
        StationsFragment fragment = new StationsFragment();
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
        Log.i("StationsFragment","Started");

        for(Map.Entry<String,?> entry : keys.entrySet()){
            Log.d("map values",entry.getKey() + ": " +entry.getValue().toString());
        }

        return view;
    }
}