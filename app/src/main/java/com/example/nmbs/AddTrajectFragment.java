package com.example.nmbs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashSet;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddTrajectFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class AddTrajectFragment extends android.app.Fragment {

    private TextView mTextView;
    private Stations stations;
    private Context context;
    private Button buttonVertek;
    private Button buttonAankomst;
    private TextView textView3;
    private TextView textView4;
    private ImageButton imageButtonAdd;
    private View view;
    public static final String SHARED_PREFS = "shardPrefs";

    // TODO: Rename and change types and number of parameters
    public static AddTrajectFragment newInstance(String param1, String param2) {
        AddTrajectFragment fragment = new AddTrajectFragment();
        return fragment;
    }

    public AddTrajectFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("start oncreate ","gestart ingedrukt");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context=getActivity().getApplicationContext();
        Log.i("start oncreate view","gestart ingedrukt");

        view = inflater.inflate(R.layout.fragment_add_traject, container, false);
        // verkrijg de stations + initalizeren station spinner
        stations= new Stations(context);
        buttonVertek= (Button) view.findViewById(R.id.buttonVertek);
        buttonVertek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenSelecteerStation("Vertrek");
                Log.i("Vertrek knop ingedrukt","This ingedrukt");
            }
        });
        buttonAankomst= (Button) view.findViewById(R.id.buttonAankomst);
        buttonAankomst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenSelecteerStation("Aankomst");
            }
        });
        textView3= (TextView) view.findViewById(R.id.textView3);
        textView4= (TextView) view.findViewById(R.id.textView4);
        imageButtonAdd=(ImageButton) view.findViewById(R.id.imageButtonAdd);
        imageButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTraject();
            }
        });
        SharedPreferences sharedPreferences= context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.OnSharedPreferenceChangeListener myPrefListner = new SharedPreferences.OnSharedPreferenceChangeListener(){
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                LoadStationsPrefrences();
            }
        };
        myPrefListner.onSharedPreferenceChanged(sharedPreferences,"Aankomst");
        myPrefListner.onSharedPreferenceChanged(sharedPreferences,"Vertrek");
        return view;
    }

    private void OpenSelecteerStation(String plaats){
        Log.i("OpenSelecteerStation", "OpenSelecteerStation: klikt");
            Intent intent = new Intent(context.getApplicationContext(),StationSelecter.class);
            intent.putExtra("StationNaamen",stations.StationNaamen);
            Log.i("AddTrajectFragment", stations.StationNaamen.get(1));
            intent.putExtra("Plaats",plaats);
            startActivity(intent);
        LoadStationsPrefrences();
    }
    public void LoadStationsPrefrences(){
        SharedPreferences sharedPreferences= context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        textView3.setText(sharedPreferences.getString("Vertrek","Geen Vertrek gevonden"));
        textView4.setText(sharedPreferences.getString("Aankomst","Geen Aankomst gevonden"));
    }



    private int GetHoeveelTrajecten(){
        SharedPreferences sharedPreferences= context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String waarde=sharedPreferences.getString("Aantal trajecten","Geen trajecten gevonden");
        if(waarde!="Geen trajecten gevonden"){
            return Integer.parseInt(waarde);
        }else{
            return 0;
        }
    }

    private void addTraject(){
        if(textView3.getText()!="Geen Vertrek gevonden"&&textView4.getText()!="Geen Aankomst gevonden"){
            SharedPreferences sharedPreferences=  context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            int TrajectNummer=GetHoeveelTrajecten()+1;
            Set<String> strings = new HashSet<String>();
            strings.add(textView3.getText().toString());
            strings.add(textView4.getText().toString());
            editor.putStringSet("Traject "+TrajectNummer,strings);
            editor.commit();
        }

    }

}