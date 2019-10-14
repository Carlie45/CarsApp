package com.edynamix.learning.android.carsapp;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class RandomCarGenerator {

    private static String[] BRANDS = {"Audi", "BMW", "Toyota"};
    private static String[] MODELS = {"A3", "X4", "G6"};
    private static String[] COLOURS = {"red", "blue", "black"};

    private static Random rand = new Random();

    public static Car getRandomCar() {
        int brandIndex = rand.nextInt(BRANDS.length);
        int modelIndex = rand.nextInt(MODELS.length);
        int colourIndex = rand.nextInt(COLOURS.length);
        int doorsCount = rand.nextInt(6);
        int year = 1980 + rand.nextInt(39);

        Car randomCar = new CarBuilder()
                .setBrand(BRANDS[brandIndex])
                .setModel(MODELS[modelIndex])
                .setColour(COLOURS[colourIndex])
                .setDoorsCount(doorsCount)
                .setYearOfManufacture(year)
                .build();

        return randomCar;
    }

}
