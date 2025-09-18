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

public class AddCityFragment extends DialogFragment { // Reusable dialog component (not a full screen). You show it when you want the user to add a city.

    // Whichever activity opens AddCityFragment, it must have implemented addCity(city)
    // when the user taps Add, the dialog can call listener.addCity(...) and add the city into the List
    interface AddCityDialogListener { //If you host me, you must provide addCity(City)
        void addCity(City city);
    }


    // Connect the Dialogue with it's host Activity, so the dialogue can send the new city back
    private AddCityDialogListener listener;
    @Override
    public void onAttach(@NonNull Context context) {  // When the fragment attaches to an Activity, it grabs the Context and casts it to that interface. If the host activity doesn’t implement the interface, the fragment throws — this guarantees the fragment can call listener.addCity(...)
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }


    // Builds and returns a dialog with a city/province form that, when Add is tapped, reads the inputs and sends a new City to the listener.
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null); //turns the fragment_add_city.xml layout into a View object
        EditText editCityName = view.findViewById(R.id.edit_text_city_text); // finds the city text input inside that view so you can read what the user types.
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text); //finds the province text input inside the view.

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()); //makes a dialog builder you use to set the title, buttons, and attach the view before creating the dialog.
        return builder // Configures the dialog (sets the form and title), adds Cancel (closes) and Add (reads the inputs and calls listener.addCity(...)), then creates the dialog.
                .setView(view)
                .setTitle("Add a city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Add", (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();
                    listener.addCity(new City(cityName, provinceName));
                })
                .create();
    }
}