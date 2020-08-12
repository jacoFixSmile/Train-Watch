package com.example.nmbs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class StationsAdapter extends RecyclerView.Adapter<StationsAdapter.ViewHolder> {
    private  ArrayList<String> Stations;
    private Context Context;
    public static final String SHARED_PREFS = "shardPrefs";
    private String Plaats;
    private Activity Closer;

    public StationsAdapter(ArrayList<String> stations, Context context,String plaats, Activity closer ){
        Log.i("TAG", "init station adapter: ");

        Stations=stations;
        Context=context;
        Plaats=plaats;
        Closer= closer;
    }
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_station,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.naam.setText(Stations.get(position));

    }

    @Override
    public int getItemCount() {

        return Stations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        Button naam;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            naam = itemView.findViewById(R.id.naam);
            naam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   setPrefVertrek(naam.getText().toString());
                }
            });
        }
    }
    private void setPrefVertrek(String naam){
        SharedPreferences sharedPreferences=  Context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Set<String> strings = new HashSet<String>();
        strings.add(sharedPreferences.getString("Vertrek","Geen Vertrek gevonden"));
        strings.add(sharedPreferences.getString("Aankomst","Geen Vertrek gevonden"));

        if(!strings.contains(naam)){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Plaats,naam);
            editor.commit();
            Closer.finish();

        }


    }
}
