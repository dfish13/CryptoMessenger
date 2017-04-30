package com.dfish.cryptomessenger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FriendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        FriendFragment fragment = new FriendFragment();

        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit();
    }
}
