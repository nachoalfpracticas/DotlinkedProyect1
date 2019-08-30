package com.example.dotlinked_proyecto.bean;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.applandeo.materialcalendarview.EventDay;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Calendar;

public class Event extends EventDay implements Parcelable {
  //  @SerializedName("eventoId")
//  @Expose
//  private int eventId;
  @SerializedName("titulo")
  @Expose
  private String title;
  @SerializedName("descripcion")
  @Expose
  private String description;
  @SerializedName("ubicacion")
  @Expose
  private String location;
  @SerializedName("horaDesde")
  @Expose
  private int timeFrom;
  @SerializedName("horaHasta")
  @Expose
  private int timeTo;
  @SerializedName("costo")
  @Expose
  private float cost = 0;
  @SerializedName("dia")
  @Expose
  private String diasId;
  @SerializedName("fechaDesde")
  @Expose
  private String dateInit;
  @SerializedName("fechaHasta")
  @Expose
  private String dateEnd;

  public static final Creator<Event> CREATOR = new Creator<Event>() {
    @Override
    public Event createFromParcel(Parcel in) {
      return new Event(in);
    }

    @Override
    public Event[] newArray(int size) {
      return new Event[size];
    }
  };

  public Event(Calendar day,
               String title,
               String description,
               String location,
               int timeFrom,
               int timeTo,
               float cost) {
    super(day);
    this.title = title;
    this.description = description;
    this.location = location;
    this.dateInit = getCalendar().getTime().toString();
    this.timeFrom = timeFrom;
    this.timeTo = timeTo;
    this.cost = cost;
  }

  private Event(Parcel in) {
    super((Calendar) in.readSerializable());
    title = in.readString();
    description = in.readString();
    location = in.readString();
    dateInit = (String) in.readSerializable();
    timeFrom = in.readInt();
    timeTo = in.readInt();
    cost = in.readFloat();


  }

  public Event(Calendar day) {
    super(day);
  }


  public Event(Calendar day, int drawable) {
    super(day, drawable);
  }


  public Event(Calendar day, Drawable drawable) {
    super(day, drawable);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeSerializable(getCalendar());
    parcel.writeString(getTitle());
    parcel.writeString(getDescription());
    parcel.writeString(getLocation());
    parcel.writeSerializable(getDateInit());
    parcel.writeInt(getTimeFrom());
    parcel.writeInt(getTimeTo());
    parcel.writeFloat(getCost());

  }

/*  // Un Evento es de una Empresa
  public int empresaId;
  public Empresa empresaEvento;

  // Un Evento tiene un Estatus
  @SerializedName("Estatus")
  public int estatusId;
  public Estatus estatusEvento;*/

 /* public int getEventId() {
    return eventId;
  }

  public void setEventId(int eventId) {
    this.eventId = eventId;
  }*/

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getDateInit() {
    return dateInit;
  }

  public void setDateInit(String dateInit) {
    this.dateInit = dateInit;
  }

  public String getDateEnd() {
    return dateEnd;
  }

  public void setDateEnd(String dateEnd) {
    this.dateEnd = dateEnd;
  }

  public int getTimeFrom() {
    return timeFrom;
  }

  public void setTimeFrom(int timeFrom) {
    this.timeFrom = timeFrom;
  }

  public int getTimeTo() {
    return timeTo;
  }

  public void setTimeTo(int timeTo) {
    this.timeTo = timeTo;
  }

  public String getDiasId() {
    return diasId;
  }

  public void setDiasId(String diasId) {
    this.diasId = diasId;
  }

  public float getCost() {
    return cost;
  }

  public void setCost(float cost) {
    this.cost = cost;
  }

  @NonNull
  @Override
  public String toString() {
    return "Event{" +
        //"eventId=" + eventId +
        "title='" + title + '\'' +
        ", description='" + description + '\'' +
        ", location='" + location + '\'' +
        ", timeFrom=" + timeFrom +
        ", timeTo=" + timeTo +
        ", cost=" + cost +
        ", diasId='" + diasId + '\'' +
        ", dateInit=" + dateInit +
        ", dateEnd=" + dateEnd +
        '}';
  }
}
