package com.dfish.cryptomessenger;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class KeyDialogFragment extends DialogFragment {


    public static KeyDialogFragment newInstance(Key k) {
        KeyDialogFragment fragment = new KeyDialogFragment();

        Bundle args = new Bundle();
        args.putParcelable(Constants.KEY, k);
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Context con = getActivity();

        Bundle args = getArguments();
        final Key k = args.getParcelable(Constants.KEY);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.key_dialog_message)
                .setCancelable(true)
                .setPositiveButton(R.string.key_dialog_positive, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(con, MainActivity.class);
                        intent.putExtra(Constants.KEY, k);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.key_dialog_negative,
                        new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        con.getContentResolver().delete(
                                CryptoMessengerProvider.CONTENT_URI,
                                "_ID=?",
                                new String[]{k.id.toString()});
                    }
                });
        return builder.create();
    }
}
