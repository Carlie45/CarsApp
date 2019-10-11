package com.edynamix.learning.android.carsapp.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.drm.DrmStore;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.edynamix.learning.android.carsapp.Car;
import com.edynamix.learning.android.carsapp.CarBuilder;
import com.edynamix.learning.android.carsapp.CarsStorage;
import com.edynamix.learning.android.carsapp.R;
import com.edynamix.learning.android.carsapp.RandomCarGenerator;
import com.edynamix.learning.android.carsapp.utils.Constants;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class AddCarActivity extends Activity {

    private final String CARS_COUNT_TEXT = "Cars count: ";

    private Button buttonBack;
    private TextView emailTextViewInAppBar;

    private TextView textViewCarsCount;

    private Button buttonAddRandomCars;
    private Button buttonDeleteRandomCar;

    private EditText editTextBrand;
    private EditText editTextModel;
    private EditText editTextColour;
    private EditText editTextDoorsCount;

    private Date dateOfManufacture;

    private TextView textViewShowSelectedDateOfManufacture;
    private Button selectDateOfManufactureButton;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    private Button createNewCarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        initViews();
    }

    private void initViews() {
        emailTextViewInAppBar = (TextView) findViewById(R.id.textViewLoggedInEmail);
        setEmailFromSharedPrefsInAppBar(emailTextViewInAppBar);

        textViewCarsCount = (TextView) findViewById(R.id.textViewCarsCount);
        updateViewForCarsCount();

        buttonAddRandomCars = (Button) findViewById(R.id.buttonAddRandomCars);
        buttonAddRandomCars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateRandomNumberOfCars();
                updateViewForCarsCount();
            }
        });

        buttonDeleteRandomCar = (Button) findViewById(R.id.buttonDeleteRandomCar);
        buttonDeleteRandomCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRandomCar();
                updateViewForCarsCount();
            }
        });

        editTextBrand = (EditText) findViewById(R.id.editTextBrand);
        editTextModel = (EditText) findViewById(R.id.editTextModel);
        editTextColour = (EditText) findViewById(R.id.editTextColour);
        editTextDoorsCount = (EditText) findViewById(R.id.editTextDoorsCount);

        textViewShowSelectedDateOfManufacture = (TextView) findViewById(R.id.textViewShowSelectedDateOfManufacture);
        displaySelectedDate(textViewShowSelectedDateOfManufacture, null);

        selectDateOfManufactureButton = (Button) findViewById(R.id.buttonSelectDateOfManufacture);
        selectDateOfManufactureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month,dayOfMonth);
                dateOfManufacture = calendar.getTime();
                displaySelectedDate(textViewShowSelectedDateOfManufacture, date);
            }
        };

        createNewCarButton = (Button) findViewById(R.id.buttonCreateNewCar);
        createNewCarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCarToStorage();
                Toast.makeText(AddCarActivity.this, "The car was saved successfully.", Toast.LENGTH_LONG).show();
            }
        });

        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCarActivity.this.finish();
            }
        });
    }

    private void setEmailFromSharedPrefsInAppBar(TextView textViewForEmail) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sharedPrefsEmail = sharedPreferences.getString(Constants.SHARED_PREFERENCES_EMAIL, Constants.EMPTY_VALUE);
        textViewForEmail.setText(sharedPrefsEmail);
    }

    /**
     * If the passed date is null, then the current date will be displayed in the specified text view.
     * @param textView - the view in which the date will be displayed
     * @param dateAsString - the date that will be displayed in the specified text view
     */
    private void displaySelectedDate(TextView textView, String dateAsString) {
        StringBuilder formattedDate = new StringBuilder();
        formattedDate.append("Selected Date: ");
        if (dateAsString == null) {
            // Set the current date.
            Calendar calendar = Calendar.getInstance();

            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH) + 1;
            int year = calendar.get(Calendar.YEAR);

            formattedDate.append(dayOfMonth).append("/");
            formattedDate.append(month).append( "/");
            formattedDate.append(year);

            dateOfManufacture = calendar.getTime();

        } else {
            formattedDate.append(dateAsString);
        }

        textView.setText(formattedDate.toString());
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

        Button positiveDatePickerButton = datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE);
        positiveDatePickerButton.setBackgroundColor(Color.parseColor(Constants.COLOR_TEAL));
    }

    private void addCarToStorage() {
        CarsStorage carsStorage = CarsStorage.getInstance();
        String brand = editTextBrand.getText() != null ? editTextBrand.getText().toString() : null;
        String model = editTextModel.getText() != null ? editTextModel.getText().toString() : null;
        String colour = editTextColour.getText() != null ? editTextColour.getText().toString() : null;
        int doorsCount = editTextDoorsCount.getText() != null && editTextDoorsCount.getText().length() > 0 ?
                Integer.parseInt(editTextDoorsCount.getText().toString()) : 0;

        Car newCar = new CarBuilder()
                .setBrand(brand)
                .setModel(model)
                .setColour(colour)
                .setDoorsCount(doorsCount)
                .setDateOfManufacture(dateOfManufacture)
                .build();
        carsStorage.addCar(newCar);

        updateViewForCarsCount();
        updateVisibilityOfDeleteButton();
    }

    private void generateRandomNumberOfCars() {
        Random rand = new Random();
        int countOfCarsToBeGenerated = rand.nextInt(4) + 1; // To get a value between 1 and 4 including.
        for (int i = 0; i < countOfCarsToBeGenerated; i++) {
            addRandomCarToStorage();
        }

        displayToastWithGeneratedNumberOfCars(countOfCarsToBeGenerated);
    }

    private String getCarsCountText() {
        return CARS_COUNT_TEXT + CarsStorage.getInstance().getCarsCount();
    }

    private void displayToastWithGeneratedNumberOfCars(int countOfCarsGenerated) {
        String messageToDisplayInToast = countOfCarsGenerated + " car";
        if (countOfCarsGenerated > 1) {
            messageToDisplayInToast += "s";
        }
        messageToDisplayInToast += " generated.";
        Toast.makeText(AddCarActivity.this, messageToDisplayInToast, Toast.LENGTH_SHORT).show();
    }

    private void addRandomCarToStorage() {
        Car randomCar = RandomCarGenerator.getRandomCar();
        CarsStorage carsStorage = CarsStorage.getInstance();
        carsStorage.addCar(randomCar);

        updateVisibilityOfDeleteButton();
    }

    private void deleteRandomCar() {
        CarsStorage carsStorage = CarsStorage.getInstance();
        int carsCount = carsStorage.getCarsCount();
        if (carsCount <= 0) {
            // Nothing to remove.
            Toast.makeText(this, "No cars to remove.", Toast.LENGTH_SHORT).show();
            return;
        }

        Random rand = new Random();
        int carIndex = rand.nextInt(carsStorage.getCarsCount());
        carsStorage.deleteCar(carIndex);

        updateVisibilityOfDeleteButton();
    }

    private void updateViewForCarsCount() {
        textViewCarsCount.setText(getCarsCountText());
    }

    private void updateVisibilityOfDeleteButton() {
        int carsCount = CarsStorage.getInstance().getCarsCount();

        if (carsCount == 0) {
            buttonDeleteRandomCar.setVisibility(View.INVISIBLE);
        } else {
            buttonDeleteRandomCar.setVisibility(View.VISIBLE);
        }
    }
}
