package com.dfish.cryptomessenger;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final private String TAG = MainActivity.class.getCanonicalName();
    private Key key;

    private FirebaseUser user;
    private FirebaseAuth mAuth;

    private Button bEncrypt;
    private Button bDecrypt;
    private Button bCopy;

    private TextView tKeyId;
    private TextView tKeyVal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        user = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();

        bEncrypt = (Button) findViewById(R.id.bEncrypt);
        bDecrypt = (Button) findViewById(R.id.bDecrypt);
        bCopy = (Button) findViewById(R.id.bCopy);
        tKeyId = (TextView) findViewById(R.id.key_id);
        tKeyVal = (TextView) findViewById(R.id.key_value);

        Bundle bundle = getIntent().getExtras();

        if (bundle == null) {
            key = new Key(0, TAG, Color.WHITE);
            tKeyId.setText("default");
            tKeyVal.setText("");
        } else {
            key = bundle.getParcelable(Constants.KEY);
            tKeyId.setText(key.id.toString());
            tKeyVal.setText(key.key);
            tKeyVal.setBackgroundColor(key.color);
        }


        bEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText eInput = (EditText) findViewById(R.id.eInput);
                EditText eOutput = (EditText) findViewById(R.id.eOutput);
                String input = eInput.getText().toString();

                try {
                    eOutput.setText(Crypter.encrypt(key.key, input));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        bDecrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText eInput = (EditText) findViewById(R.id.eInput);
                EditText eOutput = (EditText) findViewById(R.id.eOutput);
                String input = eInput.getText().toString();

                try {
                    eOutput.setText(Crypter.decrypt(key.key, input));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        bCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText eOutput = (EditText) findViewById(R.id.eOutput);
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("output", eOutput.getText().toString());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(getApplicationContext(),
                        "output copied!",
                        Toast.LENGTH_LONG).show();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        displayUserInfo();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // TODO
        // Map navigation items to activities
        int id = item.getItemId();
        Intent intent;

        if (id == R.id.nav_keys) {
            intent = new Intent(getApplicationContext(), KeyListActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_friends) {

        } else if (id == R.id.nav_settings) {
            intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_logout) {
            mAuth.signOut();
            intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_create_key) {
            intent = new Intent(getApplicationContext(), CreateKeyActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void displayUserInfo() {
        // TODO
        // Update nav header with user information
        if (user != null) {
            ((TextView) findViewById(R.id.eUserEmail)).setText(user.getEmail());
        }
    }
}
