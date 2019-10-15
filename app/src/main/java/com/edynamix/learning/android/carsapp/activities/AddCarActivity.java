package com.edynamix.learning.android.carsapp.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.edynamix.learning.android.carsapp.App;
import com.edynamix.learning.android.carsapp.models.car.Car;
import com.edynamix.learning.android.carsapp.models.car.CarBuilder;
import com.edynamix.learning.android.carsapp.models.storage.CarsStorage;
import com.edynamix.learning.android.carsapp.R;
import com.edynamix.learning.android.carsapp.utils.RandomCarGenerator;
import com.edynamix.learning.android.carsapp.utils.Constants;

import java.util.Calendar;
import java.util.Random;

public class AddCarActivity extends Activity {

    private CarsStorage carsStorage;

    private Button buttonAddCarBack;
    private Button buttonAddCarAddCarFromInput;
    private Button buttonAddCarGoToLinearLayout;
    private Button buttonAddCarAddRandomCars;
    private Button buttonAddCarDeleteRandomCar;
    private Button buttonAddCarSelectYearOfManufacture;
    private Button buttonAddCarCreateNewCar;

    private TextView textViewAddCarLoggedInEmail;
    private TextView textViewAddCarCarsCount;
    private TextView textViewAddCarShowSelectedYearOfManufacture;

    private EditText editTextAddCarBrand;
    private EditText editTextAddCarModel;
    private EditText editTextAddCarColour;
    private EditText editTextAddCarDoorsCount;

    private LinearLayout linearLayoutAddCarButtons;
    private ScrollView scrollViewAddCarListInputFiledsForCar;

    private int yearOfManufacture;
    private Dialog dialogYearPicker;

