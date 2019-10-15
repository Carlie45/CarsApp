package com.edynamix.learning.android.carsapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CarDetailsView extends LinearLayout {

    private String BRAND_AND_MODEL_LABEL;
    private String COLOUR_LABEL;
    private String DOORS_COUNT_LABEL;

    public CarDetailsView(Activity parentActivity, Car car) {
        super(parentActivity.getApplicationContext());
        BRAND_AND_MODEL_LABEL = parentActivity.getResources().getString(R.string.brand_and_model) + " ";
        COLOUR_LABEL = parentActivity.getResources().getString(R.string.colour_label) + ": ";
        DOORS_COUNT_LABEL = parentActivity.getResources().getString(R.string.doors_count_label) + ": ";

        LayoutInflater inflater = (LayoutInflater) parentActivity.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.car_details, this, true);

        TextView textViewBrandAndModel = (TextView) findViewById(R.id.textViewBrandAndModel);
        textViewBrandAndModel.setText(BRAND_AND_MODEL_LABEL + car.getBrand() + " " + car.getModel());

        TextView textViewColour = (TextView) findViewById(R.id.textViewColour);
        textViewColour.setText(COLOUR_LABEL + car.getColour());

        TextView textViewDoorsCount = (TextView) findViewById(R.id.textViewDoorsCount);
        textViewDoorsCount.setText(DOORS_COUNT_LABEL + car.getDoorsCount());
    }
}
