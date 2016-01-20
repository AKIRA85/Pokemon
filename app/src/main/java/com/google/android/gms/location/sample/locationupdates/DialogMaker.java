package com.google.android.gms.location.sample.locationupdates;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogMaker extends DialogFragment {

    Activity mActivity;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        mActivity = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);

        builder.setMessage("App reset will cause all your pokemon to be released. Do you want to continue?")
                .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SQLiteDatabase db = (new PokemonDatabase(mActivity)).getWritableDatabase();
                        db.delete(PokemonContract.CaptureList.TABLE_NAME,
                                null,
                                null);
                        Toast.makeText(mActivity, R.string.reset_message,
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

}
