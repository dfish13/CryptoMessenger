package com.dfish.cryptomessenger;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by duncanfisher on 4/26/17.
 */

public class Key implements Parcelable {

    public Integer id;
    public String key;

    public Key(int id, String key) {
        super();
        this.id = id;
        this.key = key;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(key);
    }

    private Key(Parcel in) {
        id = in.readInt();
        key = in.readString();
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
