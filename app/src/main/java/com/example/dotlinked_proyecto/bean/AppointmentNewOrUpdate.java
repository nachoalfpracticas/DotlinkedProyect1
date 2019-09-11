package com.example.dotlinked_proyecto.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppointmentNewOrUpdate {

  @SerializedName("citaId")
  @Expose
  private int appointmentId;

  @SerializedName("personaId")
  @Expose
  private int personId;

  @SerializedName("servicioId")
  @Expose
  private int serviceId;

  @SerializedName("desde")
  @Expose
  private String dateFrom;


  public AppointmentNewOrUpdate(Integer appointmentId, Integer serviceId, Integer personId, String dateFrom) {

    if (appointmentId != null) {
      this.appointmentId = appointmentId;
    }

    this.serviceId = serviceId;
    this.personId = personId;
    this.dateFrom = dateFrom;

  }


  public int getAppointmentId() {
    return appointmentId;
  }

  public void setAppointmentId(int appointmentId) {
    this.appointmentId = appointmentId;
  }

  public int getPersonId() {
    return personId;
  }

  public void setPersonId(int perssonId) {
    this.personId = perssonId;
  }

  public int getServiceId() {
    return serviceId;
  }

  public void setServiceId(int serviceId) {
    this.serviceId = serviceId;
  }


  public String getDateFrom() {
    return dateFrom;
  }

  public void setDateFrom(String dateFrom) {
    this.dateFrom = dateFrom;
  }
}
