package com.edynamix.learning.android.carsapp.utils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.widget.Button;

import com.edynamix.learning.android.carsapp.R;

public class ErrorDialogCreator {

    public static void createDialog(Resources resources, AlertDialog.Builder alertDialogBuilder, String errorMessage) {
        alertDialogBuilder.setTitle(resources.getString(R.string.login_error_title));
        alertDialogBuilder.setMessage(errorMessage);
        alertDialogBuilder.setCancelable(true);

        alertDialogBuilder.setNegativeButton(
                resources.getString(R.string.close_button_name),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        Button neutralButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        neutralButton.setBackgroundColor(resources.getColor(R.color.colorGrey));
        neutralButton.setTextColor(resources.getColor(R.color.colorBlack));
    }

}
