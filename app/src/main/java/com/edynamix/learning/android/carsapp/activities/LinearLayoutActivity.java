package com.edynamix.learning.android.carsapp.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edynamix.learning.android.carsapp.models.car.Car;
import com.edynamix.learning.android.carsapp.views.CarDetailsView;
import com.edynamix.learning.android.carsapp.models.storage.CarsStorage;
import com.edynamix.learning.android.carsapp.R;
import com.edynamix.learning.android.carsapp.utils.Constants;

public class LinearLayoutActivity extends Activity {

    private Button buttonLinearLayoutBack;
    private Button buttonLinearLayoutListCarsAlternatively;
    private Button buttonShowYearDialogClose;

    private LinearLayout linearLayoutLinearLayoutCarsDetails;

    private TextView textViewLinearLayoutLoggedInEmail;

    private Dialog dialogShowYearOfManufacture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_linear_layout);

        initViews();
    }

    private void initViews() {
        initAppToolbar();
        initButtonListCarsAlternatively();
        initLinearLayoutListCars();
        initDialogWithYearOfManufacture();
    }

    private void initAppToolbar() {
        textViewLinearLayoutLoggedInEmail = (TextView) findViewById(R.id.textViewLinearLayoutLoggedInEmail);
        setEmailFromSharedPrefsInAppBar(textViewLinearLayoutLoggedInEmail);

        buttonLinearLayoutBack = (Button) findViewById(R.id.buttonLinearLayoutBack);
        buttonLinearLayoutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initButtonListCarsAlternatively() {
        buttonLinearLayoutListCarsAlternatively = (Button) findViewById(R.id.buttonLinearLayoutListCarsAlternatively);
        buttonLinearLayoutListCarsAlternatively.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToListView();
            }
        });
    }

    private void navigateToListView() {
        Intent listViewActivityIntent = new Intent(LinearLayoutActivity.this, ListViewActivity.class);
        startActivity(listViewActivityIntent);
    }

    private void setEmailFromSharedPrefsInAppBar(TextView textViewForEmail) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sharedPrefsEmail = sharedPreferences.getString(Constants.SHARED_PREFERENCES_EMAIL, Constants.EMPTY_VALUE);
        textViewForEmail.setText(sharedPrefsEmail);
    }

    private void initLinearLayoutListCars() {
        linearLayoutLinearLayoutCarsDetails = (LinearLayout) findViewById(R.id.linearLayoutLinearLayoutCarsDetails);
        int carsCount = CarsStorage.carsList.size();
        for (int carIndex = 0; carIndex < carsCount; carIndex++) {
            Car car = CarsStorage.carsList.get(carIndex);
            LinearLayout carDetailsView = new CarDetailsView(this, car);
            carDetailsView.setTag(carIndex);
            carDetailsView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialogWithYearOfManufacture(v);
                }
            });
            linearLayoutLinearLayoutCarsDetails.addView(carDetailsView);
        }
    }

    private void initDialogWithYearOfManufacture() {
        final Dialog dialogShowYearOfManufacture = new Dialog(LinearLayoutActivity.this);
        dialogShowYearOfManufacture.setContentView(R.layout.dialog_display_year_of_manufacture);
        this.dialogShowYearOfManufacture = dialogShowYearOfManufacture;
        this.dialogShowYearOfManufacture.setTitle(R.string.year_of_manufacture);

        initButtonShowYearInDialog();
    }

    private void initButtonShowYearInDialog() {
        buttonShowYearDialogClose = (Button) dialogShowYearOfManufacture.findViewById(R.id.buttonShowYearDialogClose);
        buttonShowYearDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogShowYearOfManufacture.dismiss();
            }
        });
    }

    private void showDialogWithYearOfManufacture(View view) {
        int carIndex = (int) view.getTag();

        TextView textViewYearOfManufacture = (TextView) dialogShowYearOfManufacture.findViewById(R.id.textViewDialogDisplayYearOfManufacture);
        textViewYearOfManufacture.setText(Constants.YEAR_LABEL + CarsStorage.carsList.get(carIndex).getYearOfManufacture());

        dialogShowYearOfManufacture.show();
    }

}
