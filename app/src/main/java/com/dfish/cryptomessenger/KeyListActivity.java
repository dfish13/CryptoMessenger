package com.dfish.cryptomessenger;

import android.app.ListActivity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Random;

public class KeyListActivity extends ListActivity
        implements KeyDialogFragment.KeyDialogListener {

    private static final float GOLD = 0.618033988749895f;

    private ArrayList<Key> keys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_list);


        Cursor kCursor = getContentResolver().query(
                CryptoMessengerProvider.Keys.CONTENT_URI,
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
                keys.add(new Key(kCursor.getInt(0), kCursor.getString(2), Color.HSVToColor(hsv)));
                i++;
            }
            KeyAdapter adapter = new KeyAdapter(this, keys);
            setListAdapter(adapter);
        }


    }

    private void generateKeys(int n) {
        keys = new ArrayList<>();
        Random random = new Random();
        Integer i = 0;
        while (i < n) {
            i++;
            keys.add(new Key( i, Long.toHexString(random.nextLong()), Color.BLUE));
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        KeyDialogFragment kFrag = KeyDialogFragment.newInstance(keys.get(pos));
        kFrag.show(getFragmentManager(), Constants.KEY);
    }

    @Override
    public void onFinishKeyDialog(Key key) {
        keys.remove(key);
        ((ArrayAdapter) getListAdapter()).notifyDataSetChanged();
    }

}

