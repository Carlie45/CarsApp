package com.edynamix.learning.android.carsapp;

import java.util.Random;

public class RandomCarGenerator {

    private static final String[] BRANDS = {"Audi", "BMW", "Toyota"};
    private static final String[] MODELS = {"A3", "X4", "G6"};
    private static final String[] COLOURS = {"red", "blue", "black"};

    private static final Random rand = new Random();

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
