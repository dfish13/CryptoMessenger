package com.dfish.cryptomessenger;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class CryptoMessengerProvider extends ContentProvider {

    public static class Keys {

        public static final String TABLE = "Keys";
        public static final String KEY_ID = "_id";
        public static final String THREAD_ID = "thread_id";
        public static final String KEY_VAL = "key_val";

        public static Uri CONTENT_URI = Uri.parse(
                String.format("content://%s/%s", AUTHORITY, TABLE)
        );
    }

    public static class Friends {

        public static final String TABLE = "Friends";
        public static final String FRIEND_ID = "_id";
        public static final String THREAD_ID = "thread_id";
        public static final String PHONE_NUMBER = "phone";
        public static final String NAME = "name";

        public static Uri CONTENT_URI = Uri.parse(
                String.format("content://%s/%s", AUTHORITY, TABLE)
        );
    }

    public final static String DBNAME = "CryptoMessengerProvider";
    public final static String AUTHORITY = "com.dfish.cryptomessenger.provider";

    private static final String SQL_CREATE_KEYS = String.format(
            "CREATE TABLE IF NOT EXISTS %s ( %s INTEGER PRIMARY KEY, %s INTEGER, %s TEXT);",
            Keys.TABLE,
            Keys.KEY_ID,
            Keys.THREAD_ID,
            Keys.KEY_VAL
    );
    private static final String SQL_CREATE_FRIENDS = String.format(
            "CREATE TABLE IF NOT EXISTS %s ( %s INTEGER PRIMARY KEY, %s INTEGER, %s TEXT, %s TEXT);",
            Friends.TABLE,
            Friends.FRIEND_ID,
            Friends.THREAD_ID,
            Friends.NAME,
            Friends.PHONE_NUMBER
    );

    private static final int KEYS = 1;
    private static final int FRIENDS = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(AUTHORITY, Keys.TABLE, KEYS);
        uriMatcher.addURI(AUTHORITY, Friends.TABLE, FRIENDS);
    }

    private MainDatabaseHelper mOpenHelper;

    public CryptoMessengerProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return mOpenHelper.getWritableDatabase().delete(getType(uri), selection, selectionArgs);
    }

    @Override
    public String getType(Uri uri) {
        int match = uriMatcher.match(uri);
        switch (match) {
            case KEYS:
                return Keys.TABLE;

            case FRIENDS:
                return Friends.TABLE;

            default:
                return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = mOpenHelper.getWritableDatabase().insert(getType(uri), null, values);
        int match = uriMatcher.match(uri);
        switch (match) {
            case KEYS:
                return Uri.withAppendedPath(Keys.CONTENT_URI, Long.toString(id));
            case FRIENDS:
                return Uri.withAppendedPath(Friends.CONTENT_URI, Long.toString(id));
        }
        return null;
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
                getType(uri),
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
        return mOpenHelper.getWritableDatabase().update(getType(uri), values, selection, selectionArgs);
    }

    protected static final class MainDatabaseHelper extends SQLiteOpenHelper {
        MainDatabaseHelper(Context context) {
            super(context, DBNAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_KEYS);
            db.execSQL(SQL_CREATE_FRIENDS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        }
    }

}
