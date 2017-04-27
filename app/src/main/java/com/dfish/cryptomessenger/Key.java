package com.dfish.cryptomessenger;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by duncanfisher on 4/26/17.
 */

public class Key implements Parcelable {

    public Integer id;
    public String key;
    public Integer color;

    public Key(int id, String key, int color) {
        super();
        this.id = id;
        this.key = key;
        this.color = color;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(key);
        out.writeInt(color);
    }

    private Key(Parcel in) {
        id = in.readInt();
        key = in.readString();
        color = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Key> CREATOR
            = new Parcelable.Creator<Key>() {
        @Override
        public Key createFromParcel(Parcel in) {
            return new Key(in);
        }

        @Override
        public Key[] newArray(int size) {
            return new Key[size];
        }
    };

}
