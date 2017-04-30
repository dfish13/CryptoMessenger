package com.dfish.cryptomessenger;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.provider.Telephony;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by duncanfisher on 4/29/17.
 */

public class SmsCursorAdapter extends android.support.v4.widget.CursorAdapter {

    final public int TEXT_RECIEVED = 1;
    final public int TEXT_SENT = 2;

    public SmsCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.sms_row, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView tBody = (TextView) view.findViewById(R.id.tBody);
        String body = cursor.getString(cursor.getColumnIndex(Telephony.Sms.BODY));
        int type = cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.Sms.TYPE));

        tBody.setText(body);
        if (type == TEXT_SENT) {
            tBody.setBackgroundColor(Color.MAGENTA);
        } else if (type == TEXT_RECIEVED) {
            tBody.setBackgroundColor(Color.BLUE);
        }
    }
}
