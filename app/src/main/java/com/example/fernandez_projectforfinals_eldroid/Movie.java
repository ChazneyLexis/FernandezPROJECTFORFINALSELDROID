package com.example.fernandez_projectforfinals_eldroid;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.Timestamp;

import java.io.Serializable;

public class Movie implements Serializable, Parcelable {

    String mID;
    String mTttl;
    String mYr;
    String mRnTm;
    String mLngg;
    String mCtry;
    Timestamp mRlsDt;

    public Movie(String mID, String mTttl, String mYr, String mRnTm, String mLngg, String mCtry, Timestamp mRlsDt) {
        this.mID = mID;
        this.mTttl = mTttl;
        this.mYr = mYr;
        this.mRnTm = mRnTm;
        this.mLngg = mLngg;
        this.mCtry = mCtry;
        this.mRlsDt = mRlsDt;
    }

    public Movie(String saveID, String saveTitle, String saveYear, String saveMRuntime, String saveLanguage, Timestamp saveRDate, String saveCountry) {
    }

    public String getmID() {
        return mID;
    }

    public void setmID(String mID) {
        this.mID = mID;
    }

    public String getmTttl() {
        return mTttl;
    }

    public void setmTttl(String mTttl) {
        this.mTttl = mTttl;
    }

    public String getmYr() {
        return mYr;
    }

    public void setmYr(String mYr) {
        this.mYr = mYr;
    }

    public String getmRnTm() {
        return mRnTm;
    }

    public void setmRnTm(String mRnTm) {
        this.mRnTm = mRnTm;
    }

    public String getmLngg() {
        return mLngg;
    }

    public void setmLngg(String mLngg) {
        this.mLngg = mLngg;
    }

    public String getmCtry() {
        return mCtry;
    }

    public void setmCtry(String mCtry) {
        this.mCtry = mCtry;
    }

    public Timestamp getmRlsDt() {
        return mRlsDt;
    }

    public void setmRlsDt(Timestamp mRlsDt) {
        this.mRlsDt = mRlsDt;
    }

    protected Movie(Parcel in) {

        mID = in.readString();
        mTttl = in.readString();
        mYr = in.readString();
        mLngg = in.readString();
        mCtry = in.readString();
        mRnTm = in.readString();
        mRlsDt = in.readParcelable(Timestamp.class.getClassLoader());

    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mID);
        parcel.writeString(mTttl);
        parcel.writeString(mYr);
        parcel.writeString(mLngg);
        parcel.writeString(mCtry);
        parcel.writeString(mRnTm);
        parcel.writeParcelable(mRlsDt, i);
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

}
