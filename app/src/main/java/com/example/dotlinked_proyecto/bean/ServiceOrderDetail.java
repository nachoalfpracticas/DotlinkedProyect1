package com.example.dotlinked_proyecto.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServiceOrderDetail {

  @SerializedName("observacion")
  @Expose
  private String observation;

  @SerializedName("fechaServicio")
  @Expose
  private String serviceDate;

  @SerializedName("estatusPr")
  @Expose
  private String statusPr;

  @SerializedName("comentario")
  @Expose
  private String comment;

  @SerializedName("fechaComent")
  @Expose
  private String commentDate;

  @SerializedName("estatusCl")
  @Expose
  private String statusCl;

  @SerializedName("foto")
  @Expose
  private List<String> photo = null;

  public String getObservation() {
    return observation;
  }

  public void setObservation(String observation) {
    this.observation = observation;
  }

  public String getServiceDate() {
    return serviceDate;
  }

  public void setServiceDate(String serviceDate) {
    this.serviceDate = serviceDate;
  }

  public String getStatusPr() {
    return statusPr;
  }

  public void setStatusPr(String statusPr) {
    this.statusPr = statusPr;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getCommentDate() {
    return commentDate;
  }

  public void setCommentDate(String commentDate) {
    this.commentDate = commentDate;
  }

  public String getStatusCl() {
    return statusCl;
  }

  public void setStatusCl(String statusCl) {
    this.statusCl = statusCl;
  }

  public List<String> getPhoto() {
    return photo;
  }

  public void setPhoto(List<String> photo) {
    this.photo = photo;
  }

}
