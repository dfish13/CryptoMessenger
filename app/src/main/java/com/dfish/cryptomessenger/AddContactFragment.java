package com.dfish.cryptomessenger;

import android.*;
import android.Manifest;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;

/**
 * Created by duncanfisher on 4/29/17.
 */

public class AddContactFragment extends ListFragment implements
        LoaderManager.LoaderCallbacks<Cursor>,
        AdapterView.OnItemClickListener {

    final private int REQUEST_CODE_READ_CONTACTS = 123;

    private final static String[] COLUMNS = {
        ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
    };

    private final static int[] IDS = {
            android.R.id.text1
    };

    private static final String[] PROJECTION = {
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.LOOKUP_KEY,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
    };

    private static final String SELECTION =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " LIKE ?" :
                    ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?";

    private static final int CONTACT_ID_INDEX = 0;
    private static final int LOOKUP_KEY_INDEX = 1;

    private int contactId;
    private String contactKey;
    private Uri contactUri;

    private SimpleCursorAdapter adapter;

    public AddContactFragment() {}

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadContactsWrapper();


        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
        Cursor cursor = ((SimpleCursorAdapter) parent.getAdapter()).getCursor();
        cursor.moveToPosition(pos);

        contactId = cursor.getInt(0);
        contactKey = cursor.getString(1);
        contactUri = ContactsContract.Contacts.getLookupUri(contactId, contactKey);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {

        return new CursorLoader(
                getActivity(),
                ContactsContract.Contacts.CONTENT_URI,
                PROJECTION,
                null,
                null,
                ContactsContract.Contacts.SORT_KEY_PRIMARY
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Put the result Cursor in the adapter for the ListView
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Delete the reference to the existing Cursor
        adapter.swapCursor(null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_READ_CONTACTS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void loadContactsWrapper() {
        if (getActivity().checkSelfPermission(android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.READ_CONTACTS},
                    REQUEST_CODE_READ_CONTACTS);
            return;
        }
        loadContacts();
    }

    public void loadContacts() {
        getLoaderManager().initLoader(0, null, this);

        adapter = new SimpleCursorAdapter(
                getActivity(),
                android.R.layout.two_line_list_item,
                null,
                COLUMNS,
                IDS,
                0);

        setListAdapter(adapter);
    }

}
