package com.example.amazinglu.pheramor_project.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.amazinglu.pheramor_project.utils.DateUtil;

import java.util.Date;

public class User implements Parcelable{
    public String email;
    public String password;
    public String name;
    public String zipCode;
    public Double height;
    public String heightMeasureUnit;
    public String gender;
    public Date dateOfBirth;
    public String race;
    public String religion;
    public String userImageUrl;

    public User() {}

    protected User(Parcel in) {
        email = in.readString();
        password = in.readString();
        name = in.readString();
        zipCode = in.readString();
        height = in.readDouble();
        heightMeasureUnit = in.readString();
        gender = in.readString();
        dateOfBirth = DateUtil.stringTodate(in.readString());
        race = in.readString();
        religion = in.readString();
        userImageUrl = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeString(name);
        parcel.writeString(zipCode);
        parcel.writeDouble(height);
        parcel.writeString(heightMeasureUnit);
        parcel.writeString(gender);
        parcel.writeString(DateUtil.dateToString(dateOfBirth));
        parcel.writeString(race);
        parcel.writeString(religion);
        parcel.writeString(userImageUrl);
    }
}
