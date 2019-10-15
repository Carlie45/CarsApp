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
import com.edynamix.learning.android.carsapp.Car;
import com.edynamix.learning.android.carsapp.CarBuilder;
import com.edynamix.learning.android.carsapp.CarsStorage;
import com.edynamix.learning.android.carsapp.R;
import com.edynamix.learning.android.carsapp.RandomCarGenerator;
import com.edynamix.learning.android.carsapp.utils.Constants;

import java.util.Calendar;
import java.util.Random;

public class AddCarActivity extends Activity {

    private CarsStorage carsStorage;

    private Button buttonBack;
    private Button buttonAddCar;
    private Button buttonGoToLinearLayout;
    private Button buttonAddRandomCars;
    private Button buttonDeleteRandomCar;
    private Button buttonSelectDateOfManufacture;
    private Button buttonCreateNewCar;

    private TextView textViewEmailInAppBar;
    private TextView textViewCarsCount;
    private TextView textViewShowSelectedYearOfManufacture;

    private EditText editTextBrand;
    private EditText editTextModel;
    private EditText editTextColour;
    private EditText editTextDoorsCount;

    private LinearLayout linearLayoutButtons;
    private ScrollView scrollViewAddCar;

    private int yearOfManufacture;
    private Dialog dialogYearPicker;

    private static final String SELECTED_YEAR = App.getRes().getString(R.string.selected_year) + " ";
    private static final String CARS_COUNT_LABEL = App.getRes().getString(R.string.cars_count) + " ";
    private static final String NO_CARS_TO_REMOVE_MESSAGE = App.getRes().getString(R.string.no_cars_to_remove);

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

    private void initAddCarScrollView() {
        scrollViewAddCar = (ScrollView) findViewById(R.id.scrollViewAddCar);
    }

    private void initButtons() {
        linearLayoutButtons = (LinearLayout) findViewById(R.id.linearLayoutButtons);

        initAddCarButton();
        initAddRandomCarsButton();
        initDeleteRandomCarButton();
        initButtonToLinearLayout();

        updateVisibilityOfGoToLinearLayoutButton();
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

    private void initButtonToLinearLayout() {
        buttonGoToLinearLayout = (Button) findViewById(R.id.buttonGoToLinearLayout);
        buttonGoToLinearLayout.setOnClickListener(new View.OnClickListener() {
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
        textViewShowSelectedYearOfManufacture = (TextView) findViewById(R.id.textViewShowSelectedYearOfManufacture);
        displayCurrentYear(textViewShowSelectedYearOfManufacture);

        initPickYearButton();
        initDialogYearPicker();
    }

    private void initPickYearButton() {
        buttonSelectDateOfManufacture = (Button) findViewById(R.id.buttonSelectYearOfManufacture);
        buttonSelectDateOfManufacture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void initCreateNewCarButton() {
        final String CAR_SAVED_SUCCESSFULLY = getResources().getString(R.string.car_saved_successfully);
        buttonCreateNewCar = (Button) findViewById(R.id.buttonCreateNewCar);
        buttonCreateNewCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCarToStorage();
                Toast.makeText(AddCarActivity.this, CAR_SAVED_SUCCESSFULLY, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initDialogYearPicker() {
        final Dialog dialogYearPicker = new Dialog(AddCarActivity.this);
        dialogYearPicker.setContentView(R.layout.dialog_year_picker);
        final String SELECT_YEAR_OF_MANUFACTURE = getResources().getString(R.string.select_year_of_manufacture);
        dialogYearPicker.setTitle(SELECT_YEAR_OF_MANUFACTURE);

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
                displaySelectedYear(textViewShowSelectedYearOfManufacture, String.valueOf(newVal));
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
        textViewCarsCount.setText(getCarsCountText());
    }

    private void updateVisibilityOfViewComponents() {
        showButtonsLayout();
        hideScrollView();
        updateVisibilityOfDeleteButton();
        updateVisibilityOfGoToLinearLayoutButton();
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

    private void updateVisibilityOfGoToLinearLayoutButton() {
        int carsCount = carsStorage.getCarsCount();
        if (carsCount < 10) {
            buttonGoToLinearLayout.setVisibility(View.GONE);
        } else {
            buttonGoToLinearLayout.setVisibility(View.VISIBLE);
        }
    }
}
