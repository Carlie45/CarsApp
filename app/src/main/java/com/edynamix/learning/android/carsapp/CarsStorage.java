package com.edynamix.learning.android.carsapp;

import java.util.ArrayList;
import java.util.List;

public class CarsStorage {

    private List<Car> carsList;
    private static CarsStorage instance;

    private CarsStorage() {
        carsList = new ArrayList<>();
    }

    public static CarsStorage getInstance() {
        if (instance == null) {
            instance = new CarsStorage();
        }
        return instance;
    }

    public void addCar(Car car) {
        this.carsList.add(car);
    }

    public void deleteCar(int carIndex) {
        this.carsList.remove(carIndex);
    }

    public int getCarsCount() {
        return carsList.size();
    }
}
