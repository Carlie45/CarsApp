package com.edynamix.learning.android.carsapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.edynamix.learning.android.carsapp.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CarsStorage {

    public static List<Car> carsList;
    private static CarsStorage instance;
    private Gson gson = new Gson();
    private static SharedPreferences sharedPreferences;

    private CarsStorage() {
        carsList = new ArrayList<>();
    }

    public static CarsStorage getInstance() {
        if (instance == null) {
            instance = new CarsStorage();
        }
        return instance;
    }

    public void updatePreferencesForActivity(Activity activity) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
    }

    public void addCar(Car car) {
        this.carsList.add(car);
        storeDataToSharedPrefs();
    }

    public void deleteCar(int carIndex) {
        this.carsList.remove(carIndex);
        storeDataToSharedPrefs();
    }

    public int getCarsCount() {
        return carsList.size();
    }

    public String serializeToJSON() {
        String json = gson.toJson(this.carsList);
        return json;
    }

    public List<Car> deserializeFromJSON(String json) {
        Type typeOfT = TypeToken.getParameterized(List.class, Car.class).getType();
        List<Car> list = new Gson().fromJson(json, typeOfT);
        return list;
    }

    private void storeDataToSharedPrefs() {
        String json = serializeToJSON();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.SHARED_PREFERENCES_JSON_KEY, json);
        editor.commit();
    }

    private List<Car> readDataFromSharedPrefs() {
        String carsKey = sharedPreferences.getString(Constants.SHARED_PREFERENCES_JSON_KEY, null);

        if (carsKey != null) {
            List<Car> list = deserializeFromJSON(carsKey);
            return list;
        }
        return new ArrayList<>();
    }
}
