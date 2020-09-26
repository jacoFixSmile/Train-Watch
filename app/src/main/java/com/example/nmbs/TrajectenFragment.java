package com.example.nmbs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;


import android.renderscript.ScriptIntrinsicBLAS;
import android.service.autofill.RegexValidator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.wear.widget.WearableLinearLayoutManager;
import androidx.wear.widget.WearableRecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.ContentHandler;
import java.security.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private RequestQueue queue;
    private Boolean done=false;
    private ArrayList <Traject> stations;
    private TrajectAdapter adapter;
    private TextView lastRefresh;
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
        lastRefresh=view.findViewById(R.id.lastRefresh);
        // zonder onderstaande code scorlt hij te snel maar het wel mooi gecentreerd
        CustomScrollingLayoutCallback customScrollingLayoutCallback =
                new CustomScrollingLayoutCallback();
        recycler_launcher_view.setLayoutManager(
                new WearableLinearLayoutManager(context, customScrollingLayoutCallback));
        new NukeSSLCerts().nuke(); // vage klasse die ervoor zorgt dat volly de src vertrouwt
        queue = Volley.newRequestQueue(context.getApplicationContext());
        adapter= new TrajectAdapter(getTrajecten(),context);
        initiRecylerView();

        return view;
    }
    private void initiRecylerView(){
        recycler_launcher_view.setAdapter(adapter);
    }
    private ArrayList<Traject> getTrajecten(){
        SharedPreferences sharedPreferences= getContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Map<String,?> keys = sharedPreferences.getAll();
        Log.i("TrajectFragment","Started");
        stations= new ArrayList<>();
        Pattern regex = Pattern.compile("Traject [0-9]*$");
        for(Map.Entry<String,?> entry : keys.entrySet()){
            Matcher m= regex.matcher(entry.getKey());
            if(m.find()){
                Log.d("map values",entry.getKey() + ": " +entry.getValue().toString());
                Traject traject=new Traject(entry.getKey(),entry.getValue().toString());
                stations.add(traject);
            }

        }
        Collections.sort(stations);
        for (int i=0;i<stations.size();i++){
            Log.d("array values",stations.get(i).toString());
            VolleyRequestStations(i);
        }
        
        return stations;
    }

    private void VolleyRequestStations(int i){
        final int teller=i;
        String url = "https://api.irail.be/connections/?format=json&lang=en&from="+stations.get(i).getVertrek()+"&to="+stations.get(i).getAankomst()+"";
        // Request a string response from the provided URL.
         StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.i("Data", "onResponse: "+response.substring(0,500));
                        try {
                            JSONObject data = new JSONObject(response);
                            Date aankomUur= new Date((Long.parseLong(data.getJSONArray("connection").getJSONObject(0).getJSONObject("arrival").get("time").toString()+"000")));
                            Date vertrekUur = new Date((Long.parseLong(data.getJSONArray("connection").getJSONObject(0).getJSONObject("departure").get("time").toString()+"000")));
                            stations.get(teller).setAankomUur(String.format("%02d",aankomUur.getHours())+":"+String.format("%02d",aankomUur.getMinutes()));
                            stations.get(teller).setVertrekUur(String.format("%02d",vertrekUur.getHours())+":"+String.format("%02d",vertrekUur.getMinutes()));
                            stations.get(teller).setVertrekSpoort("Spoor: "+data.getJSONArray("connection").getJSONObject(0).getJSONObject("departure").get("platform").toString());
                            lastRefresh.setText("Updated "+ Calendar.getInstance().getTime().getHours()+":"+Calendar.getInstance().getTime().getMinutes());
                        } catch (JSONException e) {
                            e.printStackTrace();
                            stations.get(teller).setVertrekSpoort("error"+e.getMessage());
                        }

                            initiRecylerView();


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