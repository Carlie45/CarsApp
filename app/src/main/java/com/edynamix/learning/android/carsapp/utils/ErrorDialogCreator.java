package com.edynamix.learning.android.carsapp.utils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.widget.Button;

public class ErrorDialogCreator {

    public static void createDialog(AlertDialog.Builder alertDialogBuilder , String errorMessage) {
        alertDialogBuilder.setTitle(Constants.ERROR_DIALOG_TITLE);
        alertDialogBuilder.setMessage(errorMessage);
        alertDialogBuilder.setCancelable(true);

        alertDialogBuilder.setNegativeButton(
                Constants.ERROR_DIALOG_CLOSE_BUTTON_NAME,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        Button neutralButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        neutralButton.setBackgroundColor(Color.parseColor(Constants.COLOR_GREY));
        neutralButton.setTextColor(Color.parseColor(Constants.COLOR_BLACK));
    }

}
