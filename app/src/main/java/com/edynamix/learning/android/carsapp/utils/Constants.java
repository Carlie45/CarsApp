package com.edynamix.learning.android.carsapp.utils;

import com.edynamix.learning.android.carsapp.App;
import com.edynamix.learning.android.carsapp.R;

public interface Constants {

    public static final String EMPTY_VALUE = "";
    public static final String SHARED_PREFERENCES_JSON_KEY = "cars";

    // Login page error dialog related constants.
    public static final int MAX_EMAIL_LENGTH = 50;
    public static final int MAX_PASSWORD_LENGTH = 30;

    // Shared preferences related constants.
    public static final String SHARED_PREFERENCES_EMAIL = "email";
    public static final String SHARED_PREFERENCES_PASSWORD = "password";

    public static final String YEAR_LABEL = App.getRes().getString(R.string.year) + " ";

}
