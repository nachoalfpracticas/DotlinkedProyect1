package com.example.dotlinked_proyecto.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

  @SerializedName("ordenHisLd")
  @Expose
  private Integer orderHisLd;

  @SerializedName("numOrden")
  @Expose
  private String numOrder;

  @SerializedName("titulo")
  @Expose
  private String title;

  @SerializedName("fecha")
  @Expose
  private String date;

  @SerializedName("estatus")
  @Expose
  private String status;

  @SerializedName("numObservaciones")
  @Expose
  private int numObservation;

  public Order() {
  }

  public Order(Integer orderHisLd, String numOrder, String title, String date, String status, Integer numObservation) {
    this.orderHisLd = orderHisLd;
    this.numOrder = numOrder;
    this.title = title;
    this.date = date;
    this.status = status;
    this.numObservation = numObservation;
  }

  public Integer getOrderHisLd() {
    return orderHisLd;
  }

  public void setOrderHisLd(Integer orderHisLd) {
    this.orderHisLd = orderHisLd;
  }

  public String getNumOrder() {
    return numOrder;
  }

  public void setNumOrder(String numOrder) {
    this.numOrder = numOrder;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public int getNumObservation() {
    return numObservation;
  }

  public void setNumObservation(int numObservation) {
    this.numObservation = numObservation;
  }


}
