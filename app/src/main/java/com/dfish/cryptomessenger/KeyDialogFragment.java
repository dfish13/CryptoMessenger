package com.dfish.cryptomessenger;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class KeyDialogFragment extends DialogFragment {

    public interface KeyDialogListener {
        void onFinishKeyDialog(Key key);
    }

    public static KeyDialogFragment newInstance(Key key) {
        KeyDialogFragment fragment = new KeyDialogFragment();

        Bundle args = new Bundle();
        args.putParcelable(Constants.KEY, key);
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Context context = getActivity();

        Bundle args = getArguments();
        final Key key = args.getParcelable(Constants.KEY);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.key_dialog_message)
                .setCancelable(true)
                .setPositiveButton(R.string.key_dialog_positive, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra(Constants.KEY, key);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.key_dialog_negative,
                        new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        context.getContentResolver().delete(
                                CryptoMessengerProvider.Keys.CONTENT_URI,
                                "_ID=?",
                                new String[]{key.id.toString()});
                        KeyDialogListener listener = (KeyDialogListener) getActivity();
                        listener.onFinishKeyDialog(key);
                    }
                });
        return builder.create();
    }
}
