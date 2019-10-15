package com.edynamix.learning.android.carsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CarDetailsView extends LinearLayout {

    private static final String BRAND_AND_MODEL_LABEL = App.getRes().getString(R.string.brand_and_model) + " ";
    private static final String COLOUR_LABEL = App.getRes().getString(R.string.colour_label) + " ";
    private static final String DOORS_COUNT_LABEL = App.getRes().getString(R.string.doors_count_label) + ": ";

    public CarDetailsView(Context context, Car car) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
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
