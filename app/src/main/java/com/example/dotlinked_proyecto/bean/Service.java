package com.example.dotlinked_proyecto.bean;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Service {
  @SerializedName("servicioId")
  @Expose
  private Integer serviceId;

  @SerializedName("persona")
  @Expose
  private Person person;

  @SerializedName("servicio")
  @Expose
  private String serviceName;

  @SerializedName("fechaDesde")
  @Expose
  private String dateInit;

  @SerializedName("fechaHasta")
  @Expose
  private String dateEnd;

  @SerializedName("coste")
  @Expose
  private Float cost;

  @SerializedName("duracion")
  @Expose
  private Integer duration;

  @SerializedName("ubicacion")
  @Expose
  private String location;

  public Service(Integer serviceId, Person person, String serviceName, String dateInit, String dateEnd, Float cost, Integer duration, String location) {
    this.serviceId = serviceId;
    this.person = person;
    this.serviceName = serviceName;
    this.dateInit = dateInit;
    this.dateEnd = dateEnd;
    this.cost = cost;
    this.duration = duration;
    this.location = location;
  }

  public Integer getServiceId() {
    return serviceId;
  }

  public void setServiceId(Integer serviceId) {
    this.serviceId = serviceId;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  public Object getDateInit() {
    return dateInit;
  }

  public void setDateInit(String dateInit) {
    this.dateInit = dateInit;
  }

  public Object getDateEnd() {
    return dateEnd;
  }

  public void setDateEnd(String dateEnd) {
    this.dateEnd = dateEnd;
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

  @NonNull
  @Override
  public String toString() {
    return "Service{" +
        "serviceId=" + serviceId +
        ", person=" + person +
        ", serviceName='" + serviceName + '\'' +
        ", dateInit='" + dateInit + '\'' +
        ", dateEnd='" + dateEnd + '\'' +
        ", cost=" + cost +
        ", duration=" + duration +
        ", location='" + location + '\'' +
        '}';
  }
}
