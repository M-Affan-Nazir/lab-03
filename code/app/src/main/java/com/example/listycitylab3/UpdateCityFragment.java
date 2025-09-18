package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class UpdateCityFragment extends DialogFragment { // Reusable dialog component (not a full screen). You show it when you want the user to add a city.

    interface UpdateCityDialogListener { //If you host me, you must provide addCity(City)
        void updateCity(City city, int position);
    }


    private UpdateCityDialogListener listener;
    @Override
    public void onAttach(@NonNull Context context) {  // When the fragment attaches to an Activity, it grabs the Context and casts it to that interface. If the host activity doesn’t implement the interface, the fragment throws — this guarantees the fragment can call listener.addCity(...)
        super.onAttach(context);
        if (context instanceof UpdateCityDialogListener) {
            listener = (UpdateCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement UpdateCityDialogListener");
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Bundle args = getArguments();
        String city_name = args.getString("city_name");
        String province_name = args.getString("province_name");
        int position = args.getInt("position");

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_update_city, null); //turns the fragment_add_city.xml layout into a View object
        EditText updateCityName = view.findViewById(R.id.update_text_city_text); // finds the city text input inside that view so you can read what the user types.
        EditText updateProvinceName = view.findViewById(R.id.update_text_province_text); //finds the province text input inside the view.


        updateCityName.setText(city_name);
        updateProvinceName.setText(province_name);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()); //makes a dialog builder you use to set the title, buttons, and attach the view before creating the dialog.
        return builder // Configures the dialog (sets the form and title), adds Cancel (closes) and Add (reads the inputs and calls listener.addCity(...)), then creates the dialog.
                .setView(view)
                .setTitle("Update a city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Update", (dialog, which) -> {
                    String cityName = updateCityName.getText().toString();
                    String provinceName = updateProvinceName.getText().toString();
                    listener.updateCity(new City(cityName, provinceName), position); // call updateCity() in host class, using listener.
                })
                .create();
    }
}