    private static final String SELECTED_YEAR = App.getRes().getString(R.string.selected_year) + " ";
    private static final String CARS_COUNT_LABEL = App.getRes().getString(R.string.cars_count) + " ";
    private static final String NO_CARS_TO_REMOVE_MESSAGE = App.getRes().getString(R.string.no_cars_to_remove);
    private static final String CAR_SAVED_SUCCESSFULLY_MESSAGE = App.getRes().getString(R.string.car_saved_successfully);
    private static final String SELECT_YEAR_OF_MANUFACTURE_LABEL = App.getRes().getString(R.string.select_year_of_manufacture);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
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
        initAddCarScrollView();
        initButtons();
        initCarPropertiesEditTexts();
        initDateComponents();
        initCreateNewCarButton();
    }

    private void initAppToolbar() {
        textViewAddCarLoggedInEmail = (TextView) findViewById(R.id.textViewAddCarLoggedInEmail);
        setEmailFromSharedPrefsInAppBar(textViewAddCarLoggedInEmail);

        buttonAddCarBack = (Button) findViewById(R.id.buttonAddCarBack);
        buttonAddCarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initCarCounter() {
        textViewAddCarCarsCount = (TextView) findViewById(R.id.textViewAddCarCarsCount);
        updateViewForCarsCount();
    }

    private void initAddCarScrollView() {
        scrollViewAddCarListInputFiledsForCar = (ScrollView) findViewById(R.id.scrollViewAddCarListInputFiledsForCar);
    }

    private void initButtons() {
        linearLayoutAddCarButtons = (LinearLayout) findViewById(R.id.linearLayoutAddCarButtons);

        initAddCarButton();
        initAddRandomCarsButton();
        initDeleteRandomCarButton();
        initButtonToLinearLayout();

        updateVisibilityOfGoToLinearLayoutButton();
    }

    private void initAddCarButton() {
        buttonAddCarAddCarFromInput = (Button) findViewById(R.id.buttonAddCarAddCarFromInput);
        buttonAddCarAddCarFromInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollViewAddCarListInputFiledsForCar.setVisibility(View.VISIBLE);
                linearLayoutAddCarButtons.setVisibility(View.GONE);
            }
        });
    }

    private void initButtonToLinearLayout() {
        buttonAddCarGoToLinearLayout = (Button) findViewById(R.id.buttonAddCarGoToLinearLayout);
        buttonAddCarGoToLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToLinearLayoutListCars();
            }
        });
    }

    private void navigateToLinearLayoutListCars() {
        Intent goToLinearLayoutIntent = new Intent(AddCarActivity.this, LinearLayoutActivity.class);
        startActivity(goToLinearLayoutIntent);
    }

    private void initAddRandomCarsButton() {
        buttonAddCarAddRandomCars = (Button) findViewById(R.id.buttonAddCarAddRandomCars);
        buttonAddCarAddRandomCars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateRandomNumberOfCars();
                updateViewForCarsCount();
            }
        });
    }

    private void initDeleteRandomCarButton() {
        buttonAddCarDeleteRandomCar = (Button) findViewById(R.id.buttonAddCarDeleteRandomCar);
        buttonAddCarDeleteRandomCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRandomCar();
                updateViewForCarsCount();
            }
        });
        updateVisibilityOfDeleteButton();
    }

    private void initCarPropertiesEditTexts() {
        editTextAddCarBrand = (EditText) findViewById(R.id.editTextAddCarBrand);
        editTextAddCarModel = (EditText) findViewById(R.id.editTextAddCarModel);
        editTextAddCarColour = (EditText) findViewById(R.id.editTextAddCarColour);
        editTextAddCarDoorsCount = (EditText) findViewById(R.id.editTextAddCarDoorsCount);
    }

    private void initDateComponents() {
        textViewAddCarShowSelectedYearOfManufacture = (TextView) findViewById(R.id.textViewAddCarShowSelectedYearOfManufacture);
        displayCurrentYear(textViewAddCarShowSelectedYearOfManufacture);

        initPickYearButton();
        initDialogYearPicker();
    }

    private void initPickYearButton() {
        buttonAddCarSelectYearOfManufacture = (Button) findViewById(R.id.buttonAddCarSelectYearOfManufacture);
        buttonAddCarSelectYearOfManufacture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void initCreateNewCarButton() {
        buttonAddCarCreateNewCar = (Button) findViewById(R.id.buttonAddCarCreateNewCar);
        buttonAddCarCreateNewCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCarToStorage();
                Toast.makeText(AddCarActivity.this, CAR_SAVED_SUCCESSFULLY_MESSAGE, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initDialogYearPicker() {
        final Dialog dialogYearPicker = new Dialog(AddCarActivity.this);
        dialogYearPicker.setContentView(R.layout.dialog_year_picker);
        dialogYearPicker.setTitle(SELECT_YEAR_OF_MANUFACTURE_LABEL);

        final NumberPicker numberPickerYear = (NumberPicker) dialogYearPicker.findViewById(R.id.numberPickerYear);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        numberPickerYear.setMaxValue(year);
        numberPickerYear.setMinValue(year - 50);
        numberPickerYear.setValue(year);
        numberPickerYear.setWrapSelectorWheel(false);
        numberPickerYear.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                yearOfManufacture = newVal;
                displaySelectedYear(textViewAddCarShowSelectedYearOfManufacture, String.valueOf(newVal));
            }
        });

        initDialogYearPickerButtons(dialogYearPicker);
        this.dialogYearPicker = dialogYearPicker;
    }

    private void initDialogYearPickerButtons(final Dialog dialogYearPicker) {
        Button buttonYearPickerSelect = (Button) dialogYearPicker.findViewById(R.id.buttonYearPickerSelect);
        Button buttonYearPickerCancel = (Button) dialogYearPicker.findViewById(R.id.buttonYearPickerCancel);
        View.OnClickListener onClickDismissDialog = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogYearPicker.dismiss();
            }
        };

        buttonYearPickerSelect.setOnClickListener(onClickDismissDialog);
        buttonYearPickerCancel.setOnClickListener(onClickDismissDialog);
    }

    private void setEmailFromSharedPrefsInAppBar(TextView textViewForEmail) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sharedPrefsEmail = sharedPreferences.getString(Constants.SHARED_PREFERENCES_EMAIL, Constants.EMPTY_VALUE);
        textViewForEmail.setText(sharedPrefsEmail);
    }

    private void displayCurrentYear(TextView textView) {
        StringBuilder formattedDate = new StringBuilder();
        formattedDate.append(SELECTED_YEAR);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        formattedDate.append(year);

        yearOfManufacture = year;
        textView.setText(formattedDate.toString());
    }

    private void displaySelectedYear(TextView textView, String dateAsString) {
        StringBuilder formattedDate = new StringBuilder();
        formattedDate.append(SELECTED_YEAR);
        formattedDate.append(dateAsString);

        textView.setText(formattedDate.toString());
    }

    private void showDatePickerDialog() {
        dialogYearPicker.show();
    }

    private void addCarToStorage() {
        String brand = editTextAddCarBrand.getText() != null ? editTextAddCarBrand.getText().toString() : null;
        String model = editTextAddCarModel.getText() != null ? editTextAddCarModel.getText().toString() : null;
        String colour = editTextAddCarColour.getText() != null ? editTextAddCarColour.getText().toString() : null;
        int doorsCount = editTextAddCarDoorsCount.getText() != null && editTextAddCarDoorsCount.getText().length() > 0 ?
                Integer.parseInt(editTextAddCarDoorsCount.getText().toString()) : 0;

        Car newCar = new CarBuilder()
                .setBrand(brand)
                .setModel(model)
                .setColour(colour)
                .setDoorsCount(doorsCount)
                .setYearOfManufacture(yearOfManufacture)
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
        return CARS_COUNT_LABEL + carsStorage.getCarsCount();
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
            Toast.makeText(this, NO_CARS_TO_REMOVE_MESSAGE, Toast.LENGTH_SHORT).show();
            return;
        }

        Random rand = new Random();
        int carIndex = rand.nextInt(carsStorage.getCarsCount());
        carsStorage.deleteCar(carIndex);

        updateVisibilityOfViewComponents();
    }

    private void updateViewForCarsCount() {
        textViewAddCarCarsCount.setText(getCarsCountText());
    }

    private void updateVisibilityOfViewComponents() {
        showButtonsLayout();
        hideScrollView();
        updateVisibilityOfDeleteButton();
        updateVisibilityOfGoToLinearLayoutButton();
    }

    private void showButtonsLayout() {
        linearLayoutAddCarButtons.setVisibility(View.VISIBLE);
    }

    private void hideScrollView() {
        scrollViewAddCarListInputFiledsForCar.setVisibility(View.GONE);
    }

    private void updateVisibilityOfDeleteButton() {
        int carsCount = carsStorage.getCarsCount();

        if (carsCount == 0) {
            buttonAddCarDeleteRandomCar.setVisibility(View.GONE);
        } else {
            buttonAddCarDeleteRandomCar.setVisibility(View.VISIBLE);
        }
    }

    private void updateVisibilityOfGoToLinearLayoutButton() {
        int carsCount = carsStorage.getCarsCount();
        if (carsCount < 10) {
            buttonAddCarGoToLinearLayout.setVisibility(View.GONE);
        } else {
            buttonAddCarGoToLinearLayout.setVisibility(View.VISIBLE);
        }
    }
}
