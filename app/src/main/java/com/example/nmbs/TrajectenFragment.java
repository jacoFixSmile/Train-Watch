package com.example.nmbs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;


import android.renderscript.ScriptIntrinsicBLAS;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.wear.widget.WearableLinearLayoutManager;
import androidx.wear.widget.WearableRecyclerView;

import java.net.ContentHandler;
import java.util.ArrayList;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrajectenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrajectenFragment extends android.app.Fragment {

    public static final String SHARED_PREFS = "shardPrefs";
    private WearableRecyclerView recycler_launcher_view;
    private Context context;
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
        View view=inflater.inflate(R.layout.fragment_trajecten, container, false);
        context=view.getContext();
        recycler_launcher_view= view.findViewById(R.id.recycler_launcher_view_trajecten);
        recycler_launcher_view.setEdgeItemsCenteringEnabled(true);
        recycler_launcher_view.setLayoutManager(new WearableLinearLayoutManager(context));
        initiRecylerView();
        // zonder onderstaande code scorlt hij te snel maar het wel mooi gecentreerd
        CustomScrollingLayoutCallback customScrollingLayoutCallback =
                new CustomScrollingLayoutCallback();
        recycler_launcher_view.setLayoutManager(
                new WearableLinearLayoutManager(context, customScrollingLayoutCallback));
        return view;
    }
    private void initiRecylerView(){
        TrajectAdapter adapter= new TrajectAdapter(getTrajecten(),context);
        recycler_launcher_view.setAdapter(adapter);
    }
    private ArrayList<String> getTrajecten(){
        SharedPreferences sharedPreferences= getContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Map<String,?> keys = sharedPreferences.getAll();
        Log.i("TrajectFragment","Started");
        ArrayList <String> stations= new ArrayList<>();
        for(Map.Entry<String,?> entry : keys.entrySet()){
            Log.d("map values",entry.getKey() + ": " +entry.getValue().toString());
            stations.add(entry.getKey() + ": " +entry.getValue().toString());

        }
        return stations;
    }
}