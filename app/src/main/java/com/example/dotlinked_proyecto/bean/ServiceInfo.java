package com.example.dotlinked_proyecto.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceInfo extends Service {

  @SerializedName("id")
  @Expose
  private Integer serviceInfoId;
  @SerializedName("hora")
  @Expose
  private String hour;

  public ServiceInfo(String serviceId, String person, String serviceName, String dateInit, String dateEnd, float cost, Integer duration, String location) {
    super(serviceId, person, serviceName, dateInit, dateEnd, cost, duration, location);
  }

  public Integer getServiceInfoId() {
    return serviceInfoId;
  }

  public void setServiceInfoId(Integer serviceInfoId) {
    this.serviceInfoId = serviceInfoId;
  }

  public String getHour() {
    return hour;
  }

  public void setHour(String hour) {
    this.hour = hour;
  }
}
