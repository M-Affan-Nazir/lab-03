package com.example.listycitylab3;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements AddCityFragment.AddCityDialogListener, UpdateCityFragment.UpdateCityDialogListener {
    private ArrayList<City> dataList;
    private ListView cityList;
    private CityArrayAdapter cityAdapter;
    private static final String TAG = "MainActivity";

    // Called by AddCityFragment
    @Override
    public void addCity(City city) {  // The host Activity (MainActivity) implements the interface and provides the real behavior for addCity(...)
        cityAdapter.add(city);
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateCity(City city, int position) {  // The host Activity (MainActivity) implements the interface and provides the real behavior for addCity(...)
        City c = cityAdapter.getItem(position);
        c.setName(city.getName());
        c.setProvince(city.getProvince());
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] cities = { "Edmonton", "Vancouver", "Toronto" };
        String[] provinces = { "AB", "BC", "ON" };

        dataList = new ArrayList<>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }

        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v -> {
            new AddCityFragment().show(getSupportFragmentManager(), "Add City"); // MainActivity shows the fragment when the user taps the FloatingActionButton:
                                                                                     // the show() method is provided by DialogFragment.
        });

        // Bundle position, city name, and province. Send to fragment and display.
        cityList.setOnItemClickListener((listView, row, position, id) -> {

            City city = (City) listView.getItemAtPosition(position);

            Bundle b = new Bundle();
            b.putInt("position",position);
            b.putString("city_name", city.getName());
            b.putString("province_name", city.getProvince());

            UpdateCityFragment f = new UpdateCityFragment();
            f.setArguments(b);

            f.show(getSupportFragmentManager(), "Update City");

        });
    }
}