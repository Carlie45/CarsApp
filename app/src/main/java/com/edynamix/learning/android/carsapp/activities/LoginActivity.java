package com.edynamix.learning.android.carsapp.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.edynamix.learning.android.carsapp.R;
import com.edynamix.learning.android.carsapp.exceptions.IllegalCredentialsException;
import com.edynamix.learning.android.carsapp.utils.Constants;
import com.edynamix.learning.android.carsapp.utils.ErrorDialogCreator;
import com.edynamix.learning.android.carsapp.utils.LoginDataValidator;

public class LoginActivity extends Activity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button loginButton;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }

    private void initViews() {
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        loginButton = (Button) findViewById(R.id.buttonLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Editable emailTextFromInput = editTextEmail.getText();
                    Editable passwordTextFromInput = editTextPassword.getText();

                    checkCredentials(emailTextFromInput, passwordTextFromInput);
                    // We have already verified the entered credentials, so we can use them as strings.
                    String email = emailTextFromInput.toString();
                    String password = passwordTextFromInput.toString();

                    saveCredentialsToSharedPreferences(email, password);
                    navigateToAddCarActivity();
                } catch (IllegalCredentialsException ice) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                    ErrorDialogCreator.createDialog(alertDialogBuilder, ice.getMessage());
                }
            }
        });
    }

    private void navigateToAddCarActivity() {
        Intent addCarActivityIntent = new Intent(this, AddCarActivity.class);
        startActivity(addCarActivityIntent);
    }

    private void checkCredentials(Editable emailTextFromInput, Editable passwordTextFromInput) throws IllegalCredentialsException {
        if (emailTextFromInput == null || emailTextFromInput.equals("")) {
            throw new IllegalCredentialsException("Please enter an e-mail.");
        }
        LoginDataValidator.validateEmail(emailTextFromInput.toString());

        if (passwordTextFromInput == null || passwordTextFromInput.equals("")) {
            throw new IllegalCredentialsException("Please enter a password.");
        }
        LoginDataValidator.validatePassword(passwordTextFromInput.toString());
    }

    private void saveCredentialsToSharedPreferences(String email, String password) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.SHARED_PREFERENCES_EMAIL, email);
        editor.putString(Constants.SHARED_PREFERENCES_PASSWORD, password);
        editor.commit();
    }

}
