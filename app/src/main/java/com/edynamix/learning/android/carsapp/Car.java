package com.edynamix.learning.android.carsapp;

import java.util.Date;

public class Car {

    private String brand;
    private String model;
    private String colour;
    private int doorsCount;
    private Date dateOfManufacture;

    public Car(String brand, String model, String colour, int doorsCount, Date dateOfManufacture) {
        this.brand = brand;
        this.model = model;
        this.colour = colour;
        this.doorsCount = doorsCount;
        this.dateOfManufacture = dateOfManufacture;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public int getDoorsCount() {
        return doorsCount;
    }

    public void setDoorsCount(int doorsCount) {
        this.doorsCount = doorsCount;
    }

    public Date getDateOfManufacture() {
        return dateOfManufacture;
    }

    public void setDateOfManufacture(Date dateOfManufacture) {
        this.dateOfManufacture = dateOfManufacture;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", colour='" + colour + '\'' +
                ", doorsCount=" + doorsCount +
                ", dateOfManufacture=" + dateOfManufacture +
                '}';
    }
}
