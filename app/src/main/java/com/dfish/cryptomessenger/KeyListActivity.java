package com.dfish.cryptomessenger;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Random;

public class KeyListActivity extends ListActivity {

    private static final float GOLD = 0.618033988749895f;

    private ArrayList<Key> keys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_list);


        Cursor kCursor = getContentResolver().query(
                CryptoMessengerProvider.CONTENT_URI,
                null,
                null,
                null,
                null
        );



        if(kCursor.getCount() > 0) {
            Random random = new Random(System.currentTimeMillis());
            float H = random.nextFloat();
            float[] hsv;
            int i = 0;

            keys = new ArrayList<>();
            while (kCursor.moveToNext()) {
                hsv = new float[]{((H + GOLD*i)%1)*360f, 0.5f, 0.95f};
                keys.add(new Key(kCursor.getInt(0), kCursor.getString(1), Color.HSVToColor(hsv)));
                i++;
            }
        } else {
            generateData();
        }

        Random random = new Random(System.currentTimeMillis());
        KeyAdapter adapter = new KeyAdapter(this, keys);
        setListAdapter(adapter);
    }

    private void generateData() {
        keys = new ArrayList<>();
        Random random = new Random();
        Integer i = 0;
        while (i < 5) {
            i++;
            keys.add(new Key( i, Integer.toHexString(random.nextInt()), Color.BLUE));
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        KeyDialogFragment kFragment = KeyDialogFragment.newInstance(keys.get(pos));
        kFragment.show(getFragmentManager(), Constants.KEY);
    }

}

