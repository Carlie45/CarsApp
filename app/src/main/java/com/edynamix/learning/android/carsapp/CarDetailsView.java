package com.edynamix.learning.android.carsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CarDetailsView extends LinearLayout {

    public CarDetailsView(Context context, Car car) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.car_details, this, true);

        TextView textViewBrandAndModel = (TextView) findViewById(R.id.textViewBrandAndModel);
        textViewBrandAndModel.setText("Brand and model: " + car.getBrand() + " " + car.getModel());

        TextView textViewColour = (TextView) findViewById(R.id.textViewColour);
        textViewColour.setText("Colour: " + car.getColour());

        TextView textViewDoorsCount = (TextView) findViewById(R.id.textViewDoorsCount);
        textViewDoorsCount.setText("Doors count: " + car.getDoorsCount());
    }
}
