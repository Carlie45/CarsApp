package com.edynamix.learning.android.carsapp.activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.edynamix.learning.android.carsapp.R;
import com.edynamix.learning.android.carsapp.utils.Constants;

public class AddCarActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        TextView loggedEmail = (TextView) findViewById(R.id.textViewLoggedInEmail);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sharedPrefsEmail = sharedPreferences.getString(Constants.SHARED_PREFERENCES_EMAIL, "");
        loggedEmail.setText(sharedPrefsEmail);
    }
}
