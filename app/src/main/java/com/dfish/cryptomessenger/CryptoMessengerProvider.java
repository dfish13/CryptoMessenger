package com.dfish.cryptomessenger;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class CryptoMessengerProvider extends ContentProvider {

    public final static String DBNAME = "CryptoMessengerDatabase";
    public final static String TABLE_KEYS = "Keys";
    public final static String COLUMN_KEY_ID = "_ID";
    public final static String COLUMN_KEY_VAL = "key_val";

    public final static String AUTHORITY = "com.dfish.cryptomessenger.provider";
    public static final Uri CONTENT_URI = Uri.parse(
            String.format("content://%s/%s", AUTHORITY, TABLE_KEYS)
    );
    private static final String SQL_CREATE_MAIN = String.format(
            "CREATE TABLE %s ( %s INTEGER PRIMARY KEY, %s TEXT);",
            TABLE_KEYS,
            COLUMN_KEY_ID,
            COLUMN_KEY_VAL
    );

    private MainDatabaseHelper mOpenHelper;

    public CryptoMessengerProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return mOpenHelper.getWritableDatabase().delete(TABLE_KEYS, selection, selectionArgs);
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = mOpenHelper.getWritableDatabase().insert(TABLE_KEYS, null, values);
        return Uri.withAppendedPath(CONTENT_URI, Long.toString(id));
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new MainDatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return mOpenHelper.getReadableDatabase().query(
                TABLE_KEYS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return mOpenHelper.getWritableDatabase().update(TABLE_KEYS, values, selection, selectionArgs);
    }

    protected static final class MainDatabaseHelper extends SQLiteOpenHelper {
        MainDatabaseHelper(Context context) {
            super(context, DBNAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_MAIN);
        }

        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        }
    }

}
