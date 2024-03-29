package com.example.dotlinked_proyecto.bean;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Claim {

  @SerializedName("quejaId")
  @Expose
  private int claimId;

  @SerializedName("asunto")
  @Expose
  private String subject;

  @SerializedName("descripcion")
  @Expose
  private String description;

  @SerializedName("descripcionStatus")
  @Expose
  private String descriptionStatus;

  @SerializedName("fechaEmision")
  @Expose
  private String broadcastDate;

  @SerializedName("fechaactualizacion")
  @Expose
  private String updateDate;

  @SerializedName("resuelta")
  @Expose
  private int resolve; // 1 Resuelta , 0  Abierta

  // Una Queja tiene un Estatus
  private int statusId;
  // private Estatus estatusQueja { get; set; }

  // Esta  Persona Genero la  Queja
  private int personaId;
  private Person claimPerson;

  // Una Queja va a varias personas
  // private ICollection<QuejaPersona> quejaPersona { get; set; }


  public Claim(int claimId, String subject, String description, String broadcastDate, String updateDate, int resolve, int statusId, int personaId, Person claimPerson) {
    this.claimId = claimId;
    this.subject = subject;
    this.description = description;
    this.broadcastDate = broadcastDate;
    this.updateDate = updateDate;
    this.resolve = resolve;
    this.statusId = statusId;
    this.personaId = personaId;
    this.claimPerson = claimPerson;
  }

  public int getClaimId() {
    return claimId;
  }

  public void setClaimId(int claimId) {
    this.claimId = claimId;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getBroadcastDate() {
    return broadcastDate;
  }

  public void setBroadcastDate(Date broadcastDate) {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss.SSS", Locale.GERMANY);

    this.broadcastDate = df.format(broadcastDate);
  }

  public String getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(String updateDate) {
    this.updateDate = updateDate;
  }

  public int getResolve() {
    return resolve;
  }

  public void setResolve(int resolve) {
    this.resolve = resolve;
  }

  public int getStatusId() {
    return statusId;
  }

  public void setStatusId(int statusId) {
    this.statusId = statusId;
  }

  public int getPersonaId() {
    return personaId;
  }

  public void setPersonaId(int personaId) {
    this.personaId = personaId;
  }

  public Person getClaimPerson() {
    return claimPerson;
  }

  public void setClaimPerson(Person claimPerson) {
    this.claimPerson = claimPerson;
  }

  @NonNull
  @Override
  public String toString() {
    return "Claim{" +
        "claimId=" + claimId +
        ", subject='" + subject + '\'' +
        ", description='" + description + '\'' +
        ", broadcastDate=" + broadcastDate +
        ", updateDate=" + updateDate +
        ", resolve=" + resolve +
        ", statusId=" + statusId +
        ", personaId=" + personaId +
            ", claimPerson=" + claimPerson +
        '}';
  }

  public String getDescriptionStatus() {
    return descriptionStatus;
  }

  public void setDescriptionStatus(String descriptionStatus) {
    this.descriptionStatus = descriptionStatus;
  }
}
