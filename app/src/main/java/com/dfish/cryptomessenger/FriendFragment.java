package com.dfish.cryptomessenger;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by duncanfisher on 4/30/17.
 */

public class FriendFragment extends ListFragment implements
        LoaderManager.LoaderCallbacks<Cursor>,
        AdapterView.OnItemClickListener {

    private final static String[] PROJECTION = {
            Telephony.Sms._ID,
            Telephony.Sms.THREAD_ID,
            Telephony.Sms.ADDRESS,
            Telephony.Sms.BODY
    };

    private final static String[] COLUMNS = {
            Telephony.Sms.ADDRESS,
            Telephony.Sms.BODY
    };

    private final static int[] IDS = {
            android.R.id.text1,
            android.R.id.text2
    };

    private FriendAdapter adapter;

    private Set<Integer> threads;
    private ArrayList<Friend> friends;

    public FriendFragment() {}

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        threads = new HashSet<>();
        friends = new ArrayList<>();

        loadFriends();


        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
        Intent intent = new Intent(getActivity(), ConverseActivity.class);
        intent.putExtra(ConverseActivity.EXTRA_THREAD_ID, friends.get(pos).thread_id);
        startActivity(intent);
    }

    public void loadFriends() {
        getLoaderManager().initLoader(0 , null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {

        return new CursorLoader(
                getActivity(),
                Telephony.Sms.CONTENT_URI,
                PROJECTION,
                null,
                null,
                Telephony.Sms.DEFAULT_SORT_ORDER
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Put the result Cursor in the adapter for the ListView
        int thread_id;
        Friend f;
        while (cursor.moveToNext()) {
            thread_id = cursor.getInt(1);
            if (!threads.contains(thread_id)) {
                f = new Friend(thread_id, cursor.getString(2), cursor.getString(3));
                friends.add(f);
                threads.add(thread_id);
            }
        }

        adapter = new FriendAdapter(getActivity(), friends);
        setListAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Delete the reference to the existing Cursor
        adapter.notifyDataSetChanged();
    }

}
