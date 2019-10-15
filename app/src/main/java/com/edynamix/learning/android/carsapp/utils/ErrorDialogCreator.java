package com.edynamix.learning.android.carsapp.utils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.widget.Button;

import com.edynamix.learning.android.carsapp.R;

public class ErrorDialogCreator {

    private static String loginErrorDialogTitle;
    private static String closeButtonName;

    public static void createDialog(Resources resources, AlertDialog.Builder alertDialogBuilder, String errorMessage) {
        if (loginErrorDialogTitle == null) {
            loginErrorDialogTitle = resources.getString(R.string.login_error_title);
        }

        alertDialogBuilder.setTitle(loginErrorDialogTitle);
        alertDialogBuilder.setMessage(errorMessage);
        alertDialogBuilder.setCancelable(true);

        if (closeButtonName == null) {
            closeButtonName = resources.getString(R.string.close_button_name);
        }

        alertDialogBuilder.setNegativeButton(
                closeButtonName,
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
