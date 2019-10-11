package com.edynamix.learning.android.carsapp.utils;

import com.edynamix.learning.android.carsapp.exceptions.IllegalCredentialsException;

import java.util.regex.Pattern;

public class LoginDataValidator {

    /**
     * The provided e-mail is validated according to the OWASP Validation Regex Repository. The email will be rejected if it is too long.
     * See Constants.MAX_EMAIL_LENGTH.
     * @param email - required to be not null
     */
    public static void validateEmail(String email) throws IllegalCredentialsException {
        if (email.length() > Constants.MAX_EMAIL_LENGTH) {
            throw new IllegalCredentialsException("The email is too long. Max permitted value is " + Constants.MAX_EMAIL_LENGTH);
        }

        // Regex according to OWASP Validation Regex Repository.
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        boolean matchesRegex = pattern.matcher(email).matches();
        if (!matchesRegex) {
            throw new IllegalCredentialsException("The e-mail is not valid.");
        }
    }

    /**
     * This method checks if the password is at least 10 characters long, contains at least 1 letter, at least 1 digit and
     * at least 1 special character. The password will be rejected if it is too long. See Constants.MAX_PASSWORD_LENGTH.
     * @param password - required to be not null
     */
    public static void validatePassword(String password) throws IllegalCredentialsException {
        boolean isPasswordValid = false;

        if (password.length() < 10) {
             throw new IllegalCredentialsException("The password must be at least 10 characters long.");
        }

        if (password.length() > Constants.MAX_PASSWORD_LENGTH) {
            throw new IllegalCredentialsException("The password is too long. Max permitted value is " + Constants.MAX_PASSWORD_LENGTH);
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
            errorMessage.append("The password does not contain a digit. ");
        }
        if (!containsLetter) {
            errorMessage.append("The password does not contain a letter. ");
        }
        if (!containsSpecialCharacters) {
            errorMessage.append("The password does not contain a special character. ");
        }

        if (errorMessage.length() != 0) {
            throw new IllegalCredentialsException(errorMessage.toString());
        }
    }


}
