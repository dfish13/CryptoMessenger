package com.dfish.cryptomessenger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by duncanfisher on 4/30/17.
 */

public class FriendAdapter extends ArrayAdapter {

    private final Context context;
    private final ArrayList<Friend> friendArrayList;

    public FriendAdapter(Context context, ArrayList<Friend> friends) {
        super(context, android.R.layout.two_line_list_item, friends);
        this.context = context;
        this.friendArrayList = friends;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(android.R.layout.two_line_list_item, parent, false);
        TextView addressView = (TextView) rowView.findViewById(android.R.id.text1);
        TextView snippetView = (TextView) rowView.findViewById(android.R.id.text2);
        addressView.setText(friendArrayList.get(pos).address);
        snippetView.setText(friendArrayList.get(pos).snippet);

        return rowView;
    }
}
