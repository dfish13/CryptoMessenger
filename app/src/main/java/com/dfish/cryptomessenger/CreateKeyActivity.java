package com.dfish.cryptomessenger;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class CreateKeyActivity extends AppCompatActivity {

    Button bGenerate;
    Button bAdd;
    Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_key);

        bGenerate = (Button) findViewById(R.id.bGenerate);
        bAdd = (Button) findViewById(R.id.bAddKey);
        random = new Random(System.currentTimeMillis());


        bGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText eRandomKey = (EditText) findViewById(R.id.eRandomKey);
                eRandomKey.setText(getRandomKey());
            }
        });

        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText eRandomKey = (EditText) findViewById(R.id.eRandomKey);
                ContentValues values = new ContentValues();
                values.put(CryptoMessengerProvider.Keys.KEY_VAL, eRandomKey.getText().toString().trim());

                getContentResolver().insert(CryptoMessengerProvider.Keys.CONTENT_URI, values);
                Toast.makeText(CreateKeyActivity.this,
                        R.string.add_key_toast,
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    public String getRandomKey() {
        return Long.toHexString(random.nextLong());
    }
}
