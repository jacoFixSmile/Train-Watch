package com.example.nmbs;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.AdapterView;

import androidx.wear.widget.drawer.WearableNavigationDrawerView;

public class NavigationAdapter extends WearableNavigationDrawerView.WearableNavigationDrawerAdapter{

    private final Context mContext;
    private String[] Menus ={"Trajecten","Add traject","Settings"};

    /* package */
    public NavigationAdapter(Context context) {
        mContext = context;
    }
    @Override
    public int getCount() {
        return Menus.length;
    }

    @Override
    public String getItemText(int pos) {
        return Menus[pos];
       // return mSolarSystem.get(pos).getName();
    }

    @Override
    public Drawable getItemDrawable(int pos) {
        switch (pos){
            case 0: return mContext.getDrawable(android.R.drawable.ic_menu_agenda);
            case 1:return  mContext.getDrawable(android.R.drawable.ic_input_add) ;
            default: return mContext.getDrawable(android.R.drawable.ic_menu_manage) ;
        }

    }
    // een interface waarmee aangegeven word dat menu item verandert word



}