package com.example.nmbs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TrajectAdapter extends RecyclerView.Adapter<TrajectAdapter.ViewHolder> {
    private ArrayList<String> Trajecten;
    private Context context;
    public TrajectAdapter(ArrayList<String> trajecten, Context context){
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
        holder.vertrek.setText(Trajecten.get(position));
    }


    @Override
    public int getItemCount() {
        return Trajecten.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView vertrek;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            vertrek = itemView.findViewById(R.id.vertrek);

        }
    }
}
