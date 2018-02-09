package com.allin.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import com.allin.R;

/**
 * Created by harry on 10/2/17.
 */

public class AlertMessages {

    Activity context;
    LayoutInflater inflater;
    AlertDialog alert;

    public AlertMessages(Activity context) {
        this.context = context;
        inflater = context.getLayoutInflater();
    }

    public void showErrorInConnection() {

        showMessage(context.getString(R.string.oops),
                context.getString(R.string.please_check_internet),
                context.getString(R.string.ok), null, null, new AlertDialogButtonClick() {
                    @Override
                    public void onButtonClick(String s) {

                    }
                });

    }

    public void showOnlyMessage(String title, String message) {

        showMessage(title,
                message,
                context.getString(R.string.ok), null, null, new AlertDialogButtonClick() {
                    @Override
                    public void onButtonClick(String s) {

                    }
                });

    }

    public interface AlertDialogButtonClick {

        public void onButtonClick(String s);

    }

    public void showMessage(String title, String message, final String posBtn, final String negBtn, final String neuBtn, final AlertDialogButtonClick alertDialogButtonClick) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if (title != null) {
            builder.setTitle(title);
        }

        if (message != null) {
            builder.setMessage(message);
        }

        if (posBtn != null) {
            builder.setPositiveButton(posBtn, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    alertDialogButtonClick.onButtonClick(posBtn);
                }
            });
        }

        if (negBtn != null) {
            builder.setNegativeButton(negBtn, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    alertDialogButtonClick.onButtonClick(negBtn);
                }
            });
        }

        if (neuBtn != null) {
            builder.setNeutralButton(neuBtn, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    alertDialogButtonClick.onButtonClick(neuBtn);
                }
            });
        }

        AlertDialog alert = builder.create();
        alert.show();

    }

}
