package com.example.campsitehub.Parcelable;

import android.os.Parcel;
import android.os.Parcelable;

public class DataParcer implements Parcelable {

    private long id;
    private String text;

    public DataParcer() {
    }

    public DataParcer(long id) {
        this.id = id;
    }

    public DataParcer(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(id);
        dest.writeString(text);

    }

    public DataParcer(Parcel parcel)
    {
        id = parcel.readLong();
        text = parcel.readString();

    }

    public static final Parcelable.Creator<DataParcer> CREATOR = new Parcelable.Creator<DataParcer>() {

        @Override
        public DataParcer createFromParcel(Parcel parcel) {
            return new DataParcer(parcel);
        }

        @Override
        public DataParcer[] newArray(int size) {
            return new DataParcer[0];
        }
    };
}
