package com.example.dotlinked_proyecto.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Appointment {

  @SerializedName("servicioId")
  @Expose
  private Integer serviceId;
  @SerializedName("persona")
  @Expose
  private String userName;
  @SerializedName("personaId")
  @Expose
  private Integer personId;
  @SerializedName("servicio")
  @Expose
  private String service;
  @SerializedName("fechaDesde")
  @Expose
  private String dateFrom;
  @SerializedName("fechaHasta")
  @Expose
  private String dateTo;
  @SerializedName("coste")
  @Expose
  private Float cost;
  @SerializedName("duracion")
  @Expose
  private Integer duration;
  @SerializedName("ubicacion")
  @Expose
  private String location;
  @SerializedName("citaId")
  @Expose
  private Integer appointmentId;

  public Integer getServiceId() {
    return serviceId;
  }

  public void setServiceId(Integer servicioId) {
    this.serviceId = servicioId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Integer getPersonId() {
    return personId;
  }

  public void setPersonId(Integer personId) {
    this.personId = personId;
  }

  public String getService() {
    return service;
  }

  public void setServicio(String service) {
    this.service = service;
  }

  public String getDateFrom() {
    return dateFrom;
  }

  public void setDateFrom(String dateFrom) {
    this.dateFrom = dateFrom;
  }

  public String getDateTo() {
    return dateTo;
  }

  public void setDateTo(String dateTo) {
    this.dateTo = dateTo;
  }

  public Float getCost() {
    return cost;
  }

  public void setCost(Float cost) {
    this.cost = cost;
  }

  public Integer getDuration() {
    return duration;
  }

  public void setDuration(Integer duration) {
    this.duration = duration;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public Integer getAppointmentId() {
    return appointmentId;
  }

  public void setAppointmentId(Integer appointmentId) {
    this.appointmentId = appointmentId;
  }

}

