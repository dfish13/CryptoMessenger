package com.dfish.cryptomessenger;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Random;

public class KeyListActivity extends ListActivity {

    private ArrayList<Key> keys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        generateData();
        KeyAdapter adapter = new KeyAdapter(this, keys);
        setListAdapter(adapter);
    }

    private void generateData() {
        keys = new ArrayList<>();
        Random random = new Random();
        Integer i = 0;
        while (i < 5) {
            i++;
            keys.add(new Key( i, ((Integer) random.nextInt()).toString()));
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("key", keys.get(pos));
        startActivity(intent);
    }

}
