package com.example.listycitylab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CityArrayAdapter extends ArrayAdapter<City> {

    public CityArrayAdapter(Context context, ArrayList<City> cities){
        super(context, 0, cities);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) { //Produces the row view for the item at position. The system calls this when it needs to display a row.

        // ------------------- Optional: Performance improvement chunk -------------------//
        //When you scroll, Android reuses row views that went off-screen instead of creating new ones, and just updates their content.
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.content, parent, false); //create new row
        } else {
            view = convertView; //reuse old row which scrolled upward.
        }


        // ------------------- Mandatory: Main Chunk -------------------//
        City city = getItem(position); //Extract the element provided by Array Adapter at that position, into a City object.
        TextView cityName = view.findViewById(R.id.city_text); //Find the city text field in the XML
        TextView provinceName = view.findViewById(R.id.province_text); //Find the province text field in the XML

        // Below: Binds data to views — sets the text shown for that row.
        cityName.setText(city.getName());
        provinceName.setText(city.getProvince());

        return view;
    }
}
