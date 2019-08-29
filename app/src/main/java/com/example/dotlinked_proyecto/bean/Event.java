package com.example.dotlinked_proyecto.bean;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import com.applandeo.materialcalendarview.EventDay;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Calendar;

public class Event extends EventDay implements Parcelable {
  @SerializedName("eventId")
  @Expose
  public int eventId;
  @SerializedName("titulo")
  @Expose
  public String titulo;
  @SerializedName("descripcion")
  @Expose
  public String descripcion;
  @SerializedName("ubicacion")
  @Expose
  public String ubicacion;
  @SerializedName("fechaDesde")
  @Expose
  public String fechaDesde;
  @SerializedName("fechaHasta")
  @Expose
  public String fechaHasta;
  @SerializedName("horaDesde")
  @Expose
  public int horaDesde;
  @SerializedName("horaHasta")
  @Expose
  public int horaHasta;
  @SerializedName("dia")
  @Expose
  public String diasId;
  @SerializedName("costo")
  @Expose
  public float costo = 0;

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
               String titulo,
               String descripcion,
               String ubicacion,
               int horaDesde,
               int horaHasta,
               float costo) {
    super(day);
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.ubicacion = ubicacion;
    this.fechaDesde = getCalendar().getTime().toString();
    this.horaDesde = horaDesde;
    this.horaHasta = horaHasta;
    this.costo = costo;
  }

  private Event(Parcel in) {
    super((Calendar) in.readSerializable());
    titulo = in.readString();
    descripcion = in.readString();
    ubicacion = in.readString();
    fechaDesde = (String) in.readSerializable();
    horaDesde = in.readInt();
    horaHasta = in.readInt();
    costo = in.readFloat();


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
    parcel.writeString(getTitulo());
    parcel.writeString(getDescripcion());
    parcel.writeString(getUbicacion());
    parcel.writeSerializable(getFechaDesde());
    parcel.writeInt(getHoraDesde());
    parcel.writeInt(getHoraHasta());
    parcel.writeFloat(getCosto());

  }

/*  // Un Evento es de una Empresa
  public int empresaId;
  public Empresa empresaEvento;

  // Un Evento tiene un Estatus
  @SerializedName("Estatus")
  public int estatusId;
  public Estatus estatusEvento;*/

  public int getEventId() {
    return eventId;
  }

  public void setEventId(int eventId) {
    this.eventId = eventId;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getUbicacion() {
    return ubicacion;
  }

  public void setUbicacion(String ubicacion) {
    this.ubicacion = ubicacion;
  }

  public String getFechaDesde() {
    return fechaDesde;
  }

  public void setFechaDesde(String fechaDesde) {
    this.fechaDesde = fechaDesde;
  }

  public String getFechaHasta() {
    return fechaHasta;
  }

  public void setFechaHasta(String fechaHasta) {
    this.fechaHasta = fechaHasta;
  }

  public int getHoraDesde() {
    return horaDesde;
  }

  public void setHoraDesde(int horaDesde) {
    this.horaDesde = horaDesde;
  }

  public int getHoraHasta() {
    return horaHasta;
  }

  public void setHoraHasta(int horaHasta) {
    this.horaHasta = horaHasta;
  }

  public String getDiasId() {
    return diasId;
  }

  public void setDiasId(String diasId) {
    this.diasId = diasId;
  }

  public float getCosto() {
    return costo;
  }

  public void setCosto(float costo) {
    this.costo = costo;
  }

  @Override
  public String toString() {
    return "Event{" +
        "eventId=" + eventId +
        ", título='" + titulo + '\'' +
        ", descripción='" + descripcion + '\'' +
        ", ubicación='" + ubicacion + '\'' +
        ", fechaDesde=" + fechaDesde +
        ", fechaHasta=" + fechaHasta +
        ", horaDesde=" + horaDesde +
        ", horaHasta=" + horaHasta +
        ", diasId='" + diasId + '\'' +
        ", costo=" + costo +
        '}';
  }
}
