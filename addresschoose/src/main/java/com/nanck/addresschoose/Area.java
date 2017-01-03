package com.nanck.addresschoose;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/12/1.
 */

public class Area implements Parcelable {
    private String code;
    private String name;
    private int id;
    private int level;
    private int fatherId;

    public Area() {
    }

    public Area(String code, String name, int id, int level, int fatherId) {
        this.code = code;
        this.name = name;
        this.id = id;
        this.level = level;
        this.fatherId = fatherId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getFatherId() {
        return fatherId;
    }

    public void setFatherId(int fatherId) {
        this.fatherId = fatherId;
    }

    @Override
    public String toString() {
        return "Area{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", level=" + level +
                ", fatherId=" + fatherId +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.name);
        dest.writeInt(this.id);
        dest.writeInt(this.level);
        dest.writeInt(this.fatherId);
    }

    protected Area(Parcel in) {
        this.code = in.readString();
        this.name = in.readString();
        this.id = in.readInt();
        this.level = in.readInt();
        this.fatherId = in.readInt();
    }

    public static final Creator<Area> CREATOR = new Creator<Area>() {
        @Override
        public Area createFromParcel(Parcel source) {
            return new Area(source);
        }

        @Override
        public Area[] newArray(int size) {
            return new Area[size];
        }
    };
}
