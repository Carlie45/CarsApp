package com.edynamix.learning.android.carsapp.utils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Button;

import com.edynamix.learning.android.carsapp.App;
import com.edynamix.learning.android.carsapp.R;

public class ErrorDialogCreator {

    private static final String LOGIN_ERROR_DIALOG_TITLE = App.getRes().getString(R.string.login_error_title);
    private static final String CLOSE_BUTTON_NAME = App.getRes().getString(R.string.close_button_name);

    private static final int NEUTRAL_BUTTON_BACKGROUND_COLOUR_GREY = App.getRes().getColor(R.color.colorLightGrey);
    private static final int NEUTRAL_BUTTON_TEXT_COLOUR_BLACK = App.getRes().getColor(R.color.colorBlack);

    public static void createDialog(AlertDialog.Builder alertDialogBuilder, String errorMessage) {
        alertDialogBuilder.setTitle(LOGIN_ERROR_DIALOG_TITLE);
        alertDialogBuilder.setMessage(errorMessage);
        alertDialogBuilder.setCancelable(true);

        alertDialogBuilder.setNegativeButton(
                CLOSE_BUTTON_NAME,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        Button neutralButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        neutralButton.setBackgroundColor(NEUTRAL_BUTTON_BACKGROUND_COLOUR_GREY);
        neutralButton.setTextColor(NEUTRAL_BUTTON_TEXT_COLOUR_BLACK);
    }

}
