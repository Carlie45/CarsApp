package com.edynamix.learning.android.carsapp;

public class CarBuilder {

    private String brand;
    private String model;
    private String colour;
    private int doorsCount;
    private int yearOfManufacture;

    public CarBuilder() { }

    public CarBuilder setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public CarBuilder setModel(String model) {
        this.model = model;
        return this;
    }

    public CarBuilder setColour(String colour) {
        this.colour = colour;
        return this;
    }

    public CarBuilder setDoorsCount(int doorsCount) {
        this.doorsCount = doorsCount;
        return this;
    }

    public CarBuilder setYearOfManufacture(int yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
        return this;
    }

    public Car build() {
        return new Car(brand, model, colour, doorsCount, yearOfManufacture);
    }
}
