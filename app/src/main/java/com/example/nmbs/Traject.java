package com.example.nmbs;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Traject implements Comparable<Traject>{
    private int number;
    private String vertrek;
    private String Aankomst;
    private String VertrekUur;
    private String AankomUur;
    private String VertrekSpoort;


    public Traject(String key, String traject) {
        this.number = Integer.parseInt(key.substring(8));
        this.vertrek = traject.substring(1,traject.indexOf(','));
        Aankomst = traject.substring(traject.indexOf(',')+1,traject.length()-1);
        VertrekUur="/";
        AankomUur="/";
        VertrekSpoort="Geen online verbinding";
    }

    public String getVertrekUur() {
        return VertrekUur;
    }

    public void setVertrekUur(String vertrekUur) {
        VertrekUur = vertrekUur;
    }

    public String getAankomUur() {
        return AankomUur;
    }

    public void setAankomUur(String aankomUur) {
        AankomUur = aankomUur;
    }

    public String getVertrekSpoort() {
        return VertrekSpoort;
    }

    public void setVertrekSpoort(String vertrekSpoort) {
        VertrekSpoort = vertrekSpoort;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getVertrek() {
        return vertrek;
    }

    public void setVertrek(String vertrek) {
        this.vertrek = vertrek;
    }

    public String getAankomst() {
        return Aankomst;
    }

    public void setAankomst(String aankomst) {
        Aankomst = aankomst;
    }

    @Override
    public int compareTo(Traject traject) {
        if(number<traject.number){
            return -1;
        }else{
            return 1;
        }
    }
    @Override
    public String toString() {
        return number+": V"+getVertrek()+"- A"+getAankomst();
    }
}
