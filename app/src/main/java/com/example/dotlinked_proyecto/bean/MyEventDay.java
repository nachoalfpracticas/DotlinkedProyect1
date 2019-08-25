package com.example.dotlinked_proyecto.bean;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import com.applandeo.materialcalendarview.EventDay;

import java.util.Calendar;

public class MyEventDay extends EventDay implements Parcelable {


  public static final Creator<MyEventDay> CREATOR = new Creator<MyEventDay>() {
    @Override
    public MyEventDay createFromParcel(Parcel in) {
      return new MyEventDay(in);
    }

    @Override
    public MyEventDay[] newArray(int size) {
      return new MyEventDay[size];
    }
  };

  private String mNote;
  private int imageResource;

  public MyEventDay(Calendar day, int drawable, String note) {
    super(day);
    mNote = note;
    imageResource = drawable;
  }

  private MyEventDay(Parcel in) {
    super((Calendar) in.readSerializable(), in.readInt());
    imageResource = (Integer) getImageDrawable();
    mNote = in.readString();

  }

  public String getNote() {
    return mNote;
  }

  @SuppressLint("RestrictedApi")
  @Override
  public Object getImageDrawable() {
    return super.getImageDrawable();
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeSerializable(getCalendar());
    parcel.writeInt(getImageResource());
    parcel.writeString(getNote());
  }

  @Override
  public int describeContents() {
    return 0;
  }


  public void setmNote(String mNote) {
    this.mNote = mNote;
  }

  public int getImageResource() {
    return imageResource;
  }

  public void setImageResource(int imageResource) {
    this.imageResource = imageResource;
  }
}
