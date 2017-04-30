package com.dfish.cryptomessenger;

import android.app.ListActivity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Telephony;
import android.util.Log;

public class ConverseActivity extends ListActivity {

    final private int REQUEST_CODE_READ_SMS = 123;

    final private int MOM = 2;

    final static public String EXTRA_THREAD_ID = "thread_id";
    final static public String EXTRA_KEY = "key";

    private Integer thread_id;
    private Key key;

    private SmsCursorAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_converse);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            thread_id = bundle.getInt(EXTRA_THREAD_ID);
            key = bundle.getParcelable(EXTRA_KEY);
        } else {
            thread_id = MOM;
            key = new Key(0, this.getLocalClassName(), Color.BLUE);
        }



        readSMSWrapper();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_READ_SMS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readSMS();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void readSMSWrapper() {
        if (checkSelfPermission(android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.READ_SMS},
                    REQUEST_CODE_READ_SMS);
            return;
        }
        readSMS();
    }

    public void readSMS() {

        String[] cols = new String[]{
                Telephony.Sms._ID,
                Telephony.Sms.BODY,
                Telephony.Sms.TYPE,
                Telephony.Sms.ADDRESS
        };
        String selection = Telephony.Sms.THREAD_ID + " = ?";
        String[] args = new String[]{thread_id.toString()};


        Cursor cursor = getContentResolver().query(Telephony.Sms.CONTENT_URI,
                cols,
                selection,
                args,
                Telephony.Sms.DEFAULT_SORT_ORDER);

        cursor.moveToFirst();
        Log.i("address", cursor.getString(cursor.getColumnIndex(Telephony.Sms.ADDRESS)));

        adapter = new SmsCursorAdapter(this, cursor);
        setListAdapter(adapter);

    }
}
