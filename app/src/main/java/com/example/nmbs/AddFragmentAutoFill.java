package com.example.nmbs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;


public class AddFragmentAutoFill extends android.app.Fragment  {

    public static final String SHARED_PREFS = "shardPrefs";
    private AutoCompleteTextView autoFillAankomst;
    private AutoCompleteTextView autoFillTextVertek;
    private View view;
    private ImageButton imageButton;
    private TextView textView3;
    private TextView textView4;
    private Stations stations;


    public AddFragmentAutoFill() {
        // Required empty public constructor
    }


    public static AddFragmentAutoFill newInstance() {
        AddFragmentAutoFill fragment = new AddFragmentAutoFill();

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
        view=inflater.inflate(R.layout.fragment_add_auto_fill, container, false);
        autoFillTextVertek =view.findViewById(R.id.TextVertek);
        autoFillAankomst =view.findViewById(R.id.TextAankomst);
        imageButton= view.findViewById(R.id.imageButtonAdd);
        textView3=view.findViewById(R.id.textView3);
        textView4=view.findViewById(R.id.textView4);
        stations= new Stations(view.getContext());
        autoFillTextVertek.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_NEXT) {
                    OpenSelecteerStation("Vertrek");
                    return true;
                }
                return false;
            }
        });
        autoFillAankomst.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    OpenSelecteerStation("Aankomst");
                    return true;
                }
                return false;
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTraject();
            }
        });
        LoadStationsPrefrences();
        return view;

    }
    private void OpenSelecteerStation(String plaats){
        Log.i("OpenSelecteerStation", "OpenSelecteerStation: klikt");
        Intent intent = new Intent(view.getContext().getApplicationContext(),StationSelecter.class);
        if(plaats.equals("Aankomst")){
            intent.putExtra("StationNaamen",RemoveRegex(autoFillAankomst.getText().toString()));
        }else{
            intent.putExtra("StationNaamen",RemoveRegex(autoFillTextVertek.getText().toString()));
        }
        intent.putExtra("Plaats",plaats);
        for(int i=0;i<stations.StationNaamen.size();i++){
            Log.d("AddFragmentAutoFill", "OpenSelecteerStation: "+stations.StationNaamen.get(i));
        }
        startActivity(intent);

    }
    private ArrayList<String> RemoveRegex(String woord){
        String rule="[A-Za-z]*";
        char[] woordArray =woord.toCharArray();
        for(char a:woordArray){
            if(a==' '||a=='-'){
                rule+="[\\_,\\-,\\s]";
            }else{
                rule+="["+Character.toUpperCase(a)+","+a+"]";
            }
        }
        rule+="\\s?[A-Za-z]*";
        Pattern pattern= Pattern.compile(rule);
        ArrayList<String> lijst=(ArrayList<String>)stations.StationNaamen.clone();
        Log.d("AddFragmentAutoFill", "RemoveRegex: "+rule);
        for(String string:stations.StationNaamen){
            Matcher m = pattern.matcher(string);
            if(!m.find()){
                lijst.remove(string);
            }
        }
        if(lijst.size()==0){
            lijst.add("Niets gevonden");
        }
    return lijst;

    }
    public void LoadStationsPrefrences(){
        SharedPreferences sharedPreferences= view.getContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        autoFillTextVertek.setText(sharedPreferences.getString("Vertrek","Geen Vertrek gevonden"));
        autoFillAankomst.setText(sharedPreferences.getString("Aankomst","Geen Aankomst gevonden"));
    }
    private int GetHoeveelTrajecten(){
        SharedPreferences sharedPreferences= view.getContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String waarde=sharedPreferences.getString("Aantal trajecten","Geen trajecten gevonden");
        Log.i("AddTrajectFragment", "GetHoeveelTrajecten: "+waarde);
        if(!waarde.equals("Geen trajecten gevonden")){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Aantal trajecten",Integer.toString((Integer.parseInt(waarde))+1));
            editor.commit();
            return Integer.parseInt(waarde);
        }else{
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Aantal trajecten","1");
            editor.commit();
            return 1;
        }
    }
    private void addTraject(){
        Boolean uniek = true;
        if(CheckOfStationBestaan()){

            SharedPreferences sharedPreferences=  view.getContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            int TrajectNummer=GetHoeveelTrajecten();
            Set<String> strings = new HashSet<String>();
            strings.add(autoFillTextVertek.getText().toString());
            strings.add(autoFillAankomst.getText().toString());
            editor.putStringSet("Traject "+TrajectNummer,strings);
            editor.commit();

        }

    }
    private Boolean CheckOfStationBestaan(){
        if(!stations.StationNaamen.contains(autoFillTextVertek.getText().toString())){
                textView3.setTextColor(Color.RED);
            return false;
        }else{
            textView3.setTextColor(Color.GRAY);

        }
        if(!stations.StationNaamen.contains(autoFillAankomst.getText().toString())){
            textView4.setTextColor(Color.RED);
            return false;
        }else{
            textView4.setTextColor(Color.GRAY);
            return true;
        }
    }
}