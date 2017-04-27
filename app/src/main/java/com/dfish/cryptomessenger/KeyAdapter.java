package com.dfish.cryptomessenger;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by duncanfisher on 4/26/17.
 */

public class KeyAdapter extends ArrayAdapter<Key> {
    private final Context context;
    private final ArrayList<Key> keysArrayList;

    public KeyAdapter(Context context, ArrayList<Key> keysArrayList) {

        super(context, R.layout.key_row, keysArrayList);

        this.context = context;
        this.keysArrayList = keysArrayList;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.key_row, parent, false);
        rowView.setBackgroundColor(keysArrayList.get(pos).color);
        TextView keyIdView = (TextView) rowView.findViewById(R.id.key_id);
        TextView keyValueView = (TextView) rowView.findViewById(R.id.key_value);
        keyIdView.setText(keysArrayList.get(pos).id.toString());
        keyValueView.setText(keysArrayList.get(pos).key);

        return rowView;
    }
}
