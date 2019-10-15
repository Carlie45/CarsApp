package com.edynamix.learning.android.carsapp.utils;

import android.content.res.Resources;

import com.edynamix.learning.android.carsapp.R;
import com.edynamix.learning.android.carsapp.exceptions.IllegalCredentialsException;

import java.util.regex.Pattern;

public class LoginDataValidator {

    private Resources resources;

    // Regex according to OWASP Validation Regex Repository.
    private final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private String EMAIL_TOO_LONG_MESSAGE;
    private String EMAIL_NOT_VALID_MESSAGE;
    private String PASSWORD_MUST_BE_AT_LEAST_TEN_CHARACTERS_LONG_MESSAGE;
    private String PASSWORD_IS_TOO_LONG_MESSAGE;
    private String PASSWORD_DOES_NOT_CONTAIN_DIGIT_MESSAGE;
    private String PASSWORD_DOES_NOT_CONTAIN_LETTER_MESSAGE;
    private String PASSWORD_DOES_NOT_CONTAIN_SPECIAL_CHARACTER_MESSAGE;

    public LoginDataValidator(Resources resources) {
        this.resources = resources;
        init();
    }

    private void init() {
        EMAIL_TOO_LONG_MESSAGE = resources.getString(R.string.email_too_long) + " ";
        EMAIL_NOT_VALID_MESSAGE = resources.getString(R.string.email_not_valid);
        PASSWORD_MUST_BE_AT_LEAST_TEN_CHARACTERS_LONG_MESSAGE = resources.getString(R.string.password_must_be_at_least_ten_characters_long);
        PASSWORD_IS_TOO_LONG_MESSAGE = resources.getString(R.string.password_is_too_long) + " ";
        PASSWORD_DOES_NOT_CONTAIN_DIGIT_MESSAGE = resources.getString(R.string.password_does_not_contain_digit) + " ";
        PASSWORD_DOES_NOT_CONTAIN_LETTER_MESSAGE = resources.getString(R.string.password_does_not_contain_letter) + " ";
        PASSWORD_DOES_NOT_CONTAIN_SPECIAL_CHARACTER_MESSAGE = resources.getString(R.string.password_does_not_contain_spec_char) + " ";
    }

    /**
     * The provided e-mail is validated according to the OWASP Validation Regex Repository. The email will be rejected if it is too long.
     * See Constants.MAX_EMAIL_LENGTH.
     * @param email - required to be not null
     */
    public void validateEmail(String email) throws IllegalCredentialsException {
        if (email.length() > Constants.MAX_EMAIL_LENGTH) {
            throw new IllegalCredentialsException(EMAIL_TOO_LONG_MESSAGE + Constants.MAX_EMAIL_LENGTH);
        }

        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        boolean matchesRegex = pattern.matcher(email).matches();
        if (!matchesRegex) {
            throw new IllegalCredentialsException(EMAIL_NOT_VALID_MESSAGE);
        }
    }

    /**
     * This method checks if the password is at least 10 characters long, contains at least 1 letter, at least 1 digit and
     * at least 1 special character. The password will be rejected if it is too long. See Constants.MAX_PASSWORD_LENGTH.
     * @param password - required to be not null
     */
    public void validatePassword(String password) throws IllegalCredentialsException {
        boolean isPasswordValid = false;

        if (password.length() < 10) {
             throw new IllegalCredentialsException(PASSWORD_MUST_BE_AT_LEAST_TEN_CHARACTERS_LONG_MESSAGE);
        }

        if (password.length() > Constants.MAX_PASSWORD_LENGTH) {
            throw new IllegalCredentialsException(PASSWORD_IS_TOO_LONG_MESSAGE + Constants.MAX_PASSWORD_LENGTH);
        }

        boolean containsDigit = false;
        boolean containsLetter = false;
        boolean containsSpecialCharacters = false;
        for (int currentCharIndex = 0; currentCharIndex < password.length(); currentCharIndex++) {
            if (containsDigit && containsLetter && containsSpecialCharacters) {
                // The password meets all the requirements. Nothing more to check.
                return;
            }

            char currentChar = password.charAt(currentCharIndex);
            if (!containsDigit) {
                containsDigit = CharacterMatcher.isADigit(currentChar);
            }
            if (!containsLetter) {
                containsLetter = CharacterMatcher.isALetter(currentChar);
            }
            if (!containsSpecialCharacters) {
                containsSpecialCharacters = CharacterMatcher.isASpecialCharacter(currentChar);
            }
        }

        StringBuilder errorMessage = new StringBuilder();
        if (!containsDigit) {
            errorMessage.append(PASSWORD_DOES_NOT_CONTAIN_DIGIT_MESSAGE);
        }
        if (!containsLetter) {
            errorMessage.append(PASSWORD_DOES_NOT_CONTAIN_LETTER_MESSAGE);
        }
        if (!containsSpecialCharacters) {
            errorMessage.append(PASSWORD_DOES_NOT_CONTAIN_SPECIAL_CHARACTER_MESSAGE);
        }

        if (errorMessage.length() != 0) {
            throw new IllegalCredentialsException(errorMessage.toString());
        }
    }

}
