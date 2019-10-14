package com.edynamix.learning.android.carsapp.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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

    private CarsStorage carsStorage;

    private Button buttonBack;
    private Button buttonAddCar;
    private Button buttonGoToLinearLayout;
    private Button buttonGoToListView;
    private Button buttonAddRandomCars;
    private Button buttonDeleteRandomCar;
    private Button buttonSelectDateOfManufacture;
    private Button buttonCreateNewCar;

    private TextView textViewEmailInAppBar;
    private TextView textViewCarsCount;
    private TextView textViewShowSelectedDateOfManufacture;

    private EditText editTextBrand;
    private EditText editTextModel;
    private EditText editTextColour;
    private EditText editTextDoorsCount;

    private LinearLayout linearLayoutButtons;
    private ScrollView scrollViewAddCar;

    private Date dateOfManufacture;

    private DatePickerDialog.OnDateSetListener datePickerOnDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        initCarsStorage();
        initViews();
    }

    private void initCarsStorage() {
        carsStorage = CarsStorage.getInstance();
        carsStorage.updatePreferencesForActivity(this);
    }

    private void initViews() {
        initAppToolbar();
        initCarCounter();
        initButtons();
        initCarPropertiesEditTexts();
        initDateComponents();
        initCreateNewCarButton();
    }

    private void initAppToolbar() {
        textViewEmailInAppBar = (TextView) findViewById(R.id.textViewLoggedInEmail);
        setEmailFromSharedPrefsInAppBar(textViewEmailInAppBar);

        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initCarCounter() {
        textViewCarsCount = (TextView) findViewById(R.id.textViewCarsCount);
        updateViewForCarsCount();
    }

    private void initButtons() {
        scrollViewAddCar = (ScrollView) findViewById(R.id.scrollViewAddCar);
        linearLayoutButtons = (LinearLayout) findViewById(R.id.linearLayoutButtons);

        initAddCarButton();
        initButtonsForRandomCars();
        initButtonToLinearLayout();
        initButtonToListView();
        updateVisibilityOfGoToOtherViewsButtons();
    }

    private void initAddCarButton() {
        buttonAddCar = (Button) findViewById(R.id.buttonAddCar);
        buttonAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollViewAddCar.setVisibility(View.VISIBLE);
                linearLayoutButtons.setVisibility(View.GONE);
            }
        });
    }

    private void initButtonsForRandomCars() {
        initAddRandomCarsButton();
        initDeleteRandomCarButton();
    }

    private void initButtonToLinearLayout() {
        buttonGoToLinearLayout = (Button) findViewById(R.id.buttonGoToLinearLayout);
        buttonGoToLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToLinearLayoutIntent = new Intent(AddCarActivity.this, LinearLayoutActivity.class);
                startActivity(goToLinearLayoutIntent);
            }
        });
    }

    private void initButtonToListView() {
        buttonGoToListView = (Button) findViewById(R.id.buttonGoToListView);
        buttonGoToListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToLinearLayoutIntent = new Intent(AddCarActivity.this, ListViewActivity.class);
                startActivity(goToLinearLayoutIntent);
            }
        });
    }

    private void initAddRandomCarsButton() {
        buttonAddRandomCars = (Button) findViewById(R.id.buttonAddRandomCars);
        buttonAddRandomCars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateRandomNumberOfCars();
                updateViewForCarsCount();
            }
        });
    }

    private void initDeleteRandomCarButton() {
        buttonDeleteRandomCar = (Button) findViewById(R.id.buttonDeleteRandomCar);
        buttonDeleteRandomCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRandomCar();
                updateViewForCarsCount();
            }
        });
        updateVisibilityOfDeleteButton();
    }

    private void initCarPropertiesEditTexts() {
        editTextBrand = (EditText) findViewById(R.id.editTextBrand);
        editTextModel = (EditText) findViewById(R.id.editTextModel);
        editTextColour = (EditText) findViewById(R.id.editTextColour);
        editTextDoorsCount = (EditText) findViewById(R.id.editTextDoorsCount);
    }

    private void initDateComponents() {
        textViewShowSelectedDateOfManufacture = (TextView) findViewById(R.id.textViewShowSelectedDateOfManufacture);
        displayCurrentDate(textViewShowSelectedDateOfManufacture);

        initPickDateButton();
        initDatePickerOnDateSetListener();
    }

    private void initPickDateButton() {
        buttonSelectDateOfManufacture = (Button) findViewById(R.id.buttonSelectDateOfManufacture);
        buttonSelectDateOfManufacture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void initDatePickerOnDateSetListener() {
        datePickerOnDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month,dayOfMonth);
                dateOfManufacture = calendar.getTime();
                displaySelectedDate(textViewShowSelectedDateOfManufacture, date);
            }
        };
    }

    private void initCreateNewCarButton() {
        buttonCreateNewCar = (Button) findViewById(R.id.buttonCreateNewCar);
        buttonCreateNewCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCarToStorage();
                Toast.makeText(AddCarActivity.this, "The car was saved successfully.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setEmailFromSharedPrefsInAppBar(TextView textViewForEmail) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sharedPrefsEmail = sharedPreferences.getString(Constants.SHARED_PREFERENCES_EMAIL, Constants.EMPTY_VALUE);
        textViewForEmail.setText(sharedPrefsEmail);
    }

    private void displayCurrentDate(TextView textView) {
        StringBuilder formattedDate = new StringBuilder();
        formattedDate.append("Selected Date: ");

        Calendar calendar = Calendar.getInstance();
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        formattedDate.append(dayOfMonth).append("/");
        formattedDate.append(month).append( "/");
        formattedDate.append(year);

        dateOfManufacture = calendar.getTime();
        textView.setText(formattedDate.toString());
    }

    private void displaySelectedDate(TextView textView, String dateAsString) {
        StringBuilder formattedDate = new StringBuilder();
        formattedDate.append("Selected Date: ");
        formattedDate.append(dateAsString);

        textView.setText(formattedDate.toString());
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                datePickerOnDateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

        Button positiveDatePickerButton = datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE);
        positiveDatePickerButton.setBackgroundColor(Color.parseColor(Constants.COLOR_TEAL));
    }

    private void addCarToStorage() {
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
        updateVisibilityOfViewComponents();
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
        return Constants.CARS_COUNT_TEXT + carsStorage.getCarsCount();
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
        carsStorage.addCar(randomCar);

        updateVisibilityOfViewComponents();
    }

    private void deleteRandomCar() {
        int carsCount = carsStorage.getCarsCount();
        if (carsCount <= 0) {
            // Nothing to remove.
            Toast.makeText(this, "No cars to remove.", Toast.LENGTH_SHORT).show();
            return;
        }

        Random rand = new Random();
        int carIndex = rand.nextInt(carsStorage.getCarsCount());
        carsStorage.deleteCar(carIndex);

        updateVisibilityOfViewComponents();
    }

    private void updateViewForCarsCount() {
        textViewCarsCount.setText(getCarsCountText());
    }

    private void updateVisibilityOfViewComponents() {
        showButtonsLayout();
        hideScrollView();
        updateVisibilityOfDeleteButton();
        updateVisibilityOfGoToOtherViewsButtons();
    }

    private void showButtonsLayout() {
        linearLayoutButtons.setVisibility(View.VISIBLE);
    }

    private void hideScrollView() {
        scrollViewAddCar.setVisibility(View.GONE);
    }

    private void updateVisibilityOfDeleteButton() {
        int carsCount = carsStorage.getCarsCount();

        if (carsCount == 0) {
            buttonDeleteRandomCar.setVisibility(View.GONE);
        } else {
            buttonDeleteRandomCar.setVisibility(View.VISIBLE);
        }
    }

    private void updateVisibilityOfGoToOtherViewsButtons() {
        int carsCount = carsStorage.getCarsCount();
        if (carsCount < 10) {
            buttonGoToLinearLayout.setVisibility(View.GONE);
            buttonGoToListView.setVisibility(View.GONE);
        } else {
            buttonGoToLinearLayout.setVisibility(View.VISIBLE);
            buttonGoToListView.setVisibility(View.VISIBLE);
        }
    }
}
