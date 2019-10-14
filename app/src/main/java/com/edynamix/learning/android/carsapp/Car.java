package com.edynamix.learning.android.carsapp;

public class Car {

    private String brand;
    private String model;
    private String colour;
    private int doorsCount;
    private int yearOfManufacture;

    public Car(String brand, String model, String colour, int doorsCount, int  yearOfManufacture) {
        this.brand = brand;
        this.model = model;
        this.colour = colour;
        this.doorsCount = doorsCount;
        this.yearOfManufacture = yearOfManufacture;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getColour() {
        return colour;
    }

    public int getDoorsCount() {
        return doorsCount;
    }

    public int getYearOfManufacture() {
        return yearOfManufacture;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", colour='" + colour + '\'' +
                ", doorsCount=" + doorsCount +
                ", yearOfManufacture=" + yearOfManufacture +
                '}';
    }
}
