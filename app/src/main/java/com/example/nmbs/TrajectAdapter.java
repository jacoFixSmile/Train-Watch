package com.example.nmbs;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Map;

public class TrajectAdapter extends RecyclerView.Adapter<TrajectAdapter.ViewHolder> {
    private ArrayList<Traject> Trajecten;
    private Context context;
    private String TAG="TrajectAdapter";


    public TrajectAdapter(ArrayList<Traject> trajecten, Context context){
        this.Trajecten=trajecten;
        this.context=context;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_traject,parent,false);
        TrajectAdapter.ViewHolder holder = new TrajectAdapter.ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull TrajectAdapter.ViewHolder holder, int position) {
        holder.trackID.setText(Trajecten.get(position).getNumber()+"");
        holder.vertrek.setText(Trajecten.get(position).getVertrek());
        holder.aankomst.setText(Trajecten.get(position).getAankomst());
        holder.vertrekSpoor.setText(Trajecten.get(position).getVertrekSpoort());
        holder.vertrekUur.setText(Trajecten.get(position).getVertrekUur());
        holder.aankomstUur.setText(Trajecten.get(position).getAankomUur());



    }



    @Override
    public int getItemCount() {
        return Trajecten.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView vertrek;
        TextView aankomst;
        TextView trackID;
        TextView vertrekUur;
        TextView vertrekSpoor;
        TextView aankomstUur;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            vertrek = itemView.findViewById(R.id.vertrek);
            aankomst = itemView.findViewById(R.id.aankomst);
            trackID= itemView.findViewById(R.id.trackID);
            vertrekUur=itemView.findViewById(R.id.vertrekUur);
            vertrekSpoor=itemView.findViewById(R.id.vertrekSpoor);
            aankomstUur=itemView.findViewById(R.id.aankomstUur);
        }
    }
}
